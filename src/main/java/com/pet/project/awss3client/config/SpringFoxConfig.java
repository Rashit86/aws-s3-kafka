package com.pet.project.awss3client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

@Configuration
public class SpringFoxConfig {

    @Value("${aws-s3-kafka.base-package}")
    private String basePackage;

    public static final String BASIC_AUTH = "basicAuth";

    @Bean
    public Docket api() {

        SecurityReference securityReference = SecurityReference.builder()
                .reference(BASIC_AUTH)
                .scopes(new AuthorizationScope[0])
                .build();

        ArrayList<SecurityReference> reference = new ArrayList<>(1);
        reference.add(securityReference);

        ArrayList<SecurityContext> securityContexts = new ArrayList<>(1);
        securityContexts.add(SecurityContext.builder().securityReferences(reference).build());

        ArrayList<SecurityScheme> auth = new ArrayList<>(1);
        auth.add(new BasicAuth(BASIC_AUTH));

        return new Docket(DocumentationType.SWAGGER_2)
                .securitySchemes(auth)
                .securityContexts(securityContexts)
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        ApiInfoBuilder builder = new ApiInfoBuilder();
        return builder.title("Rent car service")
                .version("1.0")
                .build();
    }
}
