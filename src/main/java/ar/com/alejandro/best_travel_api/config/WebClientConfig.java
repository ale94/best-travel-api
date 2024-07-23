package ar.com.alejandro.best_travel_api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value(value = "${api.base.url}")
    private String baseUrl;

    @Value(value = "${api.api-key}")
    private String apikey;

    @Value(value = "${api.api-key.header}")
    private String apiKeyHeader;

    @Bean
    public WebClient currencywebClient() {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(apiKeyHeader, apikey)
                .build();
    }
}
