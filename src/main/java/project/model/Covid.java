package project.model;

public class Covid {
    private String iso_code;
    private String continent;
    private String location;
    private String date;
    private int total_cases;
    private int new_cases;
    private int total_deaths;
    private int new_deaths;
    private float total_cases_per_million;
    private float total_deaths_per_million;
    private int population;
    private float population_density;

    public Covid(String iso_code, String continent, String location, String date, int total_cases, int new_cases,
                 int total_deaths, int new_deaths, float total_cases_per_million, float total_deaths_per_million,
                 int population, float population_density) {
        setIso_code(iso_code);
        setContinent(continent);
        setLocation(location);
        setDate(date);
        setTotal_cases(total_cases);
        setNew_cases(new_cases);
        setTotal_deaths(total_deaths);
        setNew_deaths(new_deaths);
        setTotal_cases_per_million(total_cases_per_million);
        setTotal_deaths_per_million(total_deaths_per_million);
        setPopulation(population);
        setPopulation_density(population_density);
    }

    public void setIso_code(String iso_code) {
        this.iso_code = iso_code;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTotal_cases(int total_cases) {
        this.total_cases = total_cases;
    }

    public void setNew_cases(int new_cases) {
        this.new_cases = new_cases;
    }

    public void setTotal_deaths(int total_deaths) {
        this.total_deaths = total_deaths;
    }

    public void setNew_deaths(int new_deaths) {
        this.new_deaths = new_deaths;
    }

    public void setTotal_cases_per_million(float total_cases_per_million) {
        this.total_cases_per_million = total_cases_per_million;
    }

    public void setTotal_deaths_per_million(float total_deaths_per_million) {
        this.total_deaths_per_million = total_deaths_per_million;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public void setPopulation_density(float population_density) {
        this.population_density = population_density;
    }

}