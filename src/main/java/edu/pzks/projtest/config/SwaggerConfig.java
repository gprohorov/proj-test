package edu.pzks.projtest.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sm.pro.smrestapi.util.OpenApiTagOrderUtils;

import java.util.List;

@OpenAPIDefinition(
        info = @Info(
                title = "Docs for Solasmind REST API",
                description = "Swagger module",
                version = "version 1.6 from 13 June",
                contact = @Contact(
                        name = "Your contact",
                        email = "Your email"
                )
        )
)
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer",
        in = SecuritySchemeIn.HEADER
)
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {
    private final ApplicationContext applicationContext;
    @Bean
    public GroupedOpenApi defaultOpenApi() {
        return GroupedOpenApi.builder()
                .group("Default group")
                .pathsToMatch("/**")
                .addOpenApiCustomizer(openApi -> {
                    if (openApi.getTags() != null && !openApi.getTags().isEmpty()) {
                        OpenApiTagOrderUtils.TagScanResult scanResult =
                                OpenApiTagOrderUtils.scanTagsForGroup(applicationContext, "");
                        List<Tag> sortedTags =
                                OpenApiTagOrderUtils.sortTagsForGroup(openApi.getTags(), scanResult);
                        openApi.tags(sortedTags);
                    }
                })
                .build();
    }
}
