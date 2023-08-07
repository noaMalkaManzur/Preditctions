package rule;

import java.util.List;

import action.api.Action;

public interface Rule {
    String getName();
    Activation getActivation();
    List<Action> getActionsToPerform();
    void addAction(Action action);
}
