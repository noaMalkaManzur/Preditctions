package Instance;

import java.util.Map;

public class ActiveEnvDTO
{
    private final Map<String,PropertyInstanceDTO> envPropInstances;

    public ActiveEnvDTO(Map<String, PropertyInstanceDTO> envPropInstances) {
        this.envPropInstances = envPropInstances;
    }

    public Map<String, PropertyInstanceDTO> getEnvPropInstances() {
        return envPropInstances;
    }
}
