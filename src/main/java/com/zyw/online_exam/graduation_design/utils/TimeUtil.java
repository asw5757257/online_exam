package com.zyw.online_exam.graduation_design.utils;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author chs
 * @name TimeUtil
 * @description
 * @create 2017-11-01 12:46
 **/
public class TimeUtil {

    private static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * @author chs
     * @description 字符串转时间
     * @createtime 2017-11-01 12:52
     */
    public static Date strToDate(String dateTimeStr){
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(STANDARD_FORMAT);
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeStr);
        return  dateTime.toDate();
    }

    /**
     * @author chs
     * @description 时间转字符串
     * @createtime 2017-11-01 12:52
     */
    public static String dateToStr(Date date){
        if(date == null){
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(STANDARD_FORMAT);
    }
}
