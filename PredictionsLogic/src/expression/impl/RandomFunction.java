package expression.impl;

import execution.context.Context;
import expression.api.eExpression;

import java.util.Random;


public class RandomFunction extends FunctionExpression {

    public RandomFunction(String... args) {
        super(eExpression.FUNCTION, args);
    }
    @Override
    public Object calculateExpression(Context context) {
        try {
            int max = Integer.parseInt(args[0]);
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
    @Override
    public String toString(){
        return "random(" + args[0] + ")";
    }
}
