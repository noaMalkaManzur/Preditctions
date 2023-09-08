package action.impl;

import Enums.ActionTypeDTO;
import action.api.AbstractAction;
import action.api.Action;
import definition.entity.EntityDefinition;
import definition.property.api.PropertyType;
import definition.secondaryEntity.api.SecondaryEntityDefinition;
import definition.world.impl.Cell;
import execution.context.Context;
import execution.instance.enitty.EntityInstance;
import expression.api.Expression;

import java.util.Collection;
import java.util.List;

public class ProximityAction  extends AbstractAction {
    List<Action> actionList;
    String targetName;

    public ProximityAction(EntityDefinition entityDefinition, List<Expression> expressionList,List<Action> actionList, SecondaryEntityDefinition secondaryEntityDef, String targetName ) {
        super(ActionTypeDTO.PROXIMITY, entityDefinition, expressionList, secondaryEntityDef);
        this.actionList = actionList;
        this.targetName = targetName;
    }
    @Override
    public void invoke(Context context, int currTickToChangeValue) {

        if(checkProximity(context)){
            actionList.forEach(action -> action.invoke(context, currTickToChangeValue));
        }
    }

    private boolean checkProximity(Context context){

        int rank = PropertyType.DECIMAL.convert(getExpressionVal(getExpressionList().get(0), context));
        EntityInstance primaryEntityInstance = context.getPrimaryEntityInstance();
        Collection<Cell> coordinateCollection  = context.getGrid().findEnvironmentCells(primaryEntityInstance.getCoordinate(), rank, context);

        for (Cell cell : coordinateCollection) {
            if (cell.getEntityInstance() != null && cell.getEntityInstance().getEntityDef().getName().equals(targetName)) {
                return true;
            }
        }
        return false;
    }
}
