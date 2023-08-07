package Entities;

import java.util.Map;

public class Enviroment
{
    private Property envProp;
    private Map<String,Object> envPropValues;

    public Enviroment(Property envProp, Map<String, Object> envPropValues) {
        this.envProp = envProp;
        this.envPropValues = envPropValues;
    }

    public Property getEnvProp() {
        return envProp;
    }

    public void setEnvProp(Property envProp) {
        this.envProp = envProp;
    }

    public Map<String, Object> getEnvPropValues() {
        return envPropValues;
    }

    public void setEnvPropValues(Map<String, Object> envPropValues) {
        this.envPropValues = envPropValues;
    }
    public  Object enviroment(String ePropName)
    {
        return envPropValues.get(ePropName);
    }
}
