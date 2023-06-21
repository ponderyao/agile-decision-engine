package io.github.ponderyao.decision.value;

import java.util.Map;

/**
 * ScriptResult: 脚本执行结果
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class ScriptResult {
    
    /** 是否成功 */
    private Boolean success;
    
    /** 失败信息 */
    private String errMsg;
    
    /** 返回数据 */
    private Map<String, Object> responseData;
    
    public ScriptResult(Boolean success, String errMsg, Map<String, Object> responseData) {
        this.success = success;
        this.errMsg = errMsg;
        this.responseData = responseData;
    }
    
    public static ScriptResult success(Map<String, Object> responseData) {
        return new ScriptResult(true, null, responseData);
    }
    
    public static ScriptResult fail(String errMsg) {
        return new ScriptResult(false, errMsg, null);
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Map<String, Object> getResponseData() {
        return responseData;
    }

    public void setResponseData(Map<String, Object> responseData) {
        this.responseData = responseData;
    }
}
