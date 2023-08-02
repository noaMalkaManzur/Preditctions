package Actions;

import Entities.*;

public abstract class Action {
    protected Entity entity;

    public Action(Entity entity) {
        this.entity = entity;
    }

    public abstract void DoAction();
}
