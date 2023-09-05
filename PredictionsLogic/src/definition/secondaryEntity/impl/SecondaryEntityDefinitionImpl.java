package definition.secondaryEntity.impl;

import action.impl.condition.api.ConditionAction;
import definition.secondaryEntity.api.SecondaryEntityDefinition;

public class SecondaryEntityDefinitionImpl implements SecondaryEntityDefinition {
    String name;
    String count;
    ConditionAction conditionAction;
    public SecondaryEntityDefinitionImpl(String name, String count, ConditionAction conditionAction){
        this.name = name;
        this.count = count;
        this.conditionAction = conditionAction;
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public String getCount() {
        return count;
    }
    @Override
    public ConditionAction getConditionAction() {
        return conditionAction;
    }

}
