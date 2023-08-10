package action.impl;

import action.api.AbstractAction;
import action.api.ActionType;
import definition.entity.EntityDefinition;
import definition.property.api.PropertyType;
import execution.context.Context;
import execution.instance.property.PropertyInstance;
import expression.api.Expression;

import java.util.List;

public class DecreaseAction extends AbstractAction {
    private final String property;

    public DecreaseAction(ActionType actionType, EntityDefinition entityDefinition, List<Expression> expressionList, String property) {
        super(actionType, entityDefinition, expressionList);
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
            expressionVal = PropertyType.DECIMAL.convert(getExpressionVal(getExpressionList().get(0)));
            updatedVal = (Integer)propVal-(Integer)expressionVal;
        }
        else
        {
            propVal = PropertyType.FLOAT.convert(propertyInstance.getValue());
            expressionVal = PropertyType.FLOAT.convert(getExpressionVal(getExpressionList().get(0)));
            updatedVal = (Double)propVal-(Double)expressionVal;
        }
        if(propertyInstance.getPropertyDefinition().getRange() != null)
        {
            if (updatedVal.doubleValue() >= propertyInstance.getPropertyDefinition().getRange().getRangeFrom())
                propertyInstance.updateValue(updatedVal);
        }
        else
        {
            propertyInstance.updateValue(updatedVal);
        }
    }


}
