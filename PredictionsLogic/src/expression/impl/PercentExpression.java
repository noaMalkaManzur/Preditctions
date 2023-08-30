package expression.impl;

import execution.context.Context;
import expression.api.Expression;
import expression.api.eExpression;

public class PercentExpression extends FunctionExpression{
    Expression expression1;
    Expression expression2;
    public PercentExpression(Expression expression1, Expression expression2) {
        super(eExpression.FUNCTION);
        this.expression1 = expression1;
        this.expression2 = expression2;
    }
    @Override
    public Object calculateExpression(Context context) {
        Object value1 =  expression1.calculateExpression(context);
        Object value2 = expression2.calculateExpression(context);

        if (Double.parseDouble(value2.toString()) == 0) {
            throw new IllegalArgumentException("Second argument cannot be zero");
        }
        //todo: need to ask aviad about the type we are sending back, there is no decimal
        else{
            return Double.parseDouble(value1.toString()) /Double.parseDouble(value2.toString());
        }
    }
}
