package expression.impl;


public abstract class FunctionExpression {

    private Object arg;
    FunctionExpression(Object arg){
        this.arg= arg;
    }

    public abstract Object evaluateExpression(Object arg);
}


