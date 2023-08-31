package Defenitions.Actions.Condition.api;

import Defenitions.Actions.api.ActionDTO;
import Enums.ActionTypeDTO;

public class ConditionDTO extends ActionDTO
{
    private final String singularity;
    private final int thenSize;
    private final int elseSize;
    public ConditionDTO(ActionTypeDTO type, String primaryEntityName, String secondaryEntityName, String singularity, int thenSize, int elseSize) {
        super(type, primaryEntityName, secondaryEntityName);
        this.singularity = singularity;
        this.thenSize = thenSize;
        this.elseSize = elseSize;
    }

    public String getSingularity() {
        return singularity;
    }

    public int getThenSize() {
        return thenSize;
    }

    public int getElseSize() {
        return elseSize;
    }


}
