package project.model;

import org.apache.commons.math3.util.Precision;

public class Covid {
    private String location;
    private String date;
    private double total_cases_per_million;
    private double total_deaths_per_million;
    private int population;

    public Covid(String location, String date, double total_cases_per_million, double total_deaths_per_million,
                 int population) {
        setLocation(location);
        setDate(date);
        setTotal_cases_per_million(total_cases_per_million);
        setTotal_deaths_per_million(total_deaths_per_million);
        setPopulation(population);
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTotal_cases_per_million(double total_cases_per_million) {
        this.total_cases_per_million = total_cases_per_million;
    }

    public void setTotal_deaths_per_million(double total_deaths_per_million) {
        this.total_deaths_per_million = total_deaths_per_million;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    //just used for the checking that loader works of the loader
    public String print_country_data(){
        return location + " " +  date + " " + total_cases_per_million + " " + total_deaths_per_million + " " + population;
    }

    public String getCountry() {
        return this.location;
    }

    public String getRiskString() {
        double percentage = getRiskDouble();
        //values that determine the severity of the risk will change! just a placeholder.
        if (percentage <= 0.1){
            return "Extremely Low";
        }
        if (percentage <= 0.5){
            return "Low";
        }
        else if(percentage <= 1){
            return "Medium";
        }
        else if (percentage <= 5){
            return "High Risk";
        }
        else{
            return "Extreme risk";
        }
    }

    /**
     * Gets the percentage of the population that have cases of COVID-19
     * @return The percentage of one million citizens that have cases of COVID-19
     */
    public double getRiskDouble() {
        double percentWithCases = (this.total_cases_per_million/1000000)*100;
        return Precision.round(percentWithCases, 2);
    }
}
