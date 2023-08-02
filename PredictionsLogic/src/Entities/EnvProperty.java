package Entities;

import Utilities.Enums;

public class EnvProperty
{
    private final Enums.eTypes type ;
    private final String name ;
    private final float rangeFrom;

    public Enums.eTypes getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public float getRangeFrom() {
        return rangeFrom;
    }

    public float getRangeTo() {
        return rangeTo;
    }

    private final float rangeTo;

    public EnvProperty(Enums.eTypes type, String name, float rangeFrom, float rangeTo) {
        this.type = type;
        this.name = name;
        this.rangeFrom = rangeFrom;
        this.rangeTo = rangeTo;
    }

    @Override
    public String toString() {
        return "Name:" + name + "\n" +
                "Type:" + type + "\n" +
                "Range:" + rangeFrom + "-" + rangeTo + "\n" ;
    }
}
