package Defenitions;

import action.api.ActionType;

public class ActionDTO {
    private final ActionType type;

    public ActionDTO(ActionType type) {
        this.type = type;
    }

    public ActionType getType() {
        return type;
    }
}
