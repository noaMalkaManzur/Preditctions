package action.impl;

import action.api.AbstractAction;
import action.api.ActionType;
import com.sun.xml.internal.bind.v2.TODO;
import definition.entity.EntityDefinition;
import definition.property.api.PropertyType;
import execution.context.Context;
import execution.instance.property.PropertyInstance;
import expression.api.Expression;

public class IncreaseAction extends AbstractAction {

    private final String property;
    private final String byExpression;
    private  Expression expression;
    public IncreaseAction(ActionType actionType, EntityDefinition entityDefinition, String property, String byExpression, Expression expression) {
        super(actionType, entityDefinition);
        this.property = property;
        this.byExpression = byExpression;
        this.expression = expression;
    }
    //ToDo: Check if envariable can be double.
    @Override
    public void invoke(Context context) {
        PropertyInstance propertyInstance = context.getPrimaryEntityInstance().getPropertyByName(property);
        if (!verifyNumericPropertyType(propertyInstance)) {
            throw new IllegalArgumentException("increase action can't operate on a none number property [" + property + "]");
        }
        Object propVal;
        Object expressionVal;
        Object updatedVal;
        double rangeTo = propertyInstance.getPropertyDefinition().getRange().getRangeTo();

        if(PropertyType.DECIMAL.equals(propertyInstance.getPropertyDefinition().getType()))
        {
            propVal = PropertyType.DECIMAL.convert(propertyInstance.getValue());
            expressionVal = PropertyType.DECIMAL.convert(getExpressionVal(expression,byExpression));
            updatedVal = (Integer)propVal+(Integer)expressionVal;
        }
        else
        {
            propVal = PropertyType.FLOAT.convert(propertyInstance.getValue());
            expressionVal = PropertyType.FLOAT.convert(getExpressionVal(expression,byExpression));
            updatedVal = (Double)propVal+(Double)expressionVal;
        }
        if(((Number) updatedVal).doubleValue() <= rangeTo)
        {
            propertyInstance.updateValue(updatedVal);
        }
    }
}
