package Defenitions.Actions.api;

import Enums.ActionTypeDTO;

public class ActionDTO {
    private final ActionTypeDTO type;
    private final String primaryEntityName;
    private final String SecondaryEntityName;

    public ActionDTO(ActionTypeDTO type, String primaryEntityName, String secondaryEntityName) {
        this.type = type;
        this.primaryEntityName = primaryEntityName;
        SecondaryEntityName = secondaryEntityName;
    }

    public ActionTypeDTO getType() {
        return type;
    }

    public String getPrimaryEntityName() {
        return primaryEntityName;
    }

    public String getSecondaryEntityName() {
        return SecondaryEntityName;
    }
}
