package action.api;

import Enums.ActionTypeDTO;
import definition.entity.EntityDefinition;
import definition.property.api.PropertyType;
import execution.context.Context;
import execution.instance.property.PropertyInstance;
import expression.api.Expression;

import java.util.List;

public abstract class AbstractAction implements Action {
    private ActionTypeDTO actionType;
    private final EntityDefinition primaryEntityDefinition;
    private EntityDefinition secondaryEntityDefinition;
    private List<Expression> expressionList;

    protected AbstractAction(ActionTypeDTO actionType, EntityDefinition entityDefinition, List<Expression> expressionList, EntityDefinition secondaryEntityDefinition) {
        this.actionType = actionType;
        this.primaryEntityDefinition = entityDefinition;
        this.expressionList = expressionList;
        this.secondaryEntityDefinition = secondaryEntityDefinition;
    }
    @Override
    public EntityDefinition getContextEntity() {
        return primaryEntityDefinition;
    }
    @Override
    public Object getExpressionVal(Expression expression, Context context)
    {
        return expression.calculateExpression(context);
    }
    @Override
    public boolean verifyNumericPropertyType(PropertyInstance propertyValue) {
        return PropertyType.DECIMAL.equals(propertyValue.getPropertyDefinition().getType()) || PropertyType.FLOAT.equals(propertyValue.getPropertyDefinition().getType());
    }
    public List<Expression> getExpressionList() {
        return expressionList;
    }
    public ActionTypeDTO getActionType(){return actionType;}
    @Override
    public boolean hasSecondaryEntity() {
        return secondaryEntityDefinition != null;
    }




}
