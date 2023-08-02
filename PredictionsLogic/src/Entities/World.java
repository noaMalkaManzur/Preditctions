package Entities;

import Entities.Entity;
import Entities.Enviroment;

import java.util.List;
import java.util.Map;

public class World
{
    private Enviroment WorldEnviroment;
    private Map<String, EntityProperty> WorldEntities;
    private Rules WorldRules;
    private Termination WorldTermination;

    public World(Enviroment worldEnviroment, Map<String, EntityProperty> worldEntities, Rules worldRules, Termination worldTermination) {
        WorldEnviroment = worldEnviroment;
        WorldEntities = worldEntities;
        WorldRules = worldRules;
        WorldTermination = worldTermination;
    }

    public Termination getWorldTermination() {
        return WorldTermination;
    }

    public void setWorldTermination(Termination worldTermination) {
        WorldTermination = worldTermination;
    }

    public Rules getWorldRules() {
        return WorldRules;
    }

    public void setWorldRules(Rules worldRules) {
        WorldRules = worldRules;
    }

    public Map<String, EntityProperty> getWorldEntities() {
        return WorldEntities;
    }

    public void setWorldEntities(Map<String, EntityProperty> worldEntities) {
        WorldEntities = worldEntities;
    }

    public Enviroment getWorldEnviroment() {
        return WorldEnviroment;
    }

    public void setWorldEnviroment(Enviroment worldEnviroment) {
        WorldEnviroment = worldEnviroment;
    }
}
