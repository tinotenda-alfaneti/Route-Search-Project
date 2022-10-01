package com.icpproject;

public class CityNotFoundException extends Exception{

    public CityNotFoundException() {
        this ("The city or country your entered could not be found");
    }

    public CityNotFoundException (String message) {
        super(message);
    }
}
