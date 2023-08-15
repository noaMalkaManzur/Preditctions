package Instance;

import Defenitions.EntityPropDefinitionDTO;

public class EntPropertyInstanceDTO
{
    private final EntityPropDefinitionDTO propDef;
    private final Object val;

    public EntPropertyInstanceDTO(EntityPropDefinitionDTO propDef, Object val) {
        this.propDef = propDef;
        this.val = val;
    }

    public EntityPropDefinitionDTO getPropDef() {
        return propDef;
    }

    public Object getVal() {
        return val;
    }
}
