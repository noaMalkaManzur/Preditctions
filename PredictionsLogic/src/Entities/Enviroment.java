package Entities;

import java.util.List;

public class Enviroment
{
    private List<EnvProperty> EnvProperties;

    public Enviroment(List<EnvProperty> envProperties) {
        EnvProperties = envProperties;
    }

    public List<EnvProperty> getEnvProperties() {
        return EnvProperties;
    }

    public void setEnvProperties(List<EnvProperty> envProperties) {
        EnvProperties =  envProperties;
    }
}
