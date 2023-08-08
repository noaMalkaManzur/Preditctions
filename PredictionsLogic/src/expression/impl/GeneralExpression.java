package expression.impl;

import definition.property.api.PropertyType;
import expression.api.AbstractExpression;
import expression.api.eExpression;

public class GeneralExpression extends AbstractExpression {
    private PropertyType propertyType;
    public GeneralExpression(eExpression eExpression) {
        super(eExpression.GENERAL);
    }

    @Override
    public Object calculateExpression(String expressionString) {
        return 0;
    }
}
