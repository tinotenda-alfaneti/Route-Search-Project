package com.icpproject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ReadAndWrite {

    public ArrayList<String[]> readInputFile() {
        ArrayList<String[]> inputFileData = new ArrayList<String[]>();
        try {
            Scanner inputsFile = new Scanner(new File("inputfile.csv"));

            while (inputsFile.hasNextLine()) {
                String[] res = inputsFile.nextLine().split(",");
                inputFileData.add(res);
            }
            inputsFile.close();

        } catch (FileNotFoundException fnf) {
            fnf.printStackTrace();
        }
        return inputFileData;
    }

    //TODO: Change the working of this method. It should used the already created fileOutPutData
    public String getSourceAirportCode() {

        //Get airport code for the given city and country
        HashMap<ArrayList<String>, String> airportsData = readAirportsFile();
        ArrayList<String[]> fileOutput = readInputFile();
        String airportCode = "";
        ArrayList<String> cityCountry = new ArrayList<String>();
        try {
            cityCountry.add(fileOutput.get(0)[0]);
            cityCountry.add(fileOutput.get(0)[1]);
        } catch (ArrayIndexOutOfBoundsException aio) {
            System.out.println("Seems like your input file is empty\n");
        }
        if (airportsData.containsKey(cityCountry)) {
            airportCode = airportsData.get(cityCountry);

        }
        else {
            System.out.println("The city/country is not it the list we have sorry");
        }
        return airportCode;
    }

    //TODO: Also change this method
    public String getDestAirportCode(){

        HashMap<ArrayList<String>, String> countries = readAirportsFile();

        //Get airport code for the given city and country
        ArrayList<String[]> fileOutput = readInputFile();
        String destCode = "";
        ArrayList<String> cityCountry = new ArrayList<String>();
        cityCountry.add(fileOutput.get(1)[0]);
        cityCountry.add(fileOutput.get(1)[1]);
        if (countries.containsKey(cityCountry)) {
            destCode = countries.get(cityCountry);

        }
        return destCode;
    }
    /**
     *
     * @return
     */

    public HashMap<ArrayList<String>, String> readAirportsFile() {

        HashMap<ArrayList<String>, String> airportsFileData = new HashMap<ArrayList<String>, String>();
        try {
            Scanner airportsFile = new Scanner(new FileReader("airports.csv"));
            while (airportsFile.hasNextLine()) {
                String[] line = airportsFile.nextLine().split(",");
                ArrayList<String> cityCountry = new ArrayList<String>();
                cityCountry.add(line[2]);
                cityCountry.add(line[3]);
                airportsFileData.put(cityCountry, line[4]);
            }
        } catch (FileNotFoundException fnf) {
            fnf.printStackTrace();
        }
        return airportsFileData;
    }

    /**
     * Method to write to a file
     * @param result - the arraylist with result after searching
     */
    public void writeOutputFile(ArrayList<RouteNode> result, double totalDistance, int stops) {

        try {
            PrintWriter output = new PrintWriter("outputfile.txt");
            int i = 1;
            for (RouteNode currRoute : result) {
                String message = i + ". " + currRoute.getRoute().getAirline() + " from " +
                        currRoute.getPrev().getRoute().getAirportCode() + " to " +
                        currRoute.getRoute().getAirportCode() + " " + currRoute.getRoute().getStops() + " stops " + "\n";
                output.write(message);
                i++;
            }
            output.write("Total flights: " + (i-1) + "\n");
            output.write("Total additional stops: " + stops + "\n");
            output.write("Total Distance: " + (int) totalDistance + "KM\n");
            output.write("Optimality criteria: Distance");
            output.close();
        } catch (FileNotFoundException fnf) {
            System.out.println(fnf.getMessage());
        }

    }
}
