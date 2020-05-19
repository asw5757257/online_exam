package com.zyw.online_exam.graduation_design.Interceprot;

import com.zyw.online_exam.graduation_design.pojo.Teacher;
import com.zyw.online_exam.graduation_design.utils.CookieUtil;
import com.zyw.online_exam.graduation_design.utils.JacksonUtil;
import com.zyw.online_exam.graduation_design.utils.RedisPoolUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/17 15:51
 */
@Component
public class TeacherInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(TeacherInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        String methodName = handlerMethod.getMethod().getName();
        String className = handlerMethod.getBean().getClass().getName();
        log.info("拦截类名：{}，方法名：{}", className, methodName);
        Teacher teacher = null;
        //从redis获取用户信息
        String token = CookieUtil.readCookie(request);
        if (StringUtils.isNotEmpty(token)) {
            String studentStr = RedisPoolUtil.get(token);
            teacher = JacksonUtil.stringToObj(studentStr, Teacher.class);
        }
        //todo
        /*if(teacher == null){
            response.sendRedirect( "/index");
        }*/

        //判断用户信息是否为空或者身份权限不对
        return teacher != null && (StringUtils.equals(teacher.getRole(), "1"))||BaseInterceptor.check(response,teacher);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }


}
