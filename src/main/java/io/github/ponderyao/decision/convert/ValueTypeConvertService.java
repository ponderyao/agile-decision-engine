package io.github.ponderyao.decision.convert;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.stereotype.Component;

import io.github.ponderyao.decision.common.constant.ExceptionConstants;
import io.github.ponderyao.decision.common.exception.DecisionEngineException;

/**
 * ValueTypeConvertService: 数据类型转换服务
 *
 * @author PonderYao
 * @since 1.0.0
 */
@Component
public class ValueTypeConvertService {

    public static final Logger log = LoggerFactory.getLogger(ValueTypeConvertService.class);
    
    private final ConversionService conversionService;
    
    @Autowired
    public ValueTypeConvertService() {
        DefaultConversionService conversionService = new DefaultConversionService();
        conversionService.addConverter(new LinkedHashMapConverter());
        this.conversionService = conversionService;
    }
    
    public Object convert(Object value, String convertType) {
        WrapperValueType valueType = WrapperValueType.getByCode(convertType);
        if (ObjectUtils.isEmpty(valueType)) {
            throw new DecisionEngineException(ExceptionConstants.CONDITION_STUB.VALUE_TYPE, convertType);
        }
        try {
            return conversionService.convert(value, valueType.getType());
        } catch (RuntimeException exception) {
            log.error(exception.getMessage(), exception);
            throw new DecisionEngineException(ExceptionConstants.CONDITION_STUB.CONVERT, value.toString(), convertType, exception.getMessage());
        }
    }
    
}
