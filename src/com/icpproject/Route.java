package com.icpproject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Class to represent the route
 */
public class Route {

    private String airportCode;
    private String airline;
    private int stops;
    private double distance;

    /**
     * Generating getters and setters
     * @return - depends on the getter return type
     */
    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public int getStops() {
        return stops;
    }

    public void setStops(int stops) {
        this.stops = stops;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public static HashMap<String, ArrayList<Route>> getRoutesMap() {
        return routesMap;
    }

    public static void setRoutesMap(HashMap<String, ArrayList<Route>> routesMap) {
        Route.routesMap = routesMap;
    }

    /**
     *Constructor
     * @param airportCode - the code of the airport
     * @param airline - the airline code
     * @param stops -  the number of stops
     */
    Route(String airportCode, String airline, int stops, double distance) {
        this.airportCode = airportCode;
        this.airline = airline;
        this.stops = stops;
        this.distance = distance;
    }

    /**
     * Constructor
     * @param airportCode - the airport code
     */
    Route(String airportCode) {
        this(airportCode, "",0, 0);
    }

    static HashMap<String, ArrayList<Route>> routesMap = new HashMap<String, ArrayList<Route>>();

    /**
     * Reading the routes csv file
     * @return - a hashmap with the code and the destinations
     */
    public static HashMap<String, ArrayList<Route>> readRoutesFile() {

        //Generate destinations from a given start city

        try {
            Scanner routes = new Scanner(new FileReader("routes.csv"));
            while (routes.hasNextLine()) {
                String[] route = routes.nextLine().split(",");
                ArrayList<Route> temp = new ArrayList<Route>();
                String SrcAirCode = route[2];
                String DesAirCode = route[4];

                double[] src = Airport.getLongLat(SrcAirCode);
                double[] des = Airport.getLongLat(DesAirCode);
                //Calculating the distance
                double distance = HaverSineDistance.haversine(src[1], src[0], des[1], des[0]);
                Route tempRoute = new Route(DesAirCode, route[0], Integer.parseInt(route[7]), distance);
                //checking if the source code is in the HashMap
                if (routesMap.containsKey(SrcAirCode)) {
                    temp = routesMap.get(SrcAirCode);
                    if (!temp.contains(tempRoute)) {
                        temp.add(tempRoute);
                        routesMap.put(SrcAirCode, temp);
                    }

                } else {
                    temp.add(tempRoute);
                    routesMap.put(SrcAirCode, temp);
                }
            }
        } catch (FileNotFoundException fnf) {
            fnf.printStackTrace();
        }
        return routesMap;
    }


}
