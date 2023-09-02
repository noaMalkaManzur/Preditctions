package definition.secondaryEntity.impl;

import action.impl.condition.api.ConditionAction;
import definition.secondaryEntity.api.SecondaryEntity;

public class SecondaryEntityImpl implements SecondaryEntity {
    String name;
    String count;
    ConditionAction conditionAction;
    public SecondaryEntityImpl(String name, String count, ConditionAction conditionAction){
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
