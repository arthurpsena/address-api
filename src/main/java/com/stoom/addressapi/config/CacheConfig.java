package com.stoom.addressapi.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {

    public static final String GOOGLE_MAPS_CACHE = "googleMapsCache";

}
