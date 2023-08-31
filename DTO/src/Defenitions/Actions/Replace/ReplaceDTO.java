package Defenitions.Actions.Replace;

import Defenitions.Actions.api.ActionDTO;
import Enums.ActionTypeDTO;
import Enums.ModesDTO;

public class ReplaceDTO extends ActionDTO
{
    private final String toKill;
    private final String toCreate;
    private final ModesDTO mode;
    public ReplaceDTO(ActionTypeDTO type, String primaryEntityName, String secondaryEntityName, String toKill, String toCreate, ModesDTO mode) {
        super(type, primaryEntityName, secondaryEntityName);
        this.toKill = toKill;
        this.toCreate = toCreate;
        this.mode = mode;
    }

    public String getToKill() {
        return toKill;
    }

    public String getToCreate() {
        return toCreate;
    }

    public ModesDTO getMode() {
        return mode;
    }
}
