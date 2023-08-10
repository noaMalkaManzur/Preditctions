package action.impl.calculation.api;

import action.api.AbstractAction;
import action.api.Action;
import action.api.ActionType;
import action.api.MathActionType;
import definition.entity.EntityDefinition;
import execution.context.Context;
import execution.instance.property.PropertyInstance;
import expression.api.Expression;

public abstract class CalculationAction extends AbstractAction {

    protected String resultProp;
    protected String arg1;
    protected String arg2;
    protected Expression exp1;
    protected Expression exp2;


    public CalculationAction(ActionType actionType, EntityDefinition entityDefinition, String resultProp, String arg1, String arg2, Expression exp1, Expression exp2) {
        super(actionType, entityDefinition);
        this.resultProp = resultProp;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }
    public abstract void invoke(Context context);


}
