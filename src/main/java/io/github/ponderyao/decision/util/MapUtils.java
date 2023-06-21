package io.github.ponderyao.decision.util;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * MapUtils: Map 工具类
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class MapUtils {
    
    @SuppressWarnings("unchecked")
    public static Map<String, Object> toLinkedHashMap(Object obj) {
        if (obj instanceof LinkedHashMap) {
            return (Map<String, Object>) obj;
        }
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        for (Field field : obj.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Object value = field.get(obj);
                map.put(field.getName(), value);
            } catch (IllegalAccessException exception) {
                throw new RuntimeException(exception);
            }
        }
        return map;
    }
    
}
