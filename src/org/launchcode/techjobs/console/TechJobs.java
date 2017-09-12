package org.launchcode.techjobs.console;
import java.util.*;

/**
 * Created by LaunchCode
 */
public class TechJobs {

    private static Scanner in = new Scanner(System.in);

    public static void main (String[] args) {

        // Initialize our field map with key/name pairs
        HashMap<String, String> columnChoices = new HashMap<>();
        columnChoices.put("core competency", "Skill");
        columnChoices.put("employer", "Employer");
        columnChoices.put("location", "Location");
        columnChoices.put("position type", "Position Type");
        columnChoices.put("all", "All");

        // Top-level menu options
        HashMap<String, String> actionChoices = new HashMap<>();
        actionChoices.put("search", "Search");
        actionChoices.put("list", "List");

        System.out.println("Welcome to LaunchCode's TechJobs App!");

        // Allow the user to search until they manually quit
        while (true) {

            String actionChoice = getUserSelection("View jobs by:", actionChoices);

            if (actionChoice.equals("list")) {

                String columnChoice = getUserSelection("List", columnChoices);

                if (columnChoice.equals("all")) {
                    printJobs(JobData.findAll());

                } else {

                    ArrayList<String> results = JobData.findAll(columnChoice);

                    System.out.println("\n*** All " + columnChoices.get(columnChoice) + " Values ***");

                    // public static final Comparator <String> CASE_INSENSITIVE_ORDER
                    Collections.sort(results, String.CASE_INSENSITIVE_ORDER);

                    // Print list of skills, employers, etc
                    for (String item : results) {
                        System.out.println(item);
                    }
                    if (results.isEmpty()) {
                        System.out.println("no match found");
                    }
                }

            } else { // choice is "search"

                // How does the user want to search (e.g. by skill or employer)
                String searchField = getUserSelection("Search by:", columnChoices);

                // What is their search term?
                System.out.println("\nSearch term: ");
                String searchTerm = in.nextLine();

                if (searchField.equals("all")) {

                    // load a list of jobs containing the specified search term to see if it is empty
                    ArrayList<HashMap<String, String>> jobList = JobData.findByValue(searchTerm);

                    if (jobList.isEmpty()) {
                        System.out.println("no results");
                    }

                    printJobs(JobData.findByValue(searchTerm));}
                //System.out.println("Search all fields not yet implemented.");


                else {

                    // load a list of jobs containing the specified search term to see if it is empty
                    ArrayList<HashMap<String, String>> jobList = JobData.findByColumnAndValue(searchField, searchTerm);

                    if (jobList.isEmpty()) {
                        System.out.println("no matches");
                    }

                    printJobs(JobData.findByColumnAndValue(searchField, searchTerm));
                }
            }
        }
    }

    // ﻿Returns the key of the selected item from the choices Dictionary
    private static String getUserSelection(String menuHeader, HashMap<String, String> choices) {

        Integer choiceIdx;
        Boolean validChoice = false;
        String[] choiceKeys = new String[choices.size()];

        // Put the choices in an ordered structure so we can
        // associate an integer with each one
        Integer i = 0;
        for (String choiceKey : choices.keySet()) {
            choiceKeys[i] = choiceKey;
            i++;
        }

        do {

            System.out.println("\n" + menuHeader);

            // Print available choices
            for (Integer j = 0; j < choiceKeys.length; j++) {
                System.out.println("" + j + " - " + choices.get(choiceKeys[j]));
            }

            choiceIdx = in.nextInt();
            in.nextLine();

            // Validate user's input
            if (choiceIdx < 0 || choiceIdx >= choiceKeys.length) {
                System.out.println("Invalid choice. Try again.");
            } else {
                validChoice = true;
            }

        } while(!validChoice);

        return choiceKeys[choiceIdx];
    }

    // Print a list of jobs formatted with ***** enclosing each entry and an empty line between each entry
    private static void printJobs(ArrayList<HashMap<String, String>> someJobs) {

        for (HashMap<String, String> job : someJobs){
            System.out.println("***");

            for (Map.Entry<String, String> value : job.entrySet()) {
                System.out.println(value.getKey() + ": " + value.getValue());
            }
            {System.out.println("***");
                System.out.println( );
            }
//double for loop prints out 5 stars, then (value.getKey() & value.getValue());
// then prints 5 stars again, basically separating
// out all the entries you asked for with ***'s until there is no more job info
        }
    }
}