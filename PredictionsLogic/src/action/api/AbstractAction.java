package action.api;

import definition.entity.EntityDefinition;
import definition.property.api.PropertyType;
import execution.context.Context;
import execution.instance.property.PropertyInstance;
import expression.api.Expression;
import java.util.List;

public abstract class AbstractAction implements Action {


    private final EntityDefinition entityDefinition;
    private List<Expression> expressionList;

    protected AbstractAction(EntityDefinition entityDefinition, List<Expression> expressionList) {

        this.entityDefinition = entityDefinition;
        this.expressionList = expressionList;
    }


    @Override
    public EntityDefinition getContextEntity() {
        return entityDefinition;
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
}
