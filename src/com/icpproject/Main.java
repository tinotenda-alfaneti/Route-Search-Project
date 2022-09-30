package com.icpproject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Main search = new Main();
        while(true) {
            search.perfomSearch();
            Scanner option = new Scanner(System.in);
            System.out.print("\nDo you want to search a different route? yes/No: ");
            String userOption = option.nextLine();
            if (userOption.equalsIgnoreCase("no")) {
                option.close();
                break;
            } else {
                search.writeInputs(option);
            }


        }

    }

    public void perfomSearch() {
        try {
            Airport.collectData();
            ReadAndWrite readOrWrite = new ReadAndWrite();
            System.out.println("Last flight: " + RouteSearch.aStar(readOrWrite.getSourceAirportCode(), readOrWrite.getDestAirportCode()));
        } catch (OutOfMemoryError oom) {
            System.out.println("Looks like you want to go far. I ran out of memory");
            System.out.println(oom.getMessage());
        }
    }
    public void writeInputs(Scanner getInput) {
        System.out.print("Enter the source city and country e.g Accra,Ghana : ");
        String firstLine = getInput.nextLine();
        System.out.print("Enter the destination city and country e.g Accra,Ghana : ");
        String secondLine = getInput.nextLine();
        try {
            PrintWriter input = new PrintWriter("inputfile.csv");
            input.write(firstLine + "\n" + secondLine);
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }



    }
}
