package Defenitions;

import java.util.Map;

public class EnvironmentDefinitionDTO
{
    private final Map<String,PropertyDefinitionDTO> envProps;

    public EnvironmentDefinitionDTO(Map<String, PropertyDefinitionDTO> envProps) {
        this.envProps = envProps;
    }

    public Map<String, PropertyDefinitionDTO> getEnvProps() {
        return envProps;
    }
}
