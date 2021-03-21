package com.nbn.ms.AMS.config;

import java.time.Duration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {

    /**
     * Provides a {@link RestTemplate} that is configured according to the current environment setting.
     *
     * @return configured template
     */
	
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
        		.setReadTimeout(Duration.ofSeconds(30))
        		.setConnectTimeout(Duration.ofSeconds(30))
        		.build();
    }
    
    
}
