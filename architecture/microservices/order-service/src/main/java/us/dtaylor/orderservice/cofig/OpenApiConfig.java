package us.dtaylor.orderservice.cofig;


import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI orderServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Order Service API")
                        .description("API documentation for the Order Service")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("SOMS Team")
                                .email("support@soms.com")
                        ));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("orders")
                .pathsToMatch("/orders/**")
                .build();
    }
}

