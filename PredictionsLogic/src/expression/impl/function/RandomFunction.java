package expression.impl.function;

import expression.api.eExpression;
import expression.api.eFunctionExpression;
import expression.impl.FunctionExpression;

import java.util.Random;

class RandomFunction extends FunctionExpression {
    public RandomFunction(Object arg, eExpression typeExpression, eFunctionExpression typeFunctionExpression) {
        super(arg, typeExpression.FUNCTION, typeFunctionExpression.RANDOM);
    }

    @Override
    public Object calculateExpression(String expressionString) {
        try {
            int max = Integer.parseInt(arg.toString());
            if (max < 0) {
                throw new IllegalArgumentException("Argument must be a non-negative number.");
            }

            Random random = new Random();
            return random.nextInt(max + 1);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Argument is not a valid number.");
        }
    }
}
