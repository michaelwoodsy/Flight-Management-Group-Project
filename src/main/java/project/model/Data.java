package project.model;

public abstract class Data {
    private String dataType;

    public String getDataType() {
        return this.dataType;
    }

    public abstract String print();

    public abstract void update();
}
