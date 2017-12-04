package fxProj;


import javafx.beans.property.SimpleStringProperty;

public class CountryTableData {

    public CountryTableData() {
    }

    public String id;
    public SimpleStringProperty Name;
    public SimpleStringProperty Continent;

    public CountryTableData(String id, String name, String continent) {
        this.id = id;
        Name = new SimpleStringProperty(name);
        Continent = new SimpleStringProperty(continent);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return Name.get();
    }


    public void setName(String name) {
        this.Name.set(name);
    }

    public String getContinent() {
        return Continent.get();
    }

    public void setContinent(String continent) {
        this.Continent.set(continent);
    }
}
