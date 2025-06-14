package us.dtaylor.orderservice.cofig;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestClientConfig {

    @Bean
    public RestTemplate productRestTemplate(RestTemplateBuilder builder) {
        return builder.rootUri("http://product-service:8081").build();
    }
    @Bean
    public RestTemplate paymentRestTemplate(RestTemplateBuilder builder) {
        return builder.rootUri("http://payment-service:8083").build();
    }
}
