package expression.impl;

import definition.property.api.PropertyType;
import execution.context.Context;
import expression.api.eExpression;

public class GeneralExpression extends FunctionExpression {
    private PropertyType propertyType;

    public GeneralExpression(String arg,PropertyType propertyType) {
        super(arg, eExpression.GENERAL);
        this.propertyType = propertyType;

    }

    @Override
    public Object calculateExpression(Context context) {
        switch (propertyType) {
            case DECIMAL:
                return Integer.parseInt(arg);
            case FLOAT:
                return Double.parseDouble(arg);
            case BOOLEAN:
                return Boolean.parseBoolean(arg);
            case STRING:
                return arg;
            default:
                return null;
        }
    }


}
