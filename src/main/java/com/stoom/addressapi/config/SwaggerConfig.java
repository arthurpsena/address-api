package com.stoom.addressapi.config;

import com.stoom.addressapi.AddressApiApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage(AddressApiApplication.class.getPackage().getName())).build()
                .apiInfo(apiInfo()).tags(new Tag("Address API", "Api to manage Addresses")).useDefaultResponseMessages(false)
                .genericModelSubstitutes(ResponseEntity.class);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Smoot - Solucao E-commerce").build();
    }

}
