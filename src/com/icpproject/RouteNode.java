package com.icpproject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Class to create a tree of routes
 * Implements comparable so that the PriorityQueue knows how
 * to arrange the elements
 */
public class RouteNode implements Comparable<RouteNode>{

    private Route route;
    private RouteNode prev;

    // Evaluation functions f = heuristic + distance
    // Heuristic is the distance between the current Route and the destination
    private double f;
    private double distance;

    /**
     * Constructor for the class
     * @param route - the start route
     */
    public RouteNode(Route route) {
        this(route, null, 0.0, Double.MAX_VALUE);

    }

    /**
     * Constructor - Overloaded
     * @param route - the current route
     * @param prev - the previous airport visited
     */
    public RouteNode(Route route, RouteNode prev, double distance, double f) {
        this.route = route;
        this.prev = prev;
        this.distance = distance;
        this.f = f;

    }

    /**
     * Getters and Setters
     * @return
     */
    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public RouteNode getPrev() {
        return prev;
    }

    public void setPrev(RouteNode prev) {
        this.prev = prev;
    }

    public double getF() {
        return f;
    }

    public void setF(double f) {
        this.f = f;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    /**
     * The method used to define the ordering of the Route in the PriorityQueue
     * @param n - the other route
     * @return - 1 if greater, -1 if less and 0 if equal
     */
    @Override
    public int compareTo(RouteNode n) {
        return Double.compare(this.f, n.f);
    }

    /**
     * Method for checking equality of elements
     * @param otherRoute - The other route
     * @return - true if airportCodes are the same, false otherwise
     */
    @Override
    public boolean equals(Object otherRoute) {
        RouteNode someRoute = (RouteNode) otherRoute;
        return this.route.getAirportCode().equals(someRoute.route.getAirportCode());
    }

    /**
     * Method to generate a hashcode to ensure that the same function is used to avoid
     * repeating elements in the HashMap
     * @return - the hashCode
     */
    @Override
    public int hashCode() {
        int favNum = 99;
        int res = 1;
        return favNum + res * this.route.hashCode();
    }

    /**
     * Method for printing the string representation of the Route
     * @return - string
     */
    @Override
    public String toString() {
        return this.route.getAirline() + " from " + this.prev.route.getAirportCode() +
                " to " + this.route.getAirportCode() + "\nTotal Distance: " + this.distance;
    }

    /**
     * Method to calculate the heuristic value -
     * The distance between the current aiport and the destination
     * @param sourceAirport - the current airport
     * @param destAirport - the destination
     * @return - distance between them
     */
    public double getHeuristic(String sourceAirport, String destAirport){
        double[] source = Airport.getLongLat(sourceAirport);
        double[] dest = Airport.getLongLat(destAirport);
        double lat1 = source[1];
        double lon1 = source[0];
        double lat2 = dest[1];
        double lon2 = dest[0];
        // calculating distance from the latitudes and longitudes using HaverSine function
        return HaverSineDistance.haversine(lat1, lon1, lat2, lon2);
    }


    /**
     * Method to get the path to the solution
     * @return - arraylist of the RouteNodes
     */
    public ArrayList<RouteNode> getOptimalRoute() {
        ReadAndWrite writeOutput = new ReadAndWrite();

        ArrayList<RouteNode> finalRoute = new ArrayList<RouteNode>();

        RouteNode end = this;
        double totalDistance = end.distance;
        int stops = end.route.getStops();

        while (end.prev != null) {
            finalRoute.add(0, end);
            end = end.prev;
        }
        writeOutput.writeOutputFile(finalRoute, totalDistance,stops);
        return finalRoute;
    }
}


