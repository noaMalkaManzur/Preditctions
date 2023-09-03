package definition.secondaryEntity.api;

import action.impl.condition.api.ConditionAction;

public interface SecondaryEntityDefinition {
    String getName();
    String getCount();
    ConditionAction getConditionAction();
}
