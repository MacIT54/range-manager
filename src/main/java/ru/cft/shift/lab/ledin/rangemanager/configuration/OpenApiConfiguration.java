package ru.cft.shift.lab.ledin.rangemanager.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.utils.PropertyResolverUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
@RequiredArgsConstructor
public class OpenApiConfiguration {
    private final PropertyResolverUtils propertyResolverUtils;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info().title(message("api.title"))
                        .description(message("api.description"))
                        .contact(new Contact()
                                .name("Ledin Daniil")
                                .url("https://github.com/MacIT54/")
                                .email("daniil.ledkin@mail.ru"))
                        .version("v1.0.0")
                );
    }

    private String message(String property) {
        return this.propertyResolverUtils.resolve(property, Locale.getDefault());
    }
}