package action.impl.proximity.impl;

import Enums.ActionTypeDTO;
import action.api.AbstractAction;
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

    public ProximityAction(EntityDefinition entityDefinition, List<Expression> expressionList) {
        super(ActionTypeDTO.PROXIMITY, entityDefinition, expressionList);

    }

    @Override
    public void invoke(Context context, int currTickToChangeValue) {

        if(checkProximity(context)){
            //todo: start another action
        }

    }
    private boolean checkProximity(Context context){
        EntityInstance primaryEntityInstance = context.getPrimaryEntityInstance();
        EntityInstance secondaryEntityInstance = context.getSecondaryEntityInstance();

        Coordinate primaryEntityCoordinate = primaryEntityInstance.getCoordinate();
        Coordinate secondaryEntityCoordinate = secondaryEntityInstance.getCoordinate();

        int rank = PropertyType.FLOAT.convert(getExpressionVal(getExpressionList().get(0), context));

        Grid grid = new Grid(context.getRows(), context.getColumns());
        Collection<Coordinate> coordinateCollection =  grid.findEnvironmentCells(primaryEntityCoordinate, rank);
        return coordinateCollection.contains(secondaryEntityCoordinate);
    }
}
