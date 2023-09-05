package Defenitions;

public class ActivationDTO
{
    private final double probability;
    private final int ticks;

    public ActivationDTO(double probability, int ticks) {
        this.probability = probability;
        this.ticks = ticks;
    }

    public double getProbability() {
        return probability;
    }

    public int getTicks() {
        return ticks;
    }
}
