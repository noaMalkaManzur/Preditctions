package Instance;

import Defenitions.PropertyDefinitionDTO;

public class PropertyInstanceDTO
{
    private final PropertyDefinitionDTO propDef;
    private final Object val;

    public PropertyInstanceDTO(PropertyDefinitionDTO propDef, Object val) {
        this.propDef = propDef;
        this.val = val;
    }

    public PropertyDefinitionDTO getPropDef() {
        return propDef;
    }

    public Object getVal() {
        return val;
    }
}
