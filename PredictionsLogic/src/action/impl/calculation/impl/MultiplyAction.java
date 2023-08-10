package action.impl.calculation.impl;

import action.api.ActionType;
import action.impl.calculation.api.CalculationAction;
import definition.entity.EntityDefinition;
import definition.property.api.PropertyType;
import execution.context.Context;
import execution.instance.property.PropertyInstance;
import expression.api.Expression;

public class MultiplyAction extends CalculationAction {
    public MultiplyAction(ActionType actionType, EntityDefinition entityDefinition, String resultProp, String arg1, String arg2, Expression exp1, Expression exp2) {
        super(actionType, entityDefinition, resultProp, arg1, arg2, exp1, exp2);
    }

    @Override
    public void invoke(Context context) {
        PropertyInstance propertyInstance = context.getPrimaryEntityInstance().getPropertyByName(resultProp);
        if (!verifyNumericPropertyType(propertyInstance)) {
            throw new IllegalArgumentException("increase action can't operate on a none number property [" + resultProp + "]");
        }
        Object expVal1 = getExpressionVal(exp1,arg1);
        Object expVal2 = getExpressionVal(exp2,arg2);
        Object mulRes;
        if(expVal1 instanceof Double || expVal2 instanceof Double)
        {
            Double val1 = PropertyType.FLOAT.convert(expVal1);
            Double val2 = PropertyType.FLOAT.convert(expVal2);
            mulRes = val1 * val2;
        }
        else
        {
            Integer val1 = PropertyType.DECIMAL.convert(expVal1);
            Integer val2 = PropertyType.DECIMAL.convert(expVal2);
            mulRes = val1 * val2;
        }
        if(propertyInstance.getPropertyDefinition().getType() == PropertyType.DECIMAL)
        {
            if(mulRes instanceof Integer)
            {
                propertyInstance.updateValue(mulRes);
            }
            else
                throw new NumberFormatException("multiply result didn't match property type");
        }
        else
        {
            propertyInstance.updateValue(mulRes);
        }


    }
}
