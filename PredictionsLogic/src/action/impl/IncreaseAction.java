package action.impl;

import action.api.AbstractAction;
import definition.entity.EntityDefinition;
import definition.property.api.PropertyType;
import execution.context.Context;
import execution.instance.property.PropertyInstance;
import expression.api.Expression;

import java.util.List;

public class IncreaseAction extends AbstractAction {

    private final String property;

    public IncreaseAction(EntityDefinition entityDefinition, List<Expression> expressionList, String property) {
        super(entityDefinition, expressionList);
        this.property = property;
    }
    @Override
    public void invoke(Context context) {
        PropertyInstance propertyInstance = context.getPrimaryEntityInstance().getPropertyByName(property);
        if (!verifyNumericPropertyType(propertyInstance)) {
            throw new IllegalArgumentException("increase action can't operate on a none number property [" + property + "]");
        }
        Object propVal;
        Object expressionVal;
        Number updatedVal;

        if(PropertyType.DECIMAL.equals(propertyInstance.getPropertyDefinition().getType()))
        {
            propVal = PropertyType.DECIMAL.convert(propertyInstance.getValue());
            expressionVal = PropertyType.DECIMAL.convert(getExpressionVal(getExpressionList().get(0), context));
            updatedVal = (Integer)propVal+(Integer)expressionVal;
        }
        else
        {
            propVal = PropertyType.FLOAT.convert(propertyInstance.getValue());
            expressionVal = PropertyType.FLOAT.convert(getExpressionVal(getExpressionList().get(0), context));
            updatedVal = (Double)propVal+(Double)expressionVal;
        }
        if(propertyInstance.getPropertyDefinition().getRange() != null)
        {
            if (updatedVal.doubleValue() <= propertyInstance.getPropertyDefinition().getRange().getRangeTo())
                propertyInstance.updateValue(updatedVal);
        }
        else
        {
            propertyInstance.updateValue(updatedVal);
        }
    }
}
