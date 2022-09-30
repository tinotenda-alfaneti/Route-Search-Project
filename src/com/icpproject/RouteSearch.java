package com.icpproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;


public class RouteSearch {

    /**
     * Method to get destinations given a source airport code
     * @param routesMap - the map with the routes
     * @param airportCode - the code for the airport
     * @return - array list of routes
     */
    public static ArrayList<Route> getDestinations(HashMap<String,
                                                    ArrayList<Route>> routesMap,
                                                    String airportCode) {
        ArrayList<Route> dest = new ArrayList<Route>();
        if (routesMap.containsKey(airportCode)) {
            dest = routesMap.get(airportCode);
        }
        return dest;
    }

    /**
     * Method to perfom the search
     * @param startCity - the start city
     * @param destCity - the destination city
     * @return - the RouteNode of the final step
     */
    public static RouteNode aStar(String startCity, String destCity){

        Route route = new Route(startCity);
        RouteNode start = new RouteNode(route);
        Route dest = new Route(destCity);
        RouteNode target = new RouteNode(dest);

        PriorityQueue<RouteNode> frontier = new PriorityQueue<RouteNode>();
        PriorityQueue<RouteNode> explored = new PriorityQueue<RouteNode>();

        // Calculating the evaluation function f = h + distance
        start.setF(start.getDistance() + start.getHeuristic(start.getRoute().getAirportCode(),
                target.getRoute().getAirportCode()));
        frontier.add(start);

        while (!frontier.isEmpty()) {
            RouteNode shortRoute = frontier.remove();

            if (shortRoute.getRoute().getAirportCode().equals(target.getRoute().getAirportCode())) {
                System.out.println("Destination Found. Check the full description in outputfile.txt");
                shortRoute.getOptimalRoute();
                return shortRoute;
            }
            explored.add(shortRoute);
            System.out.println("Still Searching..., currently at " + shortRoute.getRoute().getAirportCode());

            //Generating the neighbouring routes
            ArrayList<Route> destinations = getDestinations(Route.readRoutesFile(),
                    shortRoute.getRoute().getAirportCode());

            for (Route destination : destinations) {
                //calculating the distance
                double distance = shortRoute.getDistance() + destination.getDistance();
                // calculating the evaluation function
                double f = distance + shortRoute.getHeuristic(destination.getAirportCode(),
                        target.getRoute().getAirportCode());
                //incrementing the path cost
                destination.setStops(destination.getStops()+shortRoute.getRoute().getStops());
                RouteNode newRoute = new RouteNode(destination, shortRoute, distance, f);

                if (!explored.contains(newRoute) && !frontier.contains(newRoute)) {
                    frontier.add(newRoute);

                } else {
                    // updating the already available route if it has a larger distance
                    if (distance < newRoute.getDistance()) {
                        System.out.println("Updated a node");
                        newRoute.setPrev(shortRoute);
                        newRoute.setDistance(distance);
                        newRoute.setF(f);

                        if (explored.contains(newRoute)) {
                            explored.remove(newRoute);
                            frontier.add(newRoute);
                        }

                    }
                }
            }
            explored.add(shortRoute);
        }


        return null;
    }

}

