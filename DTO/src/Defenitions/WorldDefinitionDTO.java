package Defenitions;

import java.util.Map;

public class WorldDefinitionDTO
{
    private final Map<String,EntityDefinitionDTO> entities;
    private final EnvironmentDefinitionDTO environmentVars;
    private final Map<String,RulesDTO> rules;
    private final TerminitionDTO terms;

    public WorldDefinitionDTO(Map<String, EntityDefinitionDTO> entities, EnvironmentDefinitionDTO environmentVars, Map<String, RulesDTO> rules, TerminitionDTO terms) {
        this.entities = entities;
        this.environmentVars = environmentVars;
        this.rules = rules;
        this.terms = terms;
    }

    public Map<String, EntityDefinitionDTO> getEntities() {
        return entities;
    }

    public EnvironmentDefinitionDTO getEnvironmentVars() {
        return environmentVars;
    }

    public Map<String, RulesDTO> getRules() {
        return rules;
    }

    public TerminitionDTO getTerms() {
        return terms;
    }
}
