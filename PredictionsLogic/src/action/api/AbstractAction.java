package action.api;

import definition.entity.EntityDefinition;
import definition.property.api.PropertyType;
import execution.instance.property.PropertyInstance;
import expression.api.Expression;

public abstract class AbstractAction implements Action {

    private final ActionType actionType;
    private final EntityDefinition entityDefinition;

    protected AbstractAction(ActionType actionType, EntityDefinition entityDefinition) {
        this.actionType = actionType;
        this.entityDefinition = entityDefinition;
    }

    @Override
    public ActionType getActionType() {
        return actionType;
    }

    @Override
    public EntityDefinition getContextEntity() {
        return entityDefinition;
    }
    @Override
    public Object getExpressionVal(Expression expression,String argument)
    {
        return expression.calculateExpression(argument);
    }
    @Override
    public boolean verifyNumericPropertyType(PropertyInstance propertyValue) {
        return PropertyType.DECIMAL.equals(propertyValue.getPropertyDefinition().getType()) || PropertyType.FLOAT.equals(propertyValue.getPropertyDefinition().getType());
    }




}
