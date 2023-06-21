package io.github.ponderyao.decision.chain;

import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import io.github.ponderyao.decision.common.exception.ScriptEngineException;
import io.github.ponderyao.decision.entity.ActionStub;
import io.github.ponderyao.decision.value.ScriptRequest;
import io.github.ponderyao.decision.value.ScriptResult;

/**
 * ActivityChainStep: 作业链环节
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class ActivityChainStep {
    
    private Long stepId;
    
    /** 动作桩 */
    private ActionStub actionStub;
    
    /** 前置作业环节列表 */
    private List<ActivityChainStep> previousSteps;
    
    /** 是否有后序环节 */
    private Boolean hasNext;
    
    public ActivityChainStep(Long stepId, ActionStub actionStub, List<ActivityChainStep> previousSteps, Boolean hasNext) {
        this.stepId = stepId;
        this.actionStub = actionStub;
        this.previousSteps = previousSteps;
        this.hasNext = hasNext;
    }
    
    public void invoke(ActivityChainContext context) {
        // 组装前置步骤的出参作为入参
        Map<String, Object> parameters = CollectionUtils.isEmpty(previousSteps) ? null : context.collectStepResult(this.previousSteps);
        // 执行脚本
        ScriptRequest scriptRequest = new ScriptRequest(this.actionStub.getActionScript(), this.actionStub.getScriptMethod(),
            context.getPreviousObjects(), parameters);
        ScriptResult result = context.getScriptEngine().execute(scriptRequest);
        if (!result.getSuccess()) {
            throw new ScriptEngineException(this.actionStub.getActionCode(), result.getErrMsg());
        }
        // 保存上下文
        context.recordStepResult(this, result.getResponseData());
        if (!this.hasNext && !CollectionUtils.isEmpty(result.getResponseData())) {
            context.recordFinalResult(result.getResponseData());
        }
    }

    public Long getStepId() {
        return stepId;
    }

    public void setStepId(Long stepId) {
        this.stepId = stepId;
    }

    public ActionStub getActionStub() {
        return actionStub;
    }

    public void setActionStub(ActionStub actionStub) {
        this.actionStub = actionStub;
    }

    public List<ActivityChainStep> getPreviousSteps() {
        return previousSteps;
    }

    public void setPreviousSteps(List<ActivityChainStep> previousSteps) {
        this.previousSteps = previousSteps;
    }

    public Boolean getHasNext() {
        return hasNext;
    }

    public void setHasNext(Boolean hasNext) {
        this.hasNext = hasNext;
    }
}
