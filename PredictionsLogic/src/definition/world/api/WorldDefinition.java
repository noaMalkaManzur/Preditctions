package definition.world.api;

import definition.entity.EntityDefinition;
import definition.environment.api.EnvVariablesManager;
import definition.world.impl.Grid;
import rule.Rule;

import java.util.Map;

public interface WorldDefinition
{
    public Map<String, EntityDefinition> getEntities();

    public void setEntities(Map<String, EntityDefinition> entities);

    public EnvVariablesManager getEnvVariables();

    public void setEnvVariables(EnvVariablesManager envVariables);

    public Map<String, Rule> getRules();

    public void setRules(Map<String, Rule> rules);

    public Termination getTerminationTerm();

    public void setTerminationTerm(Termination terminationTerm);

    void setGrid(Grid grid);

    Grid getGrid();
    void setThreadCount(Integer prdThreadCount);
}
