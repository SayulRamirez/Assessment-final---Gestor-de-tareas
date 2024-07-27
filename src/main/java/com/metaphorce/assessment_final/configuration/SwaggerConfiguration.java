package com.metaphorce.assessment_final.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "Assessment final - Gestor de tareas",
                summary = "Assessment final del Career Booster de Metaphorce.",
                version = "SNAPSHOT - 0.0.1v",
                contact = @Contact(
                        name = "Saúl Ramírez",
                        url = "https://www.linkedin.com/in/sayul-ramirez/"
                ))
)
@Configuration
public class SwaggerConfiguration {
}
