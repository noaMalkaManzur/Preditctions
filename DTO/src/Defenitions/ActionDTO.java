package Defenitions;

import Enums.ActionType;

public class ActionDTO {
    private final ActionType type;

    public ActionDTO(ActionType type) {
        this.type = type;
    }

    public ActionType getType() {
        return type;
    }
}
