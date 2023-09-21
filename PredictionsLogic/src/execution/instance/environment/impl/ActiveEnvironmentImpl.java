package execution.instance.environment.impl;

import java.util.HashMap;
import java.util.Map;

import execution.instance.environment.api.ActiveEnvironment;
import execution.instance.property.PropertyInstance;

public class ActiveEnvironmentImpl implements ActiveEnvironment {

    private final Map<String, PropertyInstance> envVariables;

    public ActiveEnvironmentImpl() {
        envVariables = new HashMap<>();
    }

    public ActiveEnvironmentImpl(ActiveEnvironment activeEnv)
    {
        this.envVariables = new HashMap<>(activeEnv.getProperties());
    }

    @Override
    public PropertyInstance getProperty(String name) {
        if (!envVariables.containsKey(name)) {
            throw new IllegalArgumentException("Can't find env variable with name " + name);
        }
        return envVariables.get(name);
    }

    @Override
    public void addPropertyInstance(PropertyInstance propertyInstance) {
        envVariables.put(propertyInstance.getPropertyDefinition().getName(), propertyInstance);
    }

    @Override
    public Map<String, PropertyInstance> getProperties() {
        return envVariables;
    }
}
