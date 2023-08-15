package Instance;

import Defenitions.EntityPropDefinitionDTO;
import Defenitions.EnvPropertyDefinitionDTO;

public class EnvPropertyInstanceDTO {
    private final EnvPropertyDefinitionDTO propDef;
    private final Object val;

    public EnvPropertyInstanceDTO(EnvPropertyDefinitionDTO propDef, Object val) {
        this.propDef = propDef;
        this.val = val;
    }

    public EnvPropertyDefinitionDTO getPropDef() {
        return propDef;
    }

    public Object getVal() {
        return val;
    }
}
