package definition.world.impl;

import definition.world.api.Termination;

public class TerminationImpl implements Termination {
    private final Integer bySeconds;
    private final Integer byTicks;
    private final Boolean byUser;

    public TerminationImpl(Integer byTicks, Integer bySeconds, Boolean byUser){
        this.bySeconds= bySeconds;
        this.byTicks = byTicks;
        this.byUser = byUser;
    }
    @Override
    public Integer getBySeconds() {
        return bySeconds;
    }

    @Override
    public Integer getByTicks() {
        return byTicks;
    }

    @Override
    public Boolean getByUser() {
        return byUser;
    }
}
