package definition.environment.api;

import java.util.Collection;

import definition.property.api.PropertyDefinition;
import execution.instance.environment.api.ActiveEnvironment;

public interface EnvVariablesManager {
    void addEnvironmentVariable(PropertyDefinition propertyDefinition);
    ActiveEnvironment createActiveEnvironment();
    Collection<PropertyDefinition> getEnvVariables();
}
