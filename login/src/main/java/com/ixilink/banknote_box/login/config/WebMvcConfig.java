package com.ixilink.banknote_box.login.config;

import com.ixilink.banknote_box.common.interceptor.EncodingInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * description:
 * author: 张俊
 * date: 2019-11-04 17:27
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${origin}")
    private String origin;

    /**
     * 静态资源文件
     * @param registry 资源库
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //静态
        registry.addResourceHandler("/static/**") .addResourceLocations("classpath:/static/");
    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
        .allowedOrigins(origin)
        .allowedHeaders("x-requested-with","Authorization","Content-Type","platformType","token")
        .allowedMethods("PUT","POST","GET","DELETE","OPTIONS","PATCH")
        .allowCredentials(true);
    }

    /**
     * 拦截器配置
     * @param registry 拦截链
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //多个拦截器组成一个拦截链

        //编码拦截器
        registry.addInterceptor(encodingInterceptor())
                //拦截
                .addPathPatterns("/**");
    }

    //编码拦截器bean类
    @Bean
    public EncodingInterceptor encodingInterceptor() {
        return new EncodingInterceptor();
    }
}