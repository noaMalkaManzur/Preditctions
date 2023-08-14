package action.api;

import definition.entity.EntityDefinition;
import execution.context.Context;
import execution.instance.property.PropertyInstance;
import expression.api.Expression;

public interface Action {
    void invoke(Context context);
    EntityDefinition getContextEntity();
    Object getExpressionVal(Expression expression, Context context);
    boolean verifyNumericPropertyType(PropertyInstance propertyValue);
     ActionType getActionType();

}
