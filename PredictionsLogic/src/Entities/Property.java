package Entities;

import Utilities.Enums;

public class Property
{
    private final Enums.eTypes type ;
    private final String name ;
    private final Range range;

    public Property(Enums.eTypes type, String name, double rangeFrom, double rangeTo) {
        this.type = type;
        this.name = name;
        this.range = new Range(rangeFrom,rangeTo);
    }
    public Enums.eTypes getType() {
        return type;
    }

    public String getName() {
        return name;
    }
    public Range getRange() {
        return range;
    }
    @Override
    public String toString() {
        return "Name:" + name + "\n" +
                "Type:" + type + "\n" +
                "Range:" + range.toString() + "\n" ;
    }
}
