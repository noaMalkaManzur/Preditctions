package Defenitions.Actions.Set;

import Defenitions.Actions.api.ActionDTO;
import Enums.ActionTypeDTO;

public class SetDTO extends ActionDTO
{
    private final String propertyName;

    private final String value;

    public SetDTO(ActionTypeDTO type, String primaryEntityName, String secondaryEntityName, String propertyName, String value) {
        super(type, primaryEntityName, secondaryEntityName);
        this.propertyName = propertyName;
        this.value = value;
    }
    public String getPropertyName() {
        return propertyName;
    }

    public String getValue() {
        return value;
    }
}
