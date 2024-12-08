package com.programing.nhom6.api_gateway.config;

import com.programing.nhom6.api_gateway.repository.IdentityClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import java.util.Arrays;

@ControllerAdvice
public class WebClientConfiguration {
    
    @Value("${cors.allowed-origins}")
    private String[] allowedOrigins;
    
    @Value("${cors.allowed-methods}")
    private String[] allowedMethods;
    
    @Value("${cors.allowed-headers}")
    private String[] allowedHeaders;
    
    @Value("${cors.max-age}")
    private Long maxAge;
    
    @Value("${identity.service.url}")
    private String identityServiceUrl;

    
    @Bean
    WebClient webClient() {
        return WebClient.builder()
                .baseUrl(identityServiceUrl)
                .build();
    }

    @Bean
    CorsWebFilter corsWebFilter() {
    CorsConfiguration config = new CorsConfiguration();
    
    if (allowedOrigins.length == 1 && allowedOrigins[0].equals("*")) {
        // Trường hợp cho phép tất cả origins
        config.addAllowedOriginPattern("*");
        config.setAllowCredentials(false);  // PHẢI set false khi dùng *
    } else {
        // Trường hợp chỉ định rõ origins
        config.setAllowedOrigins(Arrays.asList(allowedOrigins));
        config.setAllowCredentials(true);
    }
    
    config.setAllowedMethods(Arrays.asList(allowedMethods));
    config.setAllowedHeaders(Arrays.asList(allowedHeaders));
    config.setMaxAge(maxAge);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);

    return new CorsWebFilter(source);
}

    @Bean
    IdentityClient identityClient(WebClient webClient) {
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(webClient))
                .build();

        return httpServiceProxyFactory.createClient(IdentityClient.class);
    }
}