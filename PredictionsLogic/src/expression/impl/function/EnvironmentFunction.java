package expression.impl.function;

import execution.instance.environment.api.ActiveEnvironment;
import expression.api.eExpression;
import expression.api.eFunctionExpression;
import expression.impl.FunctionExpression;

public class EnvironmentFunction extends FunctionExpression {
    private ActiveEnvironment activeEnvironment;
    public EnvironmentFunction(String arg) {
        super(arg, typeExpression.FUNCTION, typeFunctionExpression.ENVIRONMENT);
    }

    @Override
    public Object calculateExpression(String expressionString) {
        return activeEnvironment.getProperty(expressionString).getValue();

    }
}
