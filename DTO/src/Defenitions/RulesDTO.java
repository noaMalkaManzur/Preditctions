package Defenitions;

import java.util.List;

public class RulesDTO
{
    private final List<RuleDTO> ruleDTOList;

    public RulesDTO(List<RuleDTO> ruleDTOList) {
        this.ruleDTOList = ruleDTOList;
    }

    public List<RuleDTO> getRuleDTOList() {
        return ruleDTOList;
    }
}
