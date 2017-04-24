package com.airnauts.kaktus.model.facebook;

/**
 * Created by mradziko on 21.12.2015.
 */
public class Location {
    String state;
    String zip;
    Double longitude, latitude;
    String country;
    String city;
    String street;

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }
}
