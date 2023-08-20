package expression.impl;


import execution.context.Context;
import expression.api.AbstractExpression;
import expression.api.eExpression;

public abstract class FunctionExpression extends AbstractExpression {
    protected String[] args;
    public FunctionExpression(eExpression typeExpression, String... args) {
        super(typeExpression);
        this.args= args;
    }

    public abstract Object calculateExpression(Context context);
    @Override
    public final Object getArg() {
        return args[0];
    }


}


