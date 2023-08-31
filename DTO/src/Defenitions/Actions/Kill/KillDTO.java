package Defenitions.Actions.Kill;

import Defenitions.Actions.api.ActionDTO;
import Enums.ActionTypeDTO;

public class KillDTO extends ActionDTO {
    public KillDTO(ActionTypeDTO type, String primaryEntityName, String secondaryEntityName) {
        super(type, primaryEntityName, secondaryEntityName);
    }
}
