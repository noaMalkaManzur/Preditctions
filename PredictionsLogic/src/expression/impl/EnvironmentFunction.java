package expression.impl;

import execution.instance.environment.api.ActiveEnvironment;
import expression.api.eExpression;
import expression.impl.FunctionExpression;

public class EnvironmentFunction extends FunctionExpression {
    private ActiveEnvironment activeEnvironment;
    public EnvironmentFunction(String arg, ActiveEnvironment activeEnvironment) {
        super(arg, eExpression.FUNCTION);
        this.activeEnvironment = activeEnvironment;
    }

    @Override
    public Object calculateExpression() {
        return activeEnvironment.getProperty(arg).getValue();

    }
}
