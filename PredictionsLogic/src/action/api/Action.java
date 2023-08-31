package action.api;

import Enums.ActionTypeDTO;
import definition.entity.EntityDefinition;
import execution.context.Context;
import execution.instance.property.PropertyInstance;
import expression.api.Expression;

import java.util.List;

public interface Action {
    void invoke(Context context, int currTickToChangeValue);
    EntityDefinition getContextEntity();
    Object getExpressionVal(Expression expression, Context context);
    boolean verifyNumericPropertyType(PropertyInstance propertyValue);
    ActionTypeDTO getActionType();
    String getProperty();
    List<Expression> getExpressionList();


}
