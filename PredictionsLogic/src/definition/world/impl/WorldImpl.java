package definition.world.impl;

import definition.entity.EntityDefinition;
import definition.entity.EntityDefinitionImpl;
import definition.environment.api.EnvVariablesManager;
import definition.property.api.PropertyType;
import definition.property.impl.BooleanPropertyDefinition;
import definition.property.impl.FloatPropertyDefinition;
import definition.property.impl.IntegerPropertyDefinition;
import definition.property.impl.StringPropertyDefinition;
import definition.value.generator.api.ValueGenerator;
import definition.world.api.Termination;
import definition.world.api.WorldDefinition;
import rule.Rule;

import java.util.HashMap;
import java.util.Map;

public class WorldImpl implements WorldDefinition
{
    private Map<String, EntityDefinition> entities;
    private EnvVariablesManager envVariables;
    private Map<String,Rule> rules;
    private Termination terminationTerm;
    private Grid grid;
    public WorldImpl()
    {

    }

    public WorldImpl(WorldDefinition worldDefinition) {
        this.entities = new HashMap<>(worldDefinition.getEntities());
        this.envVariables = worldDefinition.getEnvVariables();
        this.rules = new HashMap<>(worldDefinition.getRules());
        this.terminationTerm = worldDefinition.getTerminationTerm();
        this.grid = worldDefinition.getGrid();
    }

    public Map<String, EntityDefinition> getEntities() {
        return entities;
    }

    public void setEntities(Map<String, EntityDefinition> entities) {
        this.entities = entities;
    }

    public EnvVariablesManager getEnvVariables() {
        return envVariables;
    }

    public void setEnvVariables(EnvVariablesManager envVariables) {
        this.envVariables = envVariables;
    }

    public Map<String, Rule> getRules() {
        return rules;
    }

    public void setRules(Map<String, Rule> rules) {
        this.rules = rules;
    }

    public Termination getTerminationTerm() {
        return terminationTerm;
    }

    public void setTerminationTerm(Termination terminationTerm) {
        this.terminationTerm = terminationTerm;
    }

    public Grid getGrid() {return grid;}

    @Override
    public void setThreadCount(Integer prdThreadCount) {

    }

    public void setGrid(Grid grid) {this.grid = grid;}
    public Map<String,EntityDefinition> cloneEntities(Map<String,EntityDefinition> entMap)
    {
        Map<String,EntityDefinition> resMap = new HashMap<>();
        entMap.forEach((entityName,ent)->
        {
            int population = ent.getPopulation();

            resMap.put(entityName,new EntityDefinitionImpl(entityName));
            resMap.get(entityName).setPopulation(population);
            ent.getProps().forEach((propName,propertyDefinition)->{
                if(propertyDefinition.getType().equals(PropertyType.DECIMAL)){
                    resMap.get(entityName).addPropertyDefinition(new IntegerPropertyDefinition(propName,PropertyType.DECIMAL,(ValueGenerator<Integer>)propertyDefinition.generateValue(), propertyDefinition.getRange(), propertyDefinition.getRandomInit()));
                }
                else if(propertyDefinition.getType().equals(PropertyType.FLOAT)){
                    resMap.get(entityName).addPropertyDefinition(new FloatPropertyDefinition(propName,PropertyType.FLOAT,(ValueGenerator<Float>)propertyDefinition.generateValue(), propertyDefinition.getRange(), propertyDefinition.getRandomInit()));
                }
                else if(propertyDefinition.getType().equals(PropertyType.BOOLEAN)){
                    resMap.get(entityName).addPropertyDefinition(new BooleanPropertyDefinition(propName,PropertyType.BOOLEAN,(ValueGenerator<Boolean>)propertyDefinition.generateValue(),propertyDefinition.getRandomInit()));

                }
                else {
                    resMap.get(entityName).addPropertyDefinition(new StringPropertyDefinition(propName,PropertyType.STRING,(ValueGenerator<String>)propertyDefinition.generateValue(),propertyDefinition.getRandomInit()));
                }
            });
        });
        return resMap;
    }
}
