package expression.impl;

import definition.property.api.PropertyType;
import execution.context.Context;
import expression.api.eExpression;

public class GeneralExpression extends FunctionExpression {
    private PropertyType propertyType;

    public GeneralExpression(PropertyType propertyType, String... args) {
        super(eExpression.GENERAL, args);
        this.propertyType = propertyType;

    }

    @Override
    public Object calculateExpression(Context context) {
        switch (propertyType) {
            case DECIMAL:
                return Integer.parseInt(args[0]);
            case FLOAT:
                return Double.parseDouble(args[0]);
            case BOOLEAN:
                return Boolean.parseBoolean(args[0]);
            case STRING:
                return args[0];
            default:
                return null;
        }
    }
    @Override
    public String toString(){
        return args[0];
    }
}
