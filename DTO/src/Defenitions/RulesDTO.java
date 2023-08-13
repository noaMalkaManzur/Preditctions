package Defenitions;

import action.api.Action;
import rule.Activation;

import java.util.List;

public class RulesDTO
{
    private final String name;
    private final ActivationDTO activation;
    private final List<ActionDTO> actions;

    public RulesDTO(String name, ActivationDTO activation, List<ActionDTO> actions) {
        this.name = name;
        this.activation = activation;
        this.actions = actions;
    }

    public String getName() {
        return name;
    }

    public ActivationDTO getActivation() {
        return activation;
    }

    public List<ActionDTO> getActions() {
        return actions;
    }
}
