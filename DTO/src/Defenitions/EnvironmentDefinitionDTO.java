package Defenitions;

import java.util.Map;

public class EnvironmentDefinitionDTO
{
    private final Map<String, EnvPropertyDefinitionDTO> envProps;

    public EnvironmentDefinitionDTO(Map<String, EnvPropertyDefinitionDTO> envProps) {
        this.envProps = envProps;
    }

    public Map<String, EnvPropertyDefinitionDTO> getEnvProps() {
        return envProps;
    }

}
