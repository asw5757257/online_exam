package com.zyw.online_exam.graduation_design.filter;

import com.zyw.online_exam.graduation_design.Interceprot.TeacherInterceptor;
import com.zyw.online_exam.graduation_design.utils.CookieUtil;
import com.zyw.online_exam.graduation_design.utils.RedisPoolUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/17 15:18
 */
public class SessionExpireFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(SessionExpireFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("过滤器开始");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = CookieUtil.readCookie(request);
        if (StringUtils.isNotEmpty(token)) {
            log.info("过滤器执行业务");
            String userStr = RedisPoolUtil.get(token);
            if (StringUtils.isNotBlank(userStr)) {
                RedisPoolUtil.expire(token, 60 * 30);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
        log.info("过滤器结束")   ;
    }

    @Override
    public void destroy() {

    }
}
