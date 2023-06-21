package io.github.ponderyao.decision.engine;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import io.github.ponderyao.decision.util.GroovyUtils;
import io.github.ponderyao.decision.util.MapUtils;
import io.github.ponderyao.decision.value.ScriptRequest;
import io.github.ponderyao.decision.value.ScriptResult;

/**
 * GroovyScriptEngine: Groovy脚本执行引擎
 *
 * @author PonderYao
 * @since 1.0.0
 */
@Component
class GroovyScriptEngine implements ScriptEngine {
    
    private static final String INAPPROPRIATE_INVOKE_RESULT = "the data format of script execution result does not meeting the requirement";
    
    @Override
    public ScriptResult execute(ScriptRequest scriptRequest) {
        Object invokeResult = GroovyUtils.invoke(scriptRequest.getScript(), scriptRequest.getMethod(), scriptRequest.getProperties(), scriptRequest.getParams());
        if (!ObjectUtils.isEmpty(invokeResult) && !(invokeResult instanceof Map)) {
            return ScriptResult.fail(INAPPROPRIATE_INVOKE_RESULT);
        }
        return ScriptResult.success(MapUtils.toLinkedHashMap(invokeResult));
    }
    
}
