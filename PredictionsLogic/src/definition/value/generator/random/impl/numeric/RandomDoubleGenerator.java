package definition.value.generator.random.impl.numeric;

import definition.value.generator.random.api.AbstractRandomValueGenerator;

public class RandomDoubleGenerator extends AbstractNumericRandomGenerator<Double> {

    public RandomDoubleGenerator(Double from, Double to) {
        super(from, to);
    }

    @Override

    public Double generateValue() {
        return from + (to - from) * random.nextDouble();
    }
}
