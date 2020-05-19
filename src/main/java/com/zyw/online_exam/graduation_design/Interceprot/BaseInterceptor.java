package com.zyw.online_exam.graduation_design.Interceprot;

import com.zyw.online_exam.graduation_design.Dto;
import com.zyw.online_exam.graduation_design.utils.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/17 15:45
 */
public class BaseInterceptor {
    private static final Logger log = LoggerFactory.getLogger(BaseInterceptor.class);
    public static boolean check(HttpServletResponse response,Object object){
        response.reset();
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        PrintWriter printWriter;
        try {
            printWriter = response.getWriter();
            if (object == null) {
                printWriter.print(JacksonUtil.objToString(Dto.getFailed("操作被拦截，请先登录")));
            } else {
                printWriter.print(JacksonUtil.objToString(Dto.getNoAuthority( "无该操作权限")));
            }
            printWriter.flush();
            printWriter.close();
        } catch (IOException e) {
            log.error("拦截请求是发生异常", e);
            return false;
        }
        return false;
    }
}
