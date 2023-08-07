package Entities;

public class Activation
{
    private int ticks = 1;
    private double prob;
    private int ticksSinceLastActivation;

    public int getTicks() {
        return ticks;
    }

    public void setTicks(int ticks) {
        this.ticks = ticks;
    }
}
