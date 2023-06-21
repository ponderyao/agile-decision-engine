package io.github.ponderyao.decision.value;

import java.util.Map;

/**
 * DecisionResult: 决策执行结果
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class DecisionResult {
    
    private boolean matched;
    
    private String decisionRuleName;
    
    private Map<String, Object> responseData;
    
    public DecisionResult(boolean matched, String decisionRuleName, Map<String, Object> responseData) {
        this.matched = matched;
        this.decisionRuleName = decisionRuleName;
        this.responseData = responseData;
    }
    
    public static DecisionResult success(String decisionRuleName, Map<String, Object> responseData) {
        return new DecisionResult(true, decisionRuleName, responseData);
    }
    
    public static DecisionResult fail(String reason) {
        return new DecisionResult(false, reason, null);
    }

    public boolean isMatched() {
        return matched;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }

    public String getDecisionRuleName() {
        return decisionRuleName;
    }

    public void setDecisionRuleName(String decisionRuleName) {
        this.decisionRuleName = decisionRuleName;
    }

    public Map<String, Object> getResponseData() {
        return responseData;
    }

    public void setResponseData(Map<String, Object> responseData) {
        this.responseData = responseData;
    }
}
