package io.github.ponderyao.decision.value;

import java.util.Map;

/**
 * ScriptRequest: 脚本执行请求
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class ScriptRequest {
    
    /** 脚本内容 */
    private String script;
    
    /** 脚本方法 */
    private String method;
    
    /** 脚本属性 */
    private Map<String, Object> properties;
    
    /** 脚本参数 */
    private Map<String, Object> params;
    
    public ScriptRequest(String script, String method, Map<String, Object> properties, Map<String, Object> params) {
        this.script = script;
        this.method = method;
        this.properties = properties;
        this.params = params;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
