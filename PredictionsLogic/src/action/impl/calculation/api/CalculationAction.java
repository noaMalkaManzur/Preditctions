package action.impl.calculation.api;

import Enums.ActionTypeDTO;
import action.api.AbstractAction;
import definition.entity.EntityDefinition;
import definition.secondaryEntity.api.SecondaryEntityDefinition;
import execution.context.Context;
import expression.api.Expression;

import java.util.List;

public abstract class CalculationAction extends AbstractAction {

    protected String resultProp;

    protected CalculationAction(ActionTypeDTO actionType, EntityDefinition entityDefinition, List<Expression> expressionList, String resultProp, SecondaryEntityDefinition secondaryEntityDef) {
        super(actionType, entityDefinition, expressionList, secondaryEntityDef);
        this.resultProp = resultProp;
    }
    public abstract void invoke(Context context,  int currTickToChangeValue);


}
