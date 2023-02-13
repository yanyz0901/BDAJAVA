package com.dsplab.bda.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket customDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.dsplab.bda.controller"))
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("BDA", "https://gitee.com/yan-yuzheng/BDAJAVA", "yanyuzhengtju@163.com");
        return new ApiInfoBuilder()
                .title("BDA软件架构")
                .description("dsplab生物设计自动化软件")
                .contact(contact)   // 联系方式
                .version("1.0.0")  // 版本
                .build();
    }
}