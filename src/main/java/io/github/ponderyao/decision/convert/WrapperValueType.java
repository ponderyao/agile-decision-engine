package io.github.ponderyao.decision.convert;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * WrapperValueType: 包装数据类型枚举
 *
 * @author PonderYao
 * @since 1.0.0
 */
public enum WrapperValueType {
    
    BOOLEAN("Boolean", Boolean.class),
    SHORT("Short", Short.class),
    INTEGER("Integer", Integer.class),
    LONG("Long", Long.class),
    FLOAT("Float", Float.class),
    DOUBLE("Double", Double.class),
    STRING("String", String.class),
    BIG_DECIMAL("BigDecimal", BigDecimal.class),
    DATE("Date", Date.class),
    LIST("List", List.class),
    MAP("Map", LinkedHashMap.class);
    
    private final String code;
    
    private final Class<?> type;
    
    WrapperValueType(String code, Class<?> type) {
        this.code = code;
        this.type = type;
    }
    
    public String getCode() {
        return this.code;
    }
    
    public Class<?> getType() {
        return this.type;
    }
    
    public static WrapperValueType getByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (WrapperValueType valueType : WrapperValueType.values()) {
            if (StringUtils.equals(valueType.getCode(), code)) {
                return valueType;
            }
        }
        return null;
    }
    
}
