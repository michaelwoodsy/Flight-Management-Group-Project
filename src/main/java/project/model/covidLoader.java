package project.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

public class covidLoader {



    public static Hashtable<String, Covid> loadCovidFile(String path) throws IOException {

        Hashtable<String, Covid> covidDict = new Hashtable<>();
        Covid currentCovid;

        BufferedReader dataReader = new BufferedReader(new FileReader(path));

        boolean breaker = false;
        while (!breaker) {
            String row = dataReader.readLine();
            if (row == null) {
                breaker = true;
            } else {
                String[] data = row.split(",");
                currentCovid = loadCovid(data);
                covidDict.put(currentCovid.getCountry(), currentCovid);
            }
        }
        dataReader.close();
        return covidDict;
    }

    public static Covid loadCovid(String[] covidData) {
        // for numeric values, if they are not valid they are set to a default of 0.
        // for string values, if they contain illegal characters are set to null
        //e.g. if total_cases is missing or is not numeric, then it is set to 0
        //e.g. if continent is Asi3a (invalid), it is set to null
        String location;
        try {
            if (covidData[2].matches("[A-Za-z '-]+")){
                location = covidData[2];
            }
            else{
                location = "Unknown";
            }
        }catch (Exception e){
            location = "Unknown";
        }

        String date;
        try {
            //checks date doesn't contain invalid Characters
            if (covidData[3].matches("[0-9/]+")){
                date = covidData[3];
            }
            else{
                date = null;
            }
        }catch (Exception e){
            date = null;
        }

        float total_cases_per_million;
        try {
            total_cases_per_million = Float.parseFloat(covidData[8]);
        }catch (Exception e){
            total_cases_per_million = 0;
        }

        float total_deaths_per_million;
        try {
            total_deaths_per_million = Float.parseFloat(covidData[9]);
        }catch (Exception e){
            total_deaths_per_million = 0;
        }

        int population;
        try {
            population = Integer.parseInt(covidData[10]);
        }catch (Exception e){
            population = 0;
        }

        Covid newCovid = new Covid(location, date, total_cases_per_million, total_deaths_per_million, population);
        return newCovid;

    }
}
