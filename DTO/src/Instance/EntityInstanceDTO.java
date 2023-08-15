package Instance;

import Defenitions.EntityDefinitionDTO;

import java.util.Map;

public class EntityInstanceDTO {
    private final EntityDefinitionDTO entityDefinitionDTO;
    private final int id;
    private final Map<String, EntPropertyInstanceDTO> propertiesDTO;

    public EntityInstanceDTO(EntityDefinitionDTO entityDefinitionDTO, int id, Map<String, EntPropertyInstanceDTO> propertiesDTO) {
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

    public Map<String, EntPropertyInstanceDTO> getPropertiesDTO() {
        return propertiesDTO;
    }
}
