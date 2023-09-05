package Defenitions.Actions.Condition.api;

import Defenitions.Actions.api.ActionDTO;
import Enums.ActionTypeDTO;

public class ConditionDTO extends ActionDTO
{
    private final int thenSize;
    private final int elseSize;
    public ConditionDTO(ActionTypeDTO type, String primaryEntityName, String secondaryEntityName, int thenSize, int elseSize) {
        super(type, primaryEntityName, secondaryEntityName);
        this.thenSize = thenSize;
        this.elseSize = elseSize;
    }


    public int getThenSize() {
        return thenSize;
    }

    public int getElseSize() {
        return elseSize;
    }


}
