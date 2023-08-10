package expression.impl;

import expression.api.eExpression;
import expression.impl.FunctionExpression;

import java.util.Random;


public class RandomFunction extends FunctionExpression {

    public RandomFunction(String arg) {
        super(arg, eExpression.FUNCTION);
    }
    @Override
    public Object calculateExpression() {
        try {
            int max = Integer.parseInt(arg);
            if (max < 0) {
                throw new IllegalArgumentException("Argument must be a non-negative number.");
            }
            return random(max);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Argument is not a valid number.");
        }
    }
    private Integer random(int range) {
        Random random = new Random();
        return random.nextInt(range+1);
    }
}
