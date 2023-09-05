package Defenitions.Actions.Condition.impl;

import Defenitions.Actions.Condition.api.ConditionDTO;
import Enums.ActionTypeDTO;

public class MultipleDTO extends ConditionDTO {

    private final String logic;
    private final int condNum;
    public MultipleDTO(ActionTypeDTO type, String primaryEntityName, String secondaryEntityName, String logic, int thenSize, int elseSize, int condNum) {
        super(type, primaryEntityName, secondaryEntityName, thenSize, elseSize);
        this.logic = logic;
        this.condNum = condNum;
    }

    public String getLogic() {
        return logic;
    }

    public int getCondNum() {
        return condNum;
    }
}
