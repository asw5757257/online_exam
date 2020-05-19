package com.zyw.online_exam.graduation_design.config;

import com.zyw.online_exam.graduation_design.Interceprot.ManagerInterceptor;
import com.zyw.online_exam.graduation_design.Interceprot.StudentInterceptor;
import com.zyw.online_exam.graduation_design.Interceprot.TeacherInterceptor;
import com.zyw.online_exam.graduation_design.filter.SessionExpireFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/17 16:15
 */
@Configuration
public class MyWebConfig implements WebMvcConfigurer {
    @Autowired
    private TeacherInterceptor teacherInterceptor;
    @Autowired
    private StudentInterceptor studentInterceptor;
    @Autowired
    private ManagerInterceptor managerInterceptor;
    //过滤器配置
    @Bean
    public FilterRegistrationBean filterRegist() {
        FilterRegistrationBean frBean = new FilterRegistrationBean();
        frBean.setFilter(new SessionExpireFilter());
        frBean.addUrlPatterns("/*");
        //System.out.println("filter");
        return frBean;
    }

    //拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(studentInterceptor).
                addPathPatterns("/student/*").
                excludePathPatterns("/student/login").
                excludePathPatterns("/student/getCheckCodeByEmail").
                excludePathPatterns("/student/checkCheckCode");
        registry.addInterceptor(teacherInterceptor).
                addPathPatterns("/teacher/*").
                addPathPatterns("/paper/*").
                addPathPatterns("/question/*").
                excludePathPatterns("/teacher/login").
                excludePathPatterns("/teacher/getCheckCodeByEmail").
                excludePathPatterns("/teacher/checkCheckCode");
        registry.addInterceptor(managerInterceptor).
                addPathPatterns("/manage/*").
                excludePathPatterns("/manage/login");
    }
}
