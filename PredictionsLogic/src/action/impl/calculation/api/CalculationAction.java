package action.impl.calculation.api;

import action.api.AbstractAction;
import definition.entity.EntityDefinition;
import execution.context.Context;
import expression.api.Expression;

import java.util.List;

public abstract class CalculationAction extends AbstractAction {

    protected String resultProp;

    public CalculationAction(EntityDefinition entityDefinition, List<Expression> expressionList, String resultProp) {
        super(entityDefinition, expressionList);
        this.resultProp = resultProp;
    }

    public abstract void invoke(Context context);


}
