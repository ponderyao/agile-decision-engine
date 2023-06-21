package io.github.ponderyao.decision.engine;

import io.github.ponderyao.decision.value.ScriptRequest;
import io.github.ponderyao.decision.value.ScriptResult;

/**
 * ScriptEngine: 脚本执行引擎 Marker
 *
 * @author PonderYao
 * @since 1.0.0
 */
public interface ScriptEngine {

    /**
     * 执行引擎
     * 
     * @param request 脚本执行请求
     * @return 脚本执行结果
     */
    ScriptResult execute(ScriptRequest request);
    
}
