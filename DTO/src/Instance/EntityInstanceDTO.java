package Instance;

import Defenitions.EntityDefinitionDTO;
import execution.instance.property.PropertyInstance;

import java.util.Map;

public class EntityInstanceDTO {
    private final EntityDefinitionDTO entityDefinitionDTO;
    private final int id;
    private final Map<String, PropertyInstanceDTO> propertiesDTO;

    public EntityInstanceDTO(EntityDefinitionDTO entityDefinitionDTO, int id, Map<String, PropertyInstanceDTO> propertiesDTO) {
        this.entityDefinitionDTO = entityDefinitionDTO;
        this.id = id;
        this.propertiesDTO = propertiesDTO;
    }

    public EntityDefinitionDTO getEntityDefinitionDTO() {
        return entityDefinitionDTO;
    }

    public int getId() {
        return id;
    }

    public Map<String, PropertyInstanceDTO> getPropertiesDTO() {
        return propertiesDTO;
    }
}
