package action.impl;

import Enums.ActionTypeDTO;
import action.api.AbstractAction;
import action.api.Action;
import definition.entity.EntityDefinition;
import definition.property.api.PropertyType;
import definition.world.impl.Coordinate;
import definition.world.impl.Grid;
import execution.context.Context;
import execution.instance.enitty.EntityInstance;
import expression.api.Expression;

import java.util.Collection;
import java.util.List;

public class ProximityAction  extends AbstractAction {
    List<Action> actionList;

    public ProximityAction(EntityDefinition entityDefinition, List<Expression> expressionList,List<Action> actionList ) {
        super(ActionTypeDTO.PROXIMITY, entityDefinition, expressionList);
        this.actionList = actionList;

    }

    @Override
    public void invoke(Context context, int currTickToChangeValue) {

        if(checkProximity(context)){
            actionList.forEach(action -> action.invoke(context, currTickToChangeValue));
        }
    }
    private boolean checkProximity(Context context){
        EntityInstance primaryEntityInstance = context.getPrimaryEntityInstance();
        EntityInstance secondaryEntityInstance = context.getSecondaryEntityInstance();

        Coordinate primaryEntityCoordinate = primaryEntityInstance.getCoordinate();
        Coordinate secondaryEntityCoordinate = secondaryEntityInstance.getCoordinate();
        //todo: check if it is ok to use decimal because the rank need to be int!!
        int rank = PropertyType.DECIMAL.convert(getExpressionVal(getExpressionList().get(0), context));
        Grid grid = new Grid(context.getRows(), context.getColumns());
        Collection<Coordinate> coordinateCollection =  grid.findEnvironmentCells(primaryEntityCoordinate, rank);
        return checkIfCoordinateInCollection(coordinateCollection, secondaryEntityCoordinate);
    }
    private boolean checkIfCoordinateInCollection(Collection<Coordinate> coordinateCollection, Coordinate secondaryEntityCoordinate) {
        for (Coordinate coordinate : coordinateCollection) {
            if (coordinate.getX() == secondaryEntityCoordinate.getX() && coordinate.getY() == secondaryEntityCoordinate.getY()) {
                return true;
            }
        }
        return false;
    }
}
