package action.impl;

import Enums.ActionTypeDTO;
import action.api.AbstractAction;
import action.api.Action;
import definition.entity.EntityDefinition;
import definition.secondaryEntity.api.SecondaryEntityDefinition;
import definition.world.impl.Cell;
import definition.world.impl.Coordinate;
import execution.context.Context;
import execution.instance.enitty.EntityInstance;
import expression.api.Expression;

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

    private boolean checkProximity(Context context) {
        int rank = getRank(getExpressionVal(getExpressionList().get(0), context));

        if (rank <= 0) {
            throw new IllegalArgumentException("Rank must be a positive number.");
        }

        EntityInstance primaryEntityInstance = context.getPrimaryEntityInstance();
        List<Coordinate> coordinateList = context.getGrid().findEnvironmentCells(primaryEntityInstance.getCoordinate(), rank);

        if(coordinateList != null) {
            for (Coordinate coordinate : coordinateList) {
                for (Cell cell : context.getCells()) {
                    if (cell.getIsOccupied() && coordinate.getY() == cell.getCoordinate().getY() &&
                            coordinate.getX() == cell.getCoordinate().getX() &&
                            cell.getEntityInstance().getEntityDef().getName().equals(targetName)) {
                        context.setSecondEntity(cell.getEntityInstance());
                        return true;
                    }
                }
            }
        }
        return false;
    }


    private int getRank(Object expressionVal) {
        try{
            Number rank = (Number)expressionVal;
            return rank.intValue();
        }
        catch (Exception exception){
            throw new IllegalArgumentException("arg not a number");
        }
    }
}
