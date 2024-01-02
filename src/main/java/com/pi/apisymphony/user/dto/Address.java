package com.pi.apisymphony.user.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Address {
    public static final String CITY_ASSERT = "City cannot be null or blank";
    public static final String STREET_ASSERT = "Street cannot be null or blank";
    public static final String ZIPCODE_ASSERT = "Zipcode cannot be null or blank";
    public static final String GEOLOCATION_ASSERT = "Geolocation cannot be null";
    private String city;
    private String street;
    private int number;
    private String zipcode;
    private GeoLocation geolocation;
    public void validateAddress(List<String> errors){
        if(this.city == null || this.city.isBlank()){
            errors.add(CITY_ASSERT);
        }
        if(this.street == null || this.street.isBlank()){
            errors.add(STREET_ASSERT);
        }
        if(this.zipcode == null || this.zipcode.isBlank()){
            errors.add(ZIPCODE_ASSERT);
        }
        if(this.geolocation == null){
            errors.add(GEOLOCATION_ASSERT);
        }
        else{
            this.geolocation.validateGeoLocation(errors);
        }
    }
}
