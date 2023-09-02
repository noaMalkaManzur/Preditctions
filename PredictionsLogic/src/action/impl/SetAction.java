package action.impl;

import Enums.ActionTypeDTO;
import action.api.AbstractAction;
import definition.entity.EntityDefinition;
import execution.context.Context;
import execution.instance.property.PropertyInstance;
import expression.api.Expression;

import java.util.List;

public class SetAction extends AbstractAction
{
    private String property;


    public SetAction(EntityDefinition entityDefinition, List<Expression> expressionList, String property, EntityDefinition secondaryEntityDef) {
        super(ActionTypeDTO.SET, entityDefinition, expressionList, secondaryEntityDef);
        this.property = property;
    }


    @Override
    public void invoke(Context context, int currTickToChangeValue) {
        PropertyInstance propertyInstance = context.getPrimaryEntityInstance().getPropertyByName(property);
        if (!verifyNumericPropertyType(propertyInstance)) {
            throw new IllegalArgumentException("increase action can't operate on a none number property [" + property + "]");
        }
        Object expVal = getExpressionVal(getExpressionList().get(0), context);
        if(expVal instanceof Double || expVal instanceof Integer)
        {
            Number numVal = (Number)expVal;
            if (propertyInstance.getPropertyDefinition().getRange() != null)
                if((propertyInstance.getPropertyDefinition().getRange().getRangeTo()>= numVal.doubleValue())
                ||propertyInstance.getPropertyDefinition().getRange().getRangeFrom()<= numVal.doubleValue())
                {
                    propertyInstance.updateValue(expVal);
                    propertyInstance.setCurrTickForValueChanged(currTickToChangeValue);
                }
        }
        else
        {
            propertyInstance.updateValue(expVal);
            propertyInstance.setCurrTickForValueChanged(currTickToChangeValue);
        }
    }


}
