package Entities;

public class EntityProperty implements Properties {
    private final boolean random_init;
    private final Object initVal;

    public EntityProperty(Object type, String name, float rangeFrom, float rangeTo, boolean randomInit, Object initVal) {

        random_init = randomInit;
        this.initVal = initVal;
    }

    public Object getInitVal() {
        return initVal;
    }

    public boolean isRandom_init() {
        return random_init;
    }
}
