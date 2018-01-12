package main.Relations;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Teams {
    private SimpleStringProperty name;
    private SimpleIntegerProperty number;

    public Teams(int number, String name)
    {
        this.name = new SimpleStringProperty(name);
        this.number = new SimpleIntegerProperty(number);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getNumber() {
        return number.get();
    }

    public SimpleIntegerProperty numberProperty() {
        return number;
    }

    public void setNumber(int number) {
        this.number.set(number);
    }
}
