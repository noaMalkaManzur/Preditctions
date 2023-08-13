package Defenitions;

import Enums.ActionTypeDTO;

public class ActionDTO {
    private final ActionTypeDTO type;

    public ActionDTO(ActionTypeDTO type) {
        this.type = type;
    }

    public ActionTypeDTO getType() {
        return type;
    }
}
