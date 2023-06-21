package io.github.ponderyao.decision.convert;

import java.util.LinkedHashMap;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * LinkedHashMapConverter: 自定义字符串与链式哈希表数据转换器
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class LinkedHashMapConverter implements Converter<String, LinkedHashMap<String, Object>> {
    
    @Override
    @SuppressWarnings("unchecked")
    public LinkedHashMap<String, Object> convert(String source) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(source, LinkedHashMap.class);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    
}
