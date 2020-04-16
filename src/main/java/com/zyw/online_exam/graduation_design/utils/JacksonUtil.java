package com.zyw.online_exam.graduation_design.utils;


import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.*;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;

/**
 * @author chs
 * @name JacksonUtil
 * @description json序列化和反序列化工具类
 * @create 2018-07-06 9:07
 **/
public class JacksonUtil {

    private static final Logger log = LoggerFactory.getLogger(JacksonUtil.class);

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        //对象的所有属性都序列化
        objectMapper.setSerializationInclusion(Inclusion.ALWAYS);

        //取消默认的timestamps转换
        objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);

        //忽略空对象转json的错误
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);

        //同一日期格式
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        //忽略json中有，java对象中没有对应属性的报错情况
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * @author chs
     * @description Java对象序列化为字符串
     * @createtime 2018-07-06 9:46
     */
    public static <T> String objToString(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.error("对象转字符串时出错", e);
            return null;
        }
    }

    /**
     * @author chs
     * @description Java对象序列化为字符串(格式化输出)
     * @createtime 2018-07-06 9:46
     */
    public static <T> String objToStringPretty(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
            log.error("对象转字符串时出错", e);
            return null;
        }
    }

    /**
     * @author chs
     * @description 字符串反序列化为java对象
     * @createtime 2018-07-06 10:10
     */
    public static <T> T stringToObj(String str, Class<T> clazz) {
        if (StringUtils.isEmpty(str) || clazz == null) {
            return null;
        }
        try {
            return String.class.equals(clazz) ? (T) str : objectMapper.readValue(str, clazz);
        } catch (Exception e) {
            log.error("字符串转对象时出错", e);
            return null;
        }
    }

    /**
     * @author chs
     * @description 字符串反序列化为复杂java对象
     * @createtime 2018-07-06 11:03
     */
    public static <T> T stringToObj(String str, TypeReference<T> typeReference) {
        if (StringUtils.isEmpty(str) || typeReference == null) {
            return null;
        }
        try {
            return typeReference.getType().equals(String.class) ? (T) str : (T) objectMapper.readValue(str, typeReference);
        } catch (Exception e) {
            log.error("字符串转对象时出错", e);
            return null;
        }
    }

    /**
     * @author chs
     * @description 字符串反序列化为复杂java对象
     * @createtime 2018-07-06 11:03
     */
    public static <T> T stringToObj(String str, Class<T> collectionClass, Class<?>... classes) {
        if (StringUtils.isEmpty(str) || collectionClass == null) {
            return null;
        }
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass, classes);
        try {
            return objectMapper.readValue(str, javaType);
        } catch (Exception e) {
            log.error("字符串转对象时出错", e);
            return null;
        }
    }

}
