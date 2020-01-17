package com.ixilink.banknote_box.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket createRestApi() {//wagger2就只要一个Docket实例
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				// TODO 如果是线上环境，添加路径过滤，设置为全部都不符合
				// .paths(PathSelectors.none())
				//配置扫描接口的方式
				.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
				.paths(PathSelectors.any())//path是过滤
				.build()
//				.host("xxx.com:8888/xm/service")
				.globalOperationParameters(setHeaderToken());
	}
	//配置文档页面的消息
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("智慧金库开发接口文档")
				.description("更多请关注官网：http://www.ixilink.com/")
				.termsOfServiceUrl("http://www.ixilink.com/")
				.contact(new Contact("张皓峰", "https://blog.csdn.net/z694644032", "cloud_haofeng@163.com"))
				.description("智慧金库开发接口文档")
				.version("1.0")
				.build();
	}

	private List<Parameter> setHeaderToken() {
		List<Parameter> pars = new ArrayList<>();

		ParameterBuilder tokenPar = new ParameterBuilder();
		ParameterBuilder platformTypePar = new ParameterBuilder();
		tokenPar.name("token").description("token认证").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
		platformTypePar.name("platformType").description("平台标志").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
		pars.add(tokenPar.build());
		pars.add(platformTypePar.build());
		return pars;
	}
}
