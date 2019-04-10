package org.lanqiao.thesismanager.config;

import org.lanqiao.thesismanager.interceptors.ManagerLoginInterceptor;
import org.lanqiao.thesismanager.interceptors.StudentLoginInterceptor;
import org.lanqiao.thesismanager.interceptors.TeacherLoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Auther: WDS
 * @Date: 2019/1/11 15:00
 * @Description:
 */
@Configuration
public class DefaultConfig implements WebMvcConfigurer {
    //视图转化器配置
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        registry.addViewController("/").setViewName("index");

        //管理页面
        registry.addViewController("/manager").setViewName("/manager/index");
        registry.addViewController("/manager/").setViewName("/manager/index");
        registry.addViewController("/manager/userInfo").setViewName("/manager/userInfo");
        registry.addViewController("/manager/toUpdatePassword").setViewName("/manager/updatePassword");//进入修改密码页面
        registry.addViewController("/manager/toUserRegister").setViewName("/manager/userRegister");

        //教师页面
        registry.addViewController("/teacher").setViewName("/teacher/index");
        registry.addViewController("/teacher/").setViewName("/teacher/index");
        registry.addViewController("/teacher/userInfo").setViewName("/teacher/userInfo");
        //跳转到修改密码页面
        registry.addViewController("/teacher/toUpdatePassword").setViewName("/teacher/updatePassword");
        //跳转到注册页面
        registry.addViewController("/teacher/toUserRegister").setViewName("/teacher/userRegister");

        //学生页面
        registry.addViewController("/student").setViewName("/student/index");
        registry.addViewController("/student/").setViewName("/student/index");
        registry.addViewController("/student/userInfo").setViewName("/student/userInfo");
        //跳转到修改密码页面
        registry.addViewController("/student/toUpdatePassword").setViewName("/student/updatePassword");
        //跳转到注册页面
        registry.addViewController("/student/toUserRegister").setViewName("/student/userRegister");
    }

    //拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ManagerLoginInterceptor())
                .addPathPatterns("/manager/*","/manager")
                .excludePathPatterns("/manager/login","/manager/register","/manager/toUserRegister","/druid/*");
        registry.addInterceptor(new StudentLoginInterceptor())
                .addPathPatterns("/student/*","/student")
                .excludePathPatterns("/student/login","/student/register","/student/toUserRegister","/druid/*");
        registry.addInterceptor(new TeacherLoginInterceptor())
                .addPathPatterns("/teacher/*","/teacher")
                .excludePathPatterns("/teacher/login","/teacher/register","/teacher/toUserRegister","/druid/*");
    }

    //日期格式化
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new DateFormatter("yyyy-MM-dd"));
    }

    //登录页面做国际化
    @Bean
    public LocaleResolver localeResolver(){
        return new MyLocaleResolver();
    }
}
