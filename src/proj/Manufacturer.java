package proj;

import javafx.beans.property.SimpleStringProperty;

public class Manufacturer {
    private SimpleStringProperty name, location;

    public Manufacturer(String name, String location) {
        this.name = new SimpleStringProperty();
        this.location = new SimpleStringProperty();

        setName(name);
        setLocation(location);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getLocation() {
        return location.get();
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

}