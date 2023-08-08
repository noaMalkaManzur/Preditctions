package expression.impl;

import java.util.Random;

class RandomFunction extends FunctionExpression {
    RandomFunction(Object arg) {
        super(arg);
    }

    @Override
    public Object evaluateExpression(Object arg) {
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
