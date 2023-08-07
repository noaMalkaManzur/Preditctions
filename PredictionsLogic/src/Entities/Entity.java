package Entities;

import java.util.List;
import java.util.Map;

public class Entity
{
    private final String name;
    private  int population;
    private Map<String, EntityProperty> EntProperties;


    public Entity(String name, int population, Map<String, EntityProperty> entProperties) {
        this.name = name;
        this.population = population;
        this.EntProperties = entProperties;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public Map<String, EntityProperty> getEntProperties() {
        return EntProperties;
    }

    public void setEntProperties(Map<String, EntityProperty> entProperties) {
        EntProperties = entProperties;
    }

    @Override
    public String toString() {
            StringBuilder MyToString = new StringBuilder();
            MyToString.append("Name:").append(name).append("\n");
            MyToString.append("Population:").append(population).append("\n\n");
            for(Map.Entry<String, EntityProperty> entProp:EntProperties.entrySet())
            {

                MyToString.append(entProp.getValue().toString()).append("\n");
            }
            return MyToString.toString();
    }
}
