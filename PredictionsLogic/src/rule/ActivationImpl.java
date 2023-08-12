package rule;

import java.util.Random;

public class ActivationImpl implements Activation{

    Integer ticks = 1;
    Double probability = 1.0;

    public ActivationImpl() {
    }
    public ActivationImpl(Integer ticks, Double probability) {
        this.ticks = ticks;
        this.probability = probability;
    }

    public Integer getTicks() {
        return ticks;
    }

    public void setTicks(Integer ticks) {
        this.ticks = ticks;
    }

    public Double getProbability() {
        return probability;
    }

    public void setProbability(Double probability) {
        this.probability = probability;
    }



    @Override
    public boolean isActive(int tickNumber)
    {
        return isTicks(tickNumber) && isProb();
    }
    private boolean isProb()
    {
        if (probability == 1.0) {
            return true;
        } else if (probability == 0) {
            return false;
        }
        else
        {
            Random random = new Random();
            double myVal = random.nextDouble();
            return myVal < probability;
        }
    }
    private boolean isTicks(int tickNumber)
    {
        return (tickNumber % ticks) == 0;
    }
}
