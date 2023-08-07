package Entities;

import Utilities.*;

public class EntityProperty extends Property{
    private final boolean random_init;
    public EntityProperty(Enums.eTypes type, String name, float rangeFrom, float rangeTo, boolean random_init) {
        super(type,name,rangeFrom,rangeTo);
        this.random_init = random_init;
    }

    public boolean isRandom_init() {
        return random_init;
    }

    @Override
    public String toString() {
        return super.toString()+ "\n"+
                "Random init:" + random_init + "\n";
    }
}
