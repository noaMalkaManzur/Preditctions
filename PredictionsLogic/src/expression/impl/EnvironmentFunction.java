package expression.impl;

import execution.context.Context;
import expression.api.eExpression;

public class EnvironmentFunction extends FunctionExpression {

    public EnvironmentFunction(String... args) {
        super(eExpression.FUNCTION, args);

    }
    @Override
    public Object calculateExpression(Context context) {
        return context.getEnvironmentVariable(args[0]).getValue();
    }
    @Override
    public String toString(){
        return "environment(" + args[0] +")";
    }


}
