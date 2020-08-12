package project.model;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Record {
    //ArrayList for each need to be flight objects, currently string but will need to change
    private ArrayList<String> Flight = new ArrayList<String>();
    private ArrayList<String> Route = new ArrayList<String>();
    private ArrayList<String> Airport = new ArrayList<String>();
    private ArrayList<String> Airline = new ArrayList<String>();

    public ArrayList<String> filterAirports(String country){
        //skeleton code
        return Airport;
    }
    public ArrayList<String> filterAirlines(Boolean active){
        return Airline;
    }
    public ArrayList<String> filterAirlinesCountry(String country){
        return Airline;
    }
    public ArrayList<String> filterRoutesDeparture(String country){
        return Route;
    }
    public ArrayList<String> filterRoutesDestination(String county){
        return Route;
    }
    public ArrayList<String> filterRoutesStops(boolean direct){
        return Route;
    }
    public ArrayList<String> filterRoutesEquipment(String equipment){
        return Route;
    }
    public ArrayList<String> rankAirports(boolean reverse){
        return Airport;
    }
    public ArrayList<String> searchAirports(String search){
        return Airport;
    }
    public ArrayList<String> searchFlights(String search){
        return Flight;
    }
    public void addRecord(InputStream inFile, boolean newRecord){

    }
    public void addData(int dataType){

    }
    public void deleteData(int dataType){

    }

}
