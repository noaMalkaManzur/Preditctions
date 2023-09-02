package definition.secondaryEntity.api;

import action.impl.condition.api.ConditionAction;

public interface SecondaryEntity {
    String getName();
    String getCount();
    ConditionAction getConditionAction();
}
