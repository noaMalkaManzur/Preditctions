package Entities;

public class Termination
{
    private final int maxTicks;
    private final int maxSeconds;

    public Termination(int maxTicks, int maxSeconds) {
        this.maxTicks = maxTicks;
        this.maxSeconds = maxSeconds;
    }

    public int getMaxSeconds() {
        return maxSeconds;
    }

    public int getMaxTicks() {
        return maxTicks;
    }
}
