package Defenitions.Actions.Condition.impl;

import Defenitions.Actions.Condition.api.ConditionDTO;
import Enums.ActionTypeDTO;

public class SingleDTO extends ConditionDTO {
    private final String propertyName;
    private final String Operator;
    private final String Value;
    public SingleDTO(ActionTypeDTO type, String primaryEntityName, String secondaryEntityName,
                     String propertyName, String operator, String value,int thenSize,int elseSize) {
        super(type, primaryEntityName, secondaryEntityName, thenSize, elseSize);
        this.propertyName = propertyName;
        Operator = operator;
        Value = value;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public String getOperator() {
        return Operator;
    }

    public String getValue() {
        return Value;
    }
}
