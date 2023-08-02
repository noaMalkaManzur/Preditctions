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

    public static class Activation
    {
        private int ticks = 1;
        private float prob;
        private int ticksSinceLastActivation;

        public int getTicks() {
            return ticks;
        }

        public void setTicks(int ticks) {
            this.ticks = ticks;
        }
    }
}
