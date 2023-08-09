package expression.impl;

import definition.property.api.PropertyType;
import expression.api.AbstractExpression;
import expression.api.eExpression;

public class GeneralExpression extends AbstractExpression {
    private PropertyType propertyType;

    public GeneralExpression(eExpression eExpression, PropertyType propertyType) {
        super(eExpression);
        this.propertyType = propertyType;
    }
    @Override
    public Object calculateExpression(String expressionString) {
        switch (propertyType) {
            case DECIMAL:
                return Integer.parseInt(expressionString);
            case FLOAT:
                return Double.parseDouble(expressionString);
            case BOOLEAN:
                return Boolean.parseBoolean(expressionString);
            case STRING:
                return expressionString;
            default:
                return null;
        }
    }
}
