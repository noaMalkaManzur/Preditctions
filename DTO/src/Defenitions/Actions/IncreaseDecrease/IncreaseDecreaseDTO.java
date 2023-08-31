package Defenitions.Actions.IncreaseDecrease;

import Defenitions.Actions.api.ActionDTO;
import Enums.ActionTypeDTO;

public class IncreaseDecreaseDTO extends ActionDTO {
    private final String PropertyName;
    private final String ByExpression;

    public IncreaseDecreaseDTO(ActionTypeDTO type, String primaryEntityName, String secondaryEntityName, String propertyName, String byExpression) {
        super(type, primaryEntityName, secondaryEntityName);
        PropertyName = propertyName;
        ByExpression = byExpression;
    }

    public String getByExpression() {
        return ByExpression;
    }

    public String getPropertyName() {
        return PropertyName;
    }
}
