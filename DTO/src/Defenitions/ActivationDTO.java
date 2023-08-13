package Defenitions;

public class ActivationDTO
{
    private final double probabilty;
    private final int ticks;

    public ActivationDTO(double probabilty, int ticks) {
        this.probabilty = probabilty;
        this.ticks = ticks;
    }

    public double getProbabilty() {
        return probabilty;
    }

    public int getTicks() {
        return ticks;
    }
}
