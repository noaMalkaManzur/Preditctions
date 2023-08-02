package Entities;

import Utilities.*;

public class EntityProperty {

    private final Enums.eTypes type ;
    private final String name;
    private final float rangeFrom;
    private final float rangeTo ;
    private final boolean random_init;
    private Object propVal;

    public EntityProperty(Enums.eTypes type, String name, float rangeFrom, float rangeTo, boolean random_init) {
        this.type = type;
        this.name = name;
        this.rangeFrom = rangeFrom;
        this.rangeTo = rangeTo;
        this.random_init = random_init;
    }

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
    public Object getPropVal() {
        return propVal;
    }

    public void setPropVal(Object propVal) {
        this.propVal = propVal;
    }

    public boolean isRandom_init() {
        return random_init;
    }

    @Override
    public String toString() {
        return "Name:" + name + "\n" +
                "Type:" + type + "\n" +
                "Range:" + rangeFrom + "-" + rangeTo + "\n" +
                "Random init:" + random_init + "\n";
    }
}
