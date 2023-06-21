package io.github.ponderyao.decision.util;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import groovy.lang.MissingMethodException;
import groovy.lang.Script;
import io.github.ponderyao.decision.common.constant.ExceptionConstants;
import io.github.ponderyao.decision.common.exception.ScriptEngineException;

/**
 * GroovyUtils: Groovy脚本工具类
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class GroovyUtils {

    /**
     * 执行 Groovy 脚本
     * 
     * @param script groovy脚本
     * @param method 脚本中定义的用于执行的主方法名称
     * @param properties 成员变量与值
     * @param parameters 方法传参
     */
    public static Object invoke(String script, String method, Map<String, Object> properties, Map<String, Object> parameters) {
        Class<GroovyObject> groovyClass = parseClass(script);
        GroovyObject groovyObject = createInstance(groovyClass);
        if (groovyObject instanceof Script) {
            // 暂时不支持纯脚本类型的groovy实现，需存在标准的类结构
            throw new ScriptEngineException(groovyClass.getSimpleName(), ExceptionConstants.SCRIPT.INSTANTIATION, "the class of script is not a standard groovy class, please ensure setting script with class structure");
        }
        processProperties(groovyObject, properties);
        return invoke(groovyObject, method, parameters);
    }
    
    private static Object invoke(GroovyObject groovyObject, String method, Map<String, Object> parameters) {
        try {
            if (CollectionUtils.isEmpty(parameters)) {
                return groovyObject.invokeMethod(method, new Object[0]);
            }
            return groovyObject.invokeMethod(method, parameters);
        } catch (MissingMethodException e) {
            if (StringUtils.equals(method, e.getMethod())) {
                throw new ScriptEngineException(groovyObject.getClass().getSimpleName(), ExceptionConstants.SCRIPT.METHOD, method);
            } else {
                throw new ScriptEngineException(groovyObject.getClass().getSimpleName(), ExceptionConstants.SCRIPT.EXECUTION, e.getMessage());
            }
        } catch (Exception e) {
            throw new ScriptEngineException(groovyObject.getClass().getSimpleName(), ExceptionConstants.SCRIPT.EXECUTION, e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    private static Class<GroovyObject> parseClass(String script) {
        try (GroovyClassLoader classLoader = new GroovyClassLoader()) {
            return (Class<GroovyObject>) classLoader.parseClass(script);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    private static GroovyObject createInstance(Class<GroovyObject> groovyClass) {
        try {
            return groovyClass.newInstance();
        } catch (Exception e) {
            throw new ScriptEngineException(groovyClass.getSimpleName(), ExceptionConstants.SCRIPT.INSTANTIATION, e.getMessage());
        }
    }
    
    private static void processProperties(GroovyObject groovyObject, Map<String, Object> properties) {
        // 将必要对象设置为内部变量，可以在方法中通过 this.getProperty() 获取
        properties.forEach(groovyObject::setProperty);
    }
    
}
