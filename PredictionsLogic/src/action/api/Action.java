package action.api;

import Enums.ActionTypeDTO;
import definition.entity.EntityDefinition;
import definition.secondaryEntity.api.SecondaryEntityDefinition;
import execution.context.Context;
import execution.instance.property.PropertyInstance;
import expression.api.Expression;

public interface Action {
    void invoke(Context context, int currTickToChangeValue);
    EntityDefinition getContextEntity();
    Object getExpressionVal(Expression expression, Context context);
    boolean verifyNumericPropertyType(PropertyInstance propertyValue);
    ActionTypeDTO getActionType();
    boolean hasSecondaryEntity();
    SecondaryEntityDefinition getSecondaryEntityDefinition();
    
}
