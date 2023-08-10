package action.impl;

import action.api.AbstractAction;
import action.api.ActionType;
import definition.entity.EntityDefinition;
import definition.property.api.PropertyType;
import execution.context.Context;
import execution.instance.property.PropertyInstance;
import expression.api.Expression;

public class DecreaseAction extends AbstractAction {
    private final String property;
    private final String byExpression;
    private Expression expression;

    public DecreaseAction(ActionType actionType, EntityDefinition entityDefinition, String property, String byExpression, Expression expression) {
        super(actionType, entityDefinition);
        this.property = property;
        this.byExpression = byExpression;
        this.expression = expression;
    }

    @Override
    public void invoke(Context context) {
        PropertyInstance propertyInstance = context.getPrimaryEntityInstance().getPropertyByName(property);
        if (!verifyNumericPropertyType(propertyInstance)) {
            throw new IllegalArgumentException("increase action can't operate on a none number property [" + property + "]");
        }
        Object propVal;
        Object expressionVal;
        Object updatedVal;
        double rangeFrom = propertyInstance.getPropertyDefinition().getRange().getRangeFrom();

        if(PropertyType.DECIMAL.equals(propertyInstance.getPropertyDefinition().getType()))
        {
            propVal = PropertyType.DECIMAL.convert(propertyInstance.getValue());
            expressionVal = PropertyType.DECIMAL.convert(getExpressionVal(expression,byExpression));
            updatedVal = (Integer)propVal-(Integer)expressionVal;
        }
        else
        {
            propVal = PropertyType.FLOAT.convert(propertyInstance.getValue());
            expressionVal = PropertyType.FLOAT.convert(getExpressionVal(expression,byExpression));
            updatedVal = (Double)propVal-(Double)expressionVal;
        }
        if(((Number) updatedVal).doubleValue() >= rangeFrom)
        {
            propertyInstance.updateValue(updatedVal);
        }
    }


}
