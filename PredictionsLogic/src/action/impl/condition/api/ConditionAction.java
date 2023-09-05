package action.impl.condition.api;

import Enums.ActionTypeDTO;
import action.api.AbstractAction;
import action.api.Action;
import definition.entity.EntityDefinition;
import definition.secondaryEntity.api.SecondaryEntityDefinition;
import execution.context.Context;
import expression.api.Expression;

import java.util.List;

public abstract class ConditionAction extends AbstractAction {
    protected List<Action> thenActionList;
    protected List<Action> elseActionList;
    protected String propertyName;


    protected ConditionAction(ActionTypeDTO actionType, EntityDefinition entityDefinition, List<Expression> expressionList, List<Action> thenActionList,List<Action> elseActionList, String propertyName, SecondaryEntityDefinition secondaryEntityDef) {
        super(actionType, entityDefinition, expressionList, secondaryEntityDef);
        this.thenActionList = thenActionList;
        this.elseActionList = elseActionList;
        this.propertyName = propertyName;
    }


    public abstract boolean checkCondition(Context context);

    @Override
    public void invoke(Context context,  int currTickToChangeValue) {

        if (this.checkCondition(context))
        {
            thenActionList.forEach(action -> {
                action.invoke(context, currTickToChangeValue);
            });
        } else
        {
            if(elseActionList != null)
                elseActionList.forEach(action -> {
                    action.invoke(context, currTickToChangeValue);
                });
        }

    }
}
