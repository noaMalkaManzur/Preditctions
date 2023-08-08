package expression.api;

public abstract class AbstractExpression implements  Expression{

    protected Object arg;
    protected eExpression typeExpression;
    protected eFunctionExpression typeFunctionExpression;

    public AbstractExpression(Object arg, eExpression eExpression, eFunctionExpression typeFunctionExpression) {
        this.arg= arg;
        this.typeExpression = eExpression;
        this.typeFunctionExpression = typeFunctionExpression;
    }

}
