package expression.api;

public abstract class AbstractExpression implements  Expression{

    protected eExpression typeExpression;
    public AbstractExpression(eExpression eExpression) {
        this.typeExpression = eExpression;
    }
    @Override
    public final eExpression getType()
    {
        return typeExpression;
    }

}
