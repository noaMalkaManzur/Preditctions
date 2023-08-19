package expression.impl;


import execution.context.Context;
import expression.api.AbstractExpression;
import expression.api.eExpression;

public abstract class FunctionExpression extends AbstractExpression {
    protected String arg;
    public FunctionExpression(String arg, eExpression typeExpression) {
        super(typeExpression);
        this.arg = arg;
    }

    public abstract Object calculateExpression(Context context);
    @Override
    public final Object getArg() {
        return arg;
    }




}


