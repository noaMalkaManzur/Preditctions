package Entities;

import Entities.Entity;
import Entities.Enviroment;

import java.util.List;

public class World
{
    private Enviroment WorldEnviroment;
    private List<Entity> WorldEntities;
    private Rules WorldRules;
    private Termination WorldTermination;

    public World(Enviroment worldEnviroment, List<Entity> worldEntities, Rules worldRules, Termination worldTermination) {
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

    public List<Entity> getWorldEntities() {
        return WorldEntities;
    }

    public void setWorldEntities(List<Entity> worldEntities) {
        WorldEntities = worldEntities;
    }

    public Enviroment getWorldEnviroment() {
        return WorldEnviroment;
    }

    public void setWorldEnviroment(Enviroment worldEnviroment) {
        WorldEnviroment = worldEnviroment;
    }
}
