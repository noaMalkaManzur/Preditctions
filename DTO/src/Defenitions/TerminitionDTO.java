package Defenitions;

public class TerminitionDTO
{
    private final double bySeconds;
    private final double byTicks;

    public double getBySeconds() {
        return bySeconds;
    }

    public double getByTicks() {
        return byTicks;
    }

    public TerminitionDTO(double bySeconds, double byTicks) {
        this.bySeconds = bySeconds;
        this.byTicks = byTicks;
    }
}
