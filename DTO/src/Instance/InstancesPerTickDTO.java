package Instance;

public class InstancesPerTickDTO {
    private final int tick;
    private int amount;

    public InstancesPerTickDTO(int tick, int amount) {
        this.tick = tick;
        this.amount = amount;
    }

    public int getTick() {
        return tick;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
