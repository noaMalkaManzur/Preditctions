package simulation.Impl;

import action.api.Action;
import definition.world.api.WorldDefinition;
import execution.context.Context;
import execution.context.ContextImpl;
import execution.instance.enitty.EntityInstance;
import execution.instance.enitty.manager.EntityInstanceManager;
import execution.instance.enitty.manager.EntityInstanceManagerImpl;
import execution.instance.environment.api.ActiveEnvironment;
import simulation.api.SimulationManager;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;

public class SimulationManagerImpl implements SimulationManager {

    private final WorldDefinition worldDefinition;
    private ActiveEnvironment simEnvironment;
    private Context context;
    private final String guid = UUID.randomUUID().toString();
    private final Instant StartTime = Instant.now();
    private Boolean isTerminated = false;
    private String terminationReason;
    private Boolean isPause = false;

    public SimulationManagerImpl(WorldDefinition worldDefinition,ActiveEnvironment simEnvironment) {
        this.worldDefinition = worldDefinition;
        this.simEnvironment = simEnvironment;
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public String getGuid() {
        return guid;
    }

    @Override
    public Instant getStartTime() {
        return StartTime;
    }

    @Override
    public Integer getCurrTick() {
        return null;
    }

    @Override
    public long getCurrentSecond() {
        return Duration.between(getStartTime(), Instant.now()).toMillis()/ 1000;
    }

    @Override
    public Boolean getIsTerminated() {
        return isTerminated;
    }

    @Override
    public String getSimulationEndReason() {
        return terminationReason;
    }

    public ActiveEnvironment getSimEnvironment() {
        return simEnvironment;
    }

    public void setSimEnvironment(ActiveEnvironment simEnvironment) {
        this.simEnvironment = simEnvironment;
    }

    @Override
    public void run() {

        createContext();
        int ticks = 0;
        boolean isTerminated = false;

        List<EntityInstance> afterConditionList = new ArrayList<>();
        while (!isTerminated)
        {
            int finalTicks = ticks;
            List<Action> activeAction = new ArrayList<>();

            moveEntities();
            activeAction = getActiveAction(finalTicks);
            List<Action> finalActiveAction = activeAction;

            context.getEntityManager().getInstances().forEach(entityInstance -> {
                context.setPrimaryInstance(entityInstance.getId());
                finalActiveAction.forEach(action -> {
                    if (action.getContextEntity().getName().equals(entityInstance.getEntityDef().getName())) {
                        if (action.hasSecondaryEntity()) {
                            List<EntityInstance> afterConditionInstances = new ArrayList<>();
                            String secondaryEntityName = action.getSecondaryEntityDefinition().getName();
                            List<EntityInstance> entityInstancesFiltered = context.getEntityManager().getInstances().stream()
                                    .filter(entityInstance1 -> entityInstance1.getEntityDef().getName().equals(secondaryEntityName)).collect(Collectors.toList());
                            afterConditionInstances  = handleSecondaryEntityList(action, entityInstancesFiltered, context);
                            if (afterConditionInstances != null) {
                                afterConditionInstances.forEach(secondEntity -> {
                                    context.setSecondEntity(secondEntity);
                                    action.invoke(context, finalTicks);
                                });
                            }
                        }
                        else{
                            action.invoke(context, finalTicks);
                        }
                    }
                });
            });

            ticks++;
            context.setCurrTick(ticks);
            activateKillAction();
            replaceActionList();

            while(isPause)
            {
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            if(ticks==840/*validationEngine.simulationEnded(ticks,simulationStart, world)*/)
                isTerminated = true;
        }
        String endReason ="steam" /*getTerminationReason(ticks,simulationStart)*/;
        //createHistogram(Guid);
    }
    private void createContext() {
        EntityInstanceManager entityInstanceManager = new EntityInstanceManagerImpl();
        EntityInstance primaryEntityInstance = null;

        AtomicInteger index = new AtomicInteger(0);

        worldDefinition.getEntities().forEach((key, value) -> {
            int population = value.getPopulation();
            for (int i = 0; i < population; i++) {
                int currentIndex = index.getAndIncrement();
                entityInstanceManager.createEntityInstance(value);
                entityInstanceManager.getInstances().get(currentIndex).setCoordinate(worldDefinition.getGrid().getRandomCoordinateInit(entityInstanceManager.getInstances().get(currentIndex)));
            }
        });
        primaryEntityInstance = entityInstanceManager.getInstances().get(0);
        context = new ContextImpl(primaryEntityInstance,entityInstanceManager,simEnvironment, null, worldDefinition.getGrid());
    }
    private void moveEntities() {
        context.getEntityManager().getInstances().forEach(entityInstance ->
                entityInstance.setCoordinate(worldDefinition.getGrid().getNextMove(entityInstance)));
    }
    private List<Action> getActiveAction(int finalTicks) {
        List<Action> activeAction = new ArrayList<>();
        worldDefinition.getRules().forEach((name, rule) ->
        {
            if (rule.getActivation().isActive(finalTicks)) {
                activeAction.addAll(rule.getActionsToPerform());
            }
        });
        return activeAction;
    }
    private List<EntityInstance> handleSecondaryEntityList(Action action, List<EntityInstance> entityInstancesFiltered, Context context) {
        String count = action.getSecondaryEntityDefinition().getCount();
        List<EntityInstance> afterFilterSelection = new ArrayList<>();

        if (entityInstancesFiltered.isEmpty()) {
            return Collections.emptyList();
        }
        //todo:change it
        if (action.getSecondaryEntityDefinition().getConditionAction() == null && action.getSecondaryEntityDefinition().getCount() == null) {
            return entityInstancesFiltered.subList(0, Math.min(1, entityInstancesFiltered.size()));
        }

        for (EntityInstance entityInstance : entityInstancesFiltered) {
            if (action.getSecondaryEntityDefinition().getConditionAction().checkCondition(context)) {
                afterFilterSelection.add(entityInstance);
            }
        }

        if (count.toLowerCase().contains("all")) {
            return afterFilterSelection;
        } else {
            try {
                int counter = Integer.parseInt(count);
                int size = Math.min(counter, afterFilterSelection.size());
                List<EntityInstance> randomList = new ArrayList<>(afterFilterSelection);
                Collections.shuffle(randomList);
                return randomList.subList(0, size);

            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Selection for secondary entity is not a number or 'all'");
            }
        }
    }
    private void activateKillAction() {
        context.getEntityManager().getKillList().forEach(idToKill ->
        {
            context.getEntityManager().executeKill(idToKill);
            context.getGrid().clearCell(idToKill);
        });
        context.getEntityManager().clearKillList();
    }
    private void replaceActionList() {
        context.getEntityManager().getReplaceEntityList().forEach(entityInstance -> {
            context.getEntityManager().addEntityInstance(entityInstance);
            context.getGrid().setNewCell(entityInstance);
        });
        context.getEntityManager().ClearReplaceList();
    }
}
