package com.training.hrm.configs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springdoc.core.properties.SwaggerUiConfigParameters;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SwaggerConfig implements WebMvcConfigurer {

    @Bean
    // Nhóm các endpoint API được chỉ định vào một nhóm có tên "v1"
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("v1")
                .pathsToMatch("/**")
                .build();
    }

    // Cấu hình các tham số, tắt kiểm tra validator url
//    @Bean
//    public SwaggerUiConfigProperties swaggerUiConfigProperties() {
//        SwaggerUiConfigProperties swaggerUiConfigProperties = new SwaggerUiConfigProperties();
//        swaggerUiConfigProperties.setValidatorUrl(null);
//        return swaggerUiConfigProperties;
//    }
//
//    @Bean
//    public SwaggerUiConfigParameters swaggerUiConfigParameters(SwaggerUiConfigProperties swaggerUiConfigProperties) {
//        SwaggerUiConfigParameters swaggerUiConfigParameters = new SwaggerUiConfigParameters();
//        swaggerUiConfigParameters.setValidatorUrl(swaggerUiConfigProperties.getValidatorUrl());
//        return swaggerUiConfigParameters;
//    }

    @Bean
    // Cấu hình bảo mật sử dụng JWT
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key", new SecurityScheme()
                                .name("bearer-key")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}
