
package simulationInfo;

import Defenitions.EntityDefinitionDTO;
import Defenitions.RuleDTO;
import Defenitions.TerminationDTO;

import java.util.Map;

public class SimulationInfoDTO {
    private final Map<String, EntityDefinitionDTO> entities;
    private final Map<String, RuleDTO> rules;
    private final TerminationDTO terms;

    public SimulationInfoDTO(Map<String, EntityDefinitionDTO> entities, Map<String, RuleDTO> rules, TerminationDTO terms) {
        this.entities = entities;
        this.rules = rules;
        this.terms = terms;
    }

    public Map<String, EntityDefinitionDTO> getEntities() {
        return entities;
    }

    public Map<String, RuleDTO> getRules() {
        return rules;
    }

    public TerminationDTO getTerms() {
        return terms;
    }
}
