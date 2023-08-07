package definition.value.generator.random.impl.string;


import definition.value.generator.random.api.AbstractRandomValueGenerator;

public class RandomStringGenerator extends AbstractRandomValueGenerator<String> {
    private final String charPool = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789?,!_.() ";
    private final int minLength = 1;
    private final int maxLength = 50;

    @Override
    public String generateValue() {

        int length = random.nextInt(maxLength - minLength + 1) + minLength;
        char[] randomStringChars = new char[length];

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(charPool.length());
            randomStringChars[i] = charPool.charAt(randomIndex);
        }

        String randomString = new String(randomStringChars);
        return randomString;
    }
}
