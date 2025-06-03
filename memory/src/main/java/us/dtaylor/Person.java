package us.dtaylor;

// File: Person.java
public class Person {
    String name;
    int powerLevel;

    public Person(String name, int powerLevel) {
        this.name = name;
        this.powerLevel = powerLevel;
    }

    public String getInfo() {
        return name + " has power level " + powerLevel;
    }

    @Override
    protected void finalize() {
        System.out.println(name + " is being garbage collected.");
    }
}

