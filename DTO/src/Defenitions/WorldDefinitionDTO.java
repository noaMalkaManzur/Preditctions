package Defenitions;

import java.util.Map;

public class WorldDefinitionDTO
{
    private final String simulationName;
    private final Map<String,EntityDefinitionDTO> entities;
    private final EnvironmentDefinitionDTO environmentVars;
    private final Map<String, RuleDTO> rules;
    private final TerminationDTO terms;
    private final GridDTO grid;

    public WorldDefinitionDTO(Map<String, EntityDefinitionDTO> entities, EnvironmentDefinitionDTO environmentVars, Map<String, RuleDTO> rules, TerminationDTO terms, GridDTO grid, String simulationName) {
        this.entities = entities;
        this.environmentVars = environmentVars;
        this.rules = rules;
        this.terms = terms;
        this.grid = grid;
        this.simulationName = simulationName;
    }

    public GridDTO getGrid() {return grid;}

    public Map<String, EntityDefinitionDTO> getEntities() {
        return entities;
    }

    public EnvironmentDefinitionDTO getEnvironmentVars() {
        return environmentVars;
    }

    public Map<String, RuleDTO> getRules() {
        return rules;
    }

    public TerminationDTO getTerms() {
        return terms;
    }
    public String getSimulationName(){return simulationName;}
}
