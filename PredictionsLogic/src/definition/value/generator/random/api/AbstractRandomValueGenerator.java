package definition.value.generator.random.api;

import java.util.Random;

import definition.value.generator.api.ValueGenerator;

public abstract class AbstractRandomValueGenerator<T> implements ValueGenerator<T> {
    protected final Random random;

    protected AbstractRandomValueGenerator() {
        random = new Random();
    }
}
