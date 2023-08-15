package Instance;

import java.util.Map;

public class ActiveEnvDTO
{
    private final Map<String, EnvPropertyInstanceDTO> envPropInstances;

    public ActiveEnvDTO(Map<String, EnvPropertyInstanceDTO> envPropInstances) {
        this.envPropInstances = envPropInstances;
    }

    public Map<String, EnvPropertyInstanceDTO> getEnvPropInstances() {
        return envPropInstances;
    }
}
