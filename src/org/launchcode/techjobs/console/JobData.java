package org.launchcode.techjobs.console;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by LaunchCode
 */
public class JobData {

    private static final String DATA_FILE = "resources/job_data.csv";
    private static Boolean isDataLoaded = false;

    private static ArrayList<HashMap<String, String>> allJobs;

    public static ArrayList<String> findAll(String field) {

        // load data, if not already loaded
        loadData();

        ArrayList<String> values = new ArrayList<>();

        for (HashMap<String, String> row : allJobs) {
            String aValue = row.get(field);

            if (!values.contains(aValue)) {
                values.add(aValue);
            }
        }

        return values;
    }

    public static ArrayList<HashMap<String, String>> findAll() {

        // load data, if not already loaded
        loadData();

        // returns a copy of allJobs(Bonus Mission)
        Object allJobsCopy = allJobs.clone();
        return (ArrayList<HashMap<String, String>>) allJobsCopy;
        // return allJobs; (previous return value)
    }

    /**
     * Returns results of search the jobs data by key/value, using
     * inclusion of the search term.
     *
     * For example, searching for employer "Enterprise" will include results
     * with "Enterprise Holdings, Inc".
     *
     * @param column   Column that should be searched.
     * @param value Value of teh field to search for
     * @return List of all jobs matching the criteria
     */
    public static ArrayList<HashMap<String, String>> findByColumnAndValue(String column, String value) {

        // load data, if not already loaded
        loadData();

        ArrayList<HashMap<String, String>> jobs = new ArrayList<>();

        for (HashMap<String, String> row : allJobs) {

            String aValue = row.get(column);

            if (aValue.toLowerCase().contains(value.toLowerCase())) { // ignores case without changing case
                if (!jobs.contains(row)) // prevents duplicate entries
                    jobs.add(row);
            }
        }

        return jobs;
    }
//doesn't contain dups, "As with printJobs, you should write your code in a way that if a new column is added to the data,
    //your code will automatically search the new column as well,
    //don't use findbycolumvalue once for each column, use loops and collection methods
    /** public static ArrayList<HashMap<String, String>> findByColumnAndValue(String column, String value) {
     loadData();
     ArrayList<HashMap<String, String>> jobs = new ArrayList<>();
     for (HashMap<String, String> row : allJobs) {
     String aValue = row.get(column);
     if (aValue.contains(value)) {
     jobs.add(row);
     }
     }
     return jobs;
     }
     */
    //call somewhere in main-probably the other option to findByColumnValue if-else line 64
    //Edits beneath
    public static ArrayList<HashMap<String, String>> findByValue(String value) {
        value = value.toLowerCase();//ignores capitilization
        loadData();//loads data if not loaded
        ArrayList<HashMap<String, String>> jobs = new ArrayList<>();//makes new ArrayList object
        for (HashMap<String, String> job : allJobs) {//iterates through all jobs checking for like values
            for (String key : job.keySet()) {//iterates through a given specification (job, place, etc) for like values
                if (job.get(key).toLowerCase().contains(value)) {//if it finds a match it adds it to "jobs"
                    jobs.add(job);
                    break;
                }
            }
        }
        return jobs;//returns final values
    }


    /**
     * Read in data from a CSV file and store it in a list
     */
    private static void loadData() {

        // Only load data once
        if (isDataLoaded) {
            return;
        }

        try {

            // Open the CSV file and set up pull out column header info and records
            Reader in = new FileReader(DATA_FILE);
            CSVParser parser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
            List<CSVRecord> records = parser.getRecords();
            Integer numberOfColumns = records.get(0).size();
            String[] headers = parser.getHeaderMap().keySet().toArray(new String[numberOfColumns]);

            allJobs = new ArrayList<>();

            // Put the records into a more friendly format
            for (CSVRecord record : records) {
                HashMap<String, String> newJob = new HashMap<>();

                for (String headerLabel : headers) {
                    newJob.put(headerLabel, record.get(headerLabel));
                }

                allJobs.add(newJob);
            }

            // flag the data as loaded, so we don't do it twice
            isDataLoaded = true;

        } catch (IOException e) {
            System.out.println("Failed to load job data");
            e.printStackTrace();
        }
    }

}