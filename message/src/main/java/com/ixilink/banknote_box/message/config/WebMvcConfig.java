package com.ixilink.banknote_box.message.config;

import com.ixilink.banknote_box.common.exception.GlobalExceptionHandler;
import com.ixilink.banknote_box.common.interceptor.CorsInterceptor;
import com.ixilink.banknote_box.common.interceptor.EncodingInterceptor;
import com.ixilink.banknote_box.common.interceptor.ImageInterceptor;
import com.ixilink.banknote_box.common.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * description:
 * author: 张俊
 * date: 2019-11-04 17:27
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 静态资源文件
     * @param registry 资源库
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //静态
        registry.addResourceHandler("/static/**") .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/page/**") .addResourceLocations("classpath:/templates/");

        String filePath = System.getProperty("user.dir");
        //自定义路径
        registry.addResourceHandler("/images/**").addResourceLocations("file:"+filePath+"/images/");
        registry.addResourceHandler("/files/**").addResourceLocations("file:"+filePath+"/files/");
        registry.addResourceHandler("/temp/**").addResourceLocations("file:"+filePath+"/temp/");
    }

    /**
     * 拦截器配置
     * @param registry 拦截链
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //多个拦截器组成一个拦截器链

        //编码拦截器
        registry.addInterceptor(encodingInterceptor())
                //拦截
                .addPathPatterns("/**");

        //跨域拦截器
        registry.addInterceptor(corsingInterceptor())
                //拦截
                .addPathPatterns("/**");

        //登录拦截器
        //registry.addInterceptor(loginInterceptor())
        //        //拦截
        //        .addPathPatterns("/**")
        //        //不拦截
        //		.excludePathPatterns( "/v2/**", "/**/v2/**", "/webjars/**", "/swagger-ui.html","/swagger-resources/**", "/test","/static/**","/test/**","/**/*.html","/**/*.js","/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg", "/**/*.jpeg");

        //图片拦截器
        registry.addInterceptor(imageInterceptor())
                //拦截
                .addPathPatterns("/**")
                //不拦截
        		.excludePathPatterns();
    }

    //编码拦截器bean类
    @Bean
    public EncodingInterceptor encodingInterceptor() {
        return new EncodingInterceptor();
    }
    //跨域拦截器bean类
    @Bean
    public CorsInterceptor corsingInterceptor() {
        return new CorsInterceptor();
    }
    //图片访问拦截器bean类
    @Bean
    public ImageInterceptor imageInterceptor() {
        return new ImageInterceptor();
    }
    //图片访问拦截器bean类
    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }
    //全局异常捕获
    @Bean
    public GlobalExceptionHandler exceptionHandler() {
        return new GlobalExceptionHandler();
    }
}