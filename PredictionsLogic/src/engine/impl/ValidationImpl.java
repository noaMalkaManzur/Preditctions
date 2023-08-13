package engine.impl;

import exceptions.BadNameException;
import exceptions.NotNumberException;

public class ValidationImpl implements Validation {

    @Override
    public void checkValidString(Object string) {
        try {
            if(!(string instanceof String)){
                throw new BadNameException("Not a String");
            }
        }
        catch(Exception ex){

        }
    }

    @Override
    public void checkNumber(Object population) {

        try {
            if(!(population instanceof Integer)){
                throw new NotNumberException("Not a number");
            }

        } catch (Exception ex) {

        }
    }

    @Override
    public void checkEntityDefinition(Object name, Object population) {
        checkValidString(name);
        checkNumber(population);
    }
}
