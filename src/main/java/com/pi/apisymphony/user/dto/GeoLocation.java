package com.pi.apisymphony.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GeoLocation {
    private static final String LATITUDE_ASSERT = "Latitude cannot be null or blank";
    private static final String LONGITUDE_ASSERT = "Longitude cannot be null or blank";
    @JsonProperty("lat")
    @SerializedName("lat")
    private String lat;
    
    @JsonProperty("long")
    @SerializedName("long")
    private String longitude;
    public void validateGeoLocation(List<String> errors){
        if(this.lat == null || this.lat.isBlank()){
            errors.add(LATITUDE_ASSERT);
        }
        if(this.longitude == null || this.longitude.isBlank()){
            errors.add(LONGITUDE_ASSERT);
        }
    }
}
