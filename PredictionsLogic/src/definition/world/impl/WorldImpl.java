package definition.world.impl;

import Generated.PRDWorld;
import definition.entity.EntityDefinition;
import definition.environment.api.EnvVariablesManager;
import definition.world.api.Termination;
import definition.world.api.WorldDefinition;
import rule.Rule;

import java.util.Map;

public class WorldImpl implements WorldDefinition
{
    private Map<String, EntityDefinition> entities;
    private EnvVariablesManager envVariables;
    private Map<String,Rule> rules;
    private Termination terminationTerm;

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
}
