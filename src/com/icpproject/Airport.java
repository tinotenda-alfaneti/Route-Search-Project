package com.icpproject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Class to represent the airport
 * It has the latitude, longitude and the IATA code
 */
public class Airport {

    double latitude;
    double longitude;
    String airportCode;

    static HashMap<String, Airport> aiportCollections = new HashMap<String, Airport>();


    /**
     * COnstructor
     * @param airportCode - code for the airport
     * @param latitude - the latitude
     * @param longitude - the longitude
     */
    public Airport(String airportCode, double latitude, double longitude) {
        this.airportCode = airportCode;
        this.latitude = latitude;
        this.longitude = longitude;

    }

    /**
     * method for collecting Data
     */
    public static void collectData() {
        try {
            Scanner airportsFile = new Scanner(new FileReader("airports.csv"));
            while (airportsFile.hasNextLine()) {
                String[] line = airportsFile.nextLine().split(",");
                try {
                    Airport temp = new Airport(line[4], Double.parseDouble(line[6]), Double.parseDouble(line[7]));
                    aiportCollections.put(line[4], temp);
                } catch (NumberFormatException nfe) {
                    continue;
                }

            }
        } catch (FileNotFoundException fnf) {
            fnf.printStackTrace();
        }
    }

    /**
     * Method for getting a city's longitude and latitude
     * @param airportCode - the airport code to use for searching for the longitude and latitude
     * @return - ar array with longitude and latitude
     */
    public static double[] getLongLat(String airportCode) {
        double[] result = new double[2];

        if(aiportCollections.containsKey(airportCode)) {
            result[0] = aiportCollections.get(airportCode).longitude;
            result[1] = aiportCollections.get(airportCode).latitude;
        }
        return result;
    }





}
