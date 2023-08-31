package Defenitions.Actions.Condition.impl;

import Defenitions.Actions.Condition.api.ConditionDTO;
import Enums.ActionTypeDTO;

public class MultipleDTO extends ConditionDTO {

    private final String logic;
    private final int condNum;
    public MultipleDTO(ActionTypeDTO type, String primaryEntityName, String secondaryEntityName,
                       String singularity, int thenSize, int elseSize, String logic, int condNum) {
        super(type, primaryEntityName, secondaryEntityName, singularity, thenSize, elseSize);
        this.logic = logic;
        this.condNum = condNum;
    }
}
