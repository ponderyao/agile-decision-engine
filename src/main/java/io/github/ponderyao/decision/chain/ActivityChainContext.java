package io.github.ponderyao.decision.chain;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.github.ponderyao.decision.engine.ScriptEngine;
import io.github.ponderyao.decision.util.SpringBeanUtils;
import io.github.ponderyao.decision.value.DecisionCondition;

/**
 * ActivityChainContext: 作业链上下文
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class ActivityChainContext {
    
    /** 脚本前置对象 */
    private final Map<String, Object> previousObjects;
    
    /** 作业步骤执行结果 */
    private final Map<ActivityChainStep, Map<String, Object>> activityResults = new HashMap<>();
    
    /** 最终输出结果 */
    private final Map<String, Object> finalResult = new HashMap<>();

    /** 脚本引擎 */
    private final ScriptEngine scriptEngine = SpringBeanUtils.getBean(ScriptEngine.class);
    
    public ActivityChainContext(DecisionCondition condition) {
        this.previousObjects = condition.getPreviousObjects();
    }
    
    public void recordStepResult(ActivityChainStep chainStep, Map<String, Object> stepResult) {
        this.activityResults.put(chainStep, stepResult);
    }
    
    public void recordFinalResult(Map<String, Object> stepResult) {
        this.finalResult.putAll(stepResult);
    }
    
    public Map<String, Object> collectStepResult(List<ActivityChainStep> chainSteps) {
        Map<String, Object> resultMap = new LinkedHashMap<>();
        chainSteps.forEach(chainStep ->  resultMap.putAll(this.activityResults.get(chainStep)));
        return resultMap;
    }

    public Map<String, Object> getPreviousObjects() {
        return previousObjects;
    }

    public Map<ActivityChainStep, Map<String, Object>> getActivityResults() {
        return activityResults;
    }

    public Map<String, Object> getFinalResult() {
        return finalResult;
    }

    public ScriptEngine getScriptEngine() {
        return scriptEngine;
    }
}
