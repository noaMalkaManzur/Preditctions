package Entities;

import java.util.List;
import java.util.Map;

public class Enviroment
{
    private Map<String, EnvProperty> EnvProperties;

    public Enviroment(Map<String, EnvProperty> envProperties) {
        EnvProperties = envProperties;
    }

    public Map<String, EnvProperty> getEnvProperties() {
        return EnvProperties;
    }

    public void setEnvProperties(Map<String, EnvProperty> envProperties) {
        EnvProperties =  envProperties;
    }
}
