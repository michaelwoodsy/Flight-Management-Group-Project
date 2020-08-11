package project.model;

public class Route {
    private String airline;
    private int id;
    private String sourceAirport;
    private int sourceID;
    private String destAirport;
    private int destID;
    private int numStops;
    private String equipment;
    private boolean codeshare;

    public Route(String airline, int id, String sourceAirport, int sourceID, String destAirport, int destID, int numStops, String equipment, boolean codeshare) {
            setAirline(airline);
            setId(id);
            setSourceAirport(sourceAirport);
            setSourceID(sourceID);
            setDestAirport(destAirport);
            setDestID(destID);
            setNumStops(numStops);
            setEquipment(equipment);
            setCodeshare(codeshare);

    }
    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSourceAirport() {
        return sourceAirport;
    }

    public void setSourceAirport(String sourceAirport) {
        this.sourceAirport = sourceAirport;
    }

    public int getSourceID() {
        return sourceID;
    }

    public void setSourceID(int sourceID) {
        this.sourceID = sourceID;
    }

    public String getDestAirport() {
        return destAirport;
    }

    public void setDestAirport(String destAirport) {
        this.destAirport = destAirport;
    }

    public int getDestID() {
        return destID;
    }

    public void setDestID(int destID) {
        this.destID = destID;
    }

    public int getNumStops() {
        return numStops;
    }

    public void setNumStops(int numStops) {
        this.numStops = numStops;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public boolean isCodeshare() {
        return codeshare;
    }

    public void setCodeshare(boolean codeshare) {
        this.codeshare = codeshare;
    }

    public void update(String airline, int id, String sourceAirport, int sourceID, String destAirport, int destID, int numStops, String equipment, boolean codeshare) {
        if (airline != this.airline) {
            setAirline(airline);
        }
        if (id != this.id) {
            setId(id);
        }
        if (sourceAirport != this.sourceAirport) {
            setSourceAirport(sourceAirport);
        }

        if (sourceID != this.sourceID) {
            setSourceID(sourceID);
        }

        if (destAirport != this.destAirport) {
            setDestAirport(destAirport);
        }

        if (destID != this.destID) {
            setDestID(destID);
        }

        if (numStops != this.numStops) {
            setNumStops(numStops);
        }

        if (equipment != this.equipment) {
            setEquipment(equipment);
        }

        if (codeshare != this.codeshare) {
            setCodeshare(codeshare);
        }

    }
}
