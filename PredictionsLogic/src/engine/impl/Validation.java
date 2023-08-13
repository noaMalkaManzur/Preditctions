package engine.impl;

public interface Validation {
    void checkValidString(Object string);

    void checkNumber(Object number);
    void checkEntityDefinition(Object name, Object population);
}
