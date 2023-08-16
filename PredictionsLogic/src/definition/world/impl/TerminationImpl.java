package definition.world.impl;

import definition.world.api.Termination;

public class TerminationImpl implements Termination {
    private final double bySeconds;
    private final double byTicks;

    public TerminationImpl(double byTicks,double bySeconds){
        this.bySeconds= bySeconds;
        this.byTicks = byTicks;
    }
    @Override
    public double getBySeconds() {
        return bySeconds;
    }

    @Override
    public double getByTicks() {
        return byTicks;
    }
}
