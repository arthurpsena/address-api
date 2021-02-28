package com.stoom.addressapi.config;

import com.google.maps.GeoApiContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoogleMapsConfig {

    private final String apiKey;

    public GoogleMapsConfig(@Value("${address-api.integration.google-maps.key}") final String apiKey) {
        this.apiKey = apiKey;
    }

    @Bean
    public GeoApiContext geoApiContext() {
        return new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();
    }

}
