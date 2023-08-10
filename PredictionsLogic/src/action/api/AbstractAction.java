package action.api;

import definition.entity.EntityDefinition;
import definition.property.api.PropertyType;
import execution.instance.property.PropertyInstance;
import expression.api.Expression;

import java.util.List;

public abstract class AbstractAction implements Action {

    private final ActionType actionType;
    private final EntityDefinition entityDefinition;
    private List<Expression> expressionList;

    protected AbstractAction(ActionType actionType, EntityDefinition entityDefinition, List<Expression> expressionList) {
        this.actionType = actionType;
        this.entityDefinition = entityDefinition;
        this.expressionList = expressionList;
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
    public Object getExpressionVal(Expression expression)
    {
        return expression.calculateExpression();
    }
    @Override
    public boolean verifyNumericPropertyType(PropertyInstance propertyValue) {
        return PropertyType.DECIMAL.equals(propertyValue.getPropertyDefinition().getType()) || PropertyType.FLOAT.equals(propertyValue.getPropertyDefinition().getType());
    }


    public List<Expression> getExpressionList() {
        return expressionList;
    }
}
