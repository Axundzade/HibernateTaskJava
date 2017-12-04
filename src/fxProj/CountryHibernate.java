package fxProj;

/**
 * Created by dell on 9/27/2017.
 */
public class CountryHibernate {

    public int ID;
    public String Name;
    public String Continent;

    public CountryHibernate() {
    }

    public CountryHibernate(int ID, String name, String continent) {
        this.ID = ID;
        Name = name;
        Continent = continent;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getContinent() {
        return Continent;
    }

    public void setContinent(String continent) {
        Continent = continent;
    }
}
