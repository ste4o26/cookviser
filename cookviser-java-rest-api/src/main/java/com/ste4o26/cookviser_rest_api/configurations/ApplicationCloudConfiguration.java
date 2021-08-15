package com.ste4o26.cookviser_rest_api.configurations;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ApplicationCloudConfiguration {

    @Value("${com.cloudinary.api_key}")
    private String apiKey;

    @Value("${com.cloudinary.api_secret}")
    private String apiSecret;

    @Value("${com.cloudinary.cloud_name}")
    private String cloudName;

    @Bean
    public Cloudinary cloudinary() {
        Map<String, Object> configurations = new HashMap<>();
        configurations.put("api_key", this.apiKey);
        configurations.put("api_secret", this.apiSecret);
        configurations.put("cloud_name", this.cloudName);

        return new Cloudinary(configurations);
    }
}
