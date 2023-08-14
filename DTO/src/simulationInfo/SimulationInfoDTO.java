
package simulationInfo;

import Defenitions.EntityDefinitionDTO;
import Defenitions.RulesDTO;
import Defenitions.TerminitionDTO;

import java.util.Map;

public class SimulationInfoDTO {
    private final Map<String, EntityDefinitionDTO> entities;
    private final Map<String, RulesDTO> rules;
    private final TerminitionDTO terms;

    public SimulationInfoDTO(Map<String, EntityDefinitionDTO> entities, Map<String, RulesDTO> rules, TerminitionDTO terms) {
        this.entities = entities;
        this.rules = rules;
        this.terms = terms;
    }

    public Map<String, EntityDefinitionDTO> getEntities() {
        return entities;
    }

    public Map<String, RulesDTO> getRules() {
        return rules;
    }

    public TerminitionDTO getTerms() {
        return terms;
    }
}
