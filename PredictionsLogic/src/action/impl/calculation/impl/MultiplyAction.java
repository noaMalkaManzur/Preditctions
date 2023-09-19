package action.impl.calculation.impl;

import Enums.ActionTypeDTO;
import action.impl.calculation.api.CalculationAction;
import definition.entity.EntityDefinition;
import definition.property.api.PropertyType;
import definition.secondaryEntity.api.SecondaryEntityDefinition;
import execution.context.Context;
import execution.instance.property.PropertyInstance;
import expression.api.Expression;

import java.util.List;

public class MultiplyAction extends CalculationAction {

    public MultiplyAction(ActionTypeDTO actionType, EntityDefinition entityDefinition, List<Expression> expressionList, String resultProp, SecondaryEntityDefinition secondaryEntityDef) {
        super(actionType, entityDefinition, expressionList, resultProp,secondaryEntityDef);
    }

    @Override
    public void invoke(Context context,  int currTickToChangeValue) {

        PropertyInstance propertyInstance = context.getPrimaryEntityInstance().getPropertyByName(resultProp);
        if (!verifyNumericPropertyType(propertyInstance)) {
            throw new IllegalArgumentException("increase action can't operate on a none number property [" + resultProp + "]");
        }
        Object expVal1 = getExpressionVal(getExpressionList().get(0), context);
        Object expVal2 = getExpressionVal(getExpressionList().get(1), context);
        Number mulRes;
        if (expVal1 instanceof Double || expVal2 instanceof Double) {
            Double val1 = PropertyType.FLOAT.convert(expVal1);
            Double val2 = PropertyType.FLOAT.convert(expVal2);
            mulRes = val1 * val2;
        } else {
            Integer val1 = PropertyType.DECIMAL.convert(expVal1);
            Integer val2 = PropertyType.DECIMAL.convert(expVal2);
            mulRes = val1 * val2;
        }
        if (propertyInstance.getPropertyDefinition().getRange() != null) {
            if (mulRes.doubleValue() <= propertyInstance.getPropertyDefinition().getRange().getRangeTo()) {
                if (propertyInstance.getPropertyDefinition().getType() == PropertyType.DECIMAL) {
                    propertyInstance.updateValue(mulRes, currTickToChangeValue);
                }
            }
        } else {
            propertyInstance.updateValue(mulRes, currTickToChangeValue);
        }
    }

}
