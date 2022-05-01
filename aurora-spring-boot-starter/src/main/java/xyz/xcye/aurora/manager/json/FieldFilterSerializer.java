package xyz.xcye.aurora.manager.json;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * @author qsyyke
 */


public class FieldFilterSerializer {
    /**
     * 想要保留的字段标识
     */
    private static final String DYNAMIC_INCLUDE = "DYNAMIC_INCLUDE";

    /**
     * 想要过滤的字段标识
     */
    private static final String DYNAMIC_EXCLUDE = "DYNAMIC_EXCLUDE";

    /**
     * jackson核心类 过滤属性全部由这个类完成
     */
    private final ObjectMapper mapper = new ObjectMapper();

    @JsonFilter(DYNAMIC_EXCLUDE)
    interface DynamicExclude {}

    @JsonFilter(DYNAMIC_EXCLUDE)
    interface DynamicInclude {}

    public void filter(Class<?> clazz, String... propertyArray) {
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.serializeAllExcept(propertyArray);
        SimpleFilterProvider provider = new SimpleFilterProvider().addFilter(DYNAMIC_EXCLUDE, filter);
        mapper.setFilterProvider(provider);
        mapper.addMixIn(clazz, DynamicExclude.class);
    }

    public String toJSONString(Object object) throws JsonProcessingException {
        //解决jackson2无法反序列化LocalDateTime的问题
        //这里要注意时间属性上要加入 @JsonFormat 注解 否则无法正常解析
        mapper.registerModule(new JavaTimeModule());
        //将类转换成json字符串返回
        return mapper.writeValueAsString(object);
    }
}
