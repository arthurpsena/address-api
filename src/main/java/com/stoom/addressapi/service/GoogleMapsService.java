package com.stoom.addressapi.service;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.stoom.addressapi.exception.CommunicationException;
import com.stoom.addressapi.model.dto.request.AddressRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

import static com.stoom.addressapi.config.CacheConfig.GOOGLE_MAPS_CACHE;

@Service
@RequiredArgsConstructor
public class GoogleMapsService {

    private final GeoApiContext geoApiContext;
    @Resource
    private GoogleMapsService self;

    public void setCoordinates(final AddressRequest request) {
        final LatLng location = self.getLocation(request.getAddress());
        request.setLatitude(Double.toString(location.lat));
        request.setLongitude(Double.toString(location.lng));
    }

    @Cacheable(cacheNames = GOOGLE_MAPS_CACHE, unless = "#result == null")
    public LatLng getLocation(final String address) {
        try {
            final GeocodingResult[] results = GeocodingApi.geocode(geoApiContext,
                    address).await();
            return results[0].geometry.location;
        } catch (ApiException | InterruptedException | IllegalStateException | IOException e) {
            throw new CommunicationException();
        }
    }

}
