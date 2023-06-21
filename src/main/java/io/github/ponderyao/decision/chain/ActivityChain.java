package io.github.ponderyao.decision.chain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.util.ObjectUtils;

import io.github.ponderyao.decision.common.constant.ExceptionConstants;
import io.github.ponderyao.decision.common.exception.DecisionEngineException;
import io.github.ponderyao.decision.entity.ActionEntry;
import io.github.ponderyao.decision.entity.ActionStub;
import io.github.ponderyao.decision.value.DecisionCondition;

/**
 * ActivityChain: 作业链
 * 
 * 作业链是动作脚本执行链路的简称, 即决策结果中按一定的顺序执行动作脚本.
 * 理想情况下建议决策结果有且仅有一项动作项, 但对于一些复杂的策略实现场
 * 景, 组件亦提供多动作的支撑, 并且允许动作之间设置执行先后顺序.
 * 
 * 执行顺序以拓扑排序的算法实现, 一个动作可指向下多个动作, 同时可以被多
 * 个前置动作指向该动作. 存在先后顺序的动作允许将上一动作的执行结果作为
 * 下一动作的入参. 不过，除非明确需要通过分解上下动作以达到复用或业务解
 * 耦的目的, 否则仍然建议在同一个动作中完成.
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class ActivityChain {
    
    /** 动作链上下文 */
    private final ActivityChainContext activityChainContext;
    
    /** 动作环节队列 */
    private Queue<ActivityChainStep> stepQueue;
    
    public ActivityChain(DecisionCondition condition, List<ActionStub> actionStubList, List<ActionEntry> actionEntryList) {
        this.activityChainContext = new ActivityChainContext(condition);
        initStepQueue(actionStubList, actionEntryList);
    }
    
    private void initStepQueue(List<ActionStub> actionStubList, List<ActionEntry> actionEntryList) {
        Map<Long, ActionStub> actionStubMap = actionStubList.stream().collect(Collectors.toMap(ActionStub::getActionStubId, Function.identity()));
        Map<Long, ActionStub> entryToActionStubMap = new HashMap<>();
        actionEntryList.forEach(actionEntry -> entryToActionStubMap.put(actionEntry.getActionEntryId(), actionStubMap.get(actionEntry.getActionStubId())));
        this.stepQueue = new LinkedList<>();
        topologicalSort(actionEntryList, entryToActionStubMap);
    }

    /**
     * 拓扑排序
     */
    private void topologicalSort(List<ActionEntry> actionEntryList, Map<Long, ActionStub> entryToActionStubMap) {
        Map<Long, Integer> indegree = new HashMap<>();
        Map<Long, List<Long>> actionToGraph = new HashMap<>();
        Map<Long, Set<Long>> actionFromGraph = new HashMap<>();
        actionEntryList.forEach(entry -> {
            Long key = entry.getActionEntryId();
            if (!indegree.containsKey(key)) {
                indegree.put(key, 0);
            }
            if (!ObjectUtils.isEmpty(entry.getNextAction())) {
                List<Long> nextActions = Arrays.stream(entry.getNextAction().split(",")).map(Long::valueOf).collect(Collectors.toList());
                actionToGraph.put(key, nextActions);
                nextActions.forEach(nextId -> {
                    indegree.put(nextId, indegree.getOrDefault(nextId, 0) + 1);
                    if (!actionFromGraph.containsKey(nextId)) {
                        actionFromGraph.put(nextId, new HashSet<>());
                    }
                    actionFromGraph.get(nextId).add(key);
                });
            }
        });
        Queue<ActivityChainStep> tempQueue = new LinkedList<>();
        Map<Long, ActivityChainStep> stepMap = new HashMap<>();
        indegree.forEach((key, value) -> {
            if (value == 0) {
                ActivityChainStep chainStep = new ActivityChainStep(key, entryToActionStubMap.get(key), null, actionToGraph.containsKey(key));
                tempQueue.offer(chainStep);
                stepMap.put(key, chainStep);
            }
        });
        while (!tempQueue.isEmpty()) {
            ActivityChainStep chainStep = tempQueue.poll();
            this.stepQueue.offer(chainStep);
            if (chainStep.getHasNext()) {
                actionToGraph.get(chainStep.getStepId()).forEach(nextId -> {
                    if (stepMap.containsKey(nextId)) {
                        // 作业链存在环路
                        throw new DecisionEngineException(ExceptionConstants.ACTION_ENTRY.LOOP);
                    }
                    indegree.put(nextId, indegree.get(nextId) - 1);
                    if (indegree.get(nextId) == 0) {
                        List<ActivityChainStep> previousSteps = new ArrayList<>();
                        if (actionFromGraph.containsKey(nextId)) {
                            actionFromGraph.get(nextId).forEach(prevId -> previousSteps.add(stepMap.get(prevId)));
                        }
                        ActivityChainStep nextStep = new ActivityChainStep(nextId, entryToActionStubMap.get(nextId), previousSteps, actionToGraph.containsKey(nextId));
                        tempQueue.offer(nextStep);
                        stepMap.put(nextId, nextStep);
                    }
                });
            }
        }
    }
    
    public Map<String, Object> invoke() {
        while (!this.stepQueue.isEmpty()) {
            ActivityChainStep currStep = this.stepQueue.poll();
            currStep.invoke(activityChainContext);
        }
        return activityChainContext.getFinalResult();
    }
    
}
