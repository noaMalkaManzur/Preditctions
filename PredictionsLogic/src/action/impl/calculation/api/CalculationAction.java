package action.impl.calculation.api;

import action.api.AbstractAction;
import action.api.ActionType;
import definition.entity.EntityDefinition;
import execution.context.Context;
import expression.api.Expression;

import java.util.List;

public abstract class CalculationAction extends AbstractAction {

    protected String resultProp;

    public CalculationAction(ActionType actionType, EntityDefinition entityDefinition, List<Expression> expressionList, String resultProp) {
        super(actionType, entityDefinition, expressionList);
        this.resultProp = resultProp;
    }

    public abstract void invoke(Context context);


}
