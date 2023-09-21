package simulation.Impl;

import Defenitions.EntPopDTO;
import Defenitions.ProgressSimulationDTO;
import Defenitions.RerunInfoDTO;
import Histogram.api.Histogram;
import Histogram.impl.HistogramImpl;
import Instance.EntityPopGraphDTO;
import Instance.InstancesPerTickDTO;
import action.api.Action;
import definition.world.api.WorldDefinition;
import engine.Validaton.api.ValidationEngine;
import engine.Validaton.impl.ValidationEngineImpl;
import execution.context.Context;
import execution.context.ContextImpl;
import execution.instance.enitty.EntityInstance;
import execution.instance.enitty.manager.EntityInstanceManager;
import execution.instance.enitty.manager.EntityInstanceManagerImpl;
import execution.instance.environment.api.ActiveEnvironment;
import histogramDTO.HistogramByPropertyEntitiesDTO;
import histogramDTO.HistoryRunningSimulationDTO;
import simulation.api.SimulationManager;
import simulation.api.SimulationState;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class SimulationManagerImpl implements SimulationManager {
    private final WorldDefinition worldDefinition;
    private ActiveEnvironment simEnvironment;
    private Context context;
    Map<String, Histogram> histogramMap = new LinkedHashMap<>();
    private final String guid = UUID.randomUUID().toString();
    private  Instant StartTime;
    private Boolean isTerminated = false;
    private String terminationReason;
    private Boolean isPause = false;
    private Boolean isStop = false;
    private EntityPopGraphDTO graphDTO = new EntityPopGraphDTO();
    private RerunInfoDTO rerunInfoDTO;
    private SimulationState simState = SimulationState.PENDING;
    private ProgressSimulationDTO progressDTO = new ProgressSimulationDTO(0,0,null,simState);
    private ValidationEngine validationEngine = new ValidationEngineImpl();

    private final Object pauseLock = new Object();

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
    @Override
    public ProgressSimulationDTO getProgressDTO() {
        return progressDTO;
    }
    @Override
    public EntityPopGraphDTO getGraphDTO() {
        return graphDTO;
    }

    @Override
    public void run() {
        try {
            //region Simulation Create
            rerunInfoDTO = initReRunInfo();
            createContext();
            StartTime = Instant.now();
            simState =  SimulationState.RUNNING;
            int ticks = 0;
            List<EntPopDTO> entitiesUpdateData = new ArrayList<>();
            //endregion

            //region Simulation Run
            while (!isTerminated) {
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
                                afterConditionInstances = handleSecondaryEntityList(action, entityInstancesFiltered, context);
                                if (afterConditionInstances != null) {
                                    afterConditionInstances.forEach(secondEntity -> {
                                        context.setSecondEntity(secondEntity);
                                        action.invoke(context, finalTicks);
                                    });
                                }
                            } else {
                                action.invoke(context, finalTicks);
                            }
                        }
                    });
                });
                //region Pop per tick Create
                Map<String, InstancesPerTickDTO> popPerEntityMap = new HashMap<>();
                for (EntityInstance entityInstance : context.getEntityManager().getInstances()) {
                    String name = entityInstance.getEntityDef().getName();
                    if (popPerEntityMap.containsKey(name)) {
                        int currAmount = popPerEntityMap.get(name).getAmount();
                        popPerEntityMap.get(name).setAmount(currAmount + 1);
                    }
                    else
                    {
                        popPerEntityMap.put(name, new InstancesPerTickDTO(ticks,1));
                    }
                }
                graphDTO.getGraphData().add(popPerEntityMap);
                entitiesUpdateData.clear();

                worldDefinition.getEntities().forEach((name,entDef)->
                {
                    AtomicInteger instanceCount = new AtomicInteger(0);
                    context.getEntityManager().getInstances().forEach(entityInstance ->
                    {
                        if (entityInstance.getEntityDef().getName().equals(name)) {
                            instanceCount.incrementAndGet();
                        }
                    });
                    entitiesUpdateData.add(new EntPopDTO(name,instanceCount.get()));
                });
                //endregion
                ticks++;
                context.setCurrTick(ticks);
                activateKillAction();
                replaceActionList();

                long seconds = Duration.between(getStartTime(), Instant.now()).getSeconds();
                progressDTO = new ProgressSimulationDTO(seconds, finalTicks, entitiesUpdateData,simState);

                while (isPause) {
                    synchronized (pauseLock) {
                        try {
                            pauseLock.wait(); // Wait when isPause is true
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }

                if (worldDefinition.getTerminationTerm().getByUser() != null && worldDefinition.getTerminationTerm().getByUser() && isStop) {
                    isTerminated = true;
                    terminationReason = "Simulation has been ended by the user";
                }

                else if (validationEngine.simulationEndedByTicks(ticks, StartTime, worldDefinition)) {
                    isTerminated = true;
                    terminationReason = "The simulation has been ended by the terms shown in the file";
                }
            }
            //endregion
            simState = SimulationState.FINISHED;

            createHistogram(guid);
        } catch (Exception e) {
            terminationReason="We have ended the simulation due to an error:  "+ e;
        }
    }
    void createHistogram(String guid){
        String histogramDate = createHistogramDate();
        Map<Integer, EntityInstance> instanceMap = new LinkedHashMap<>();

        context.getEntityManager().getInstances().forEach(instance->{
            instanceMap.put(instance.getId(), instance);
        });
        Histogram histogram = new HistogramImpl(guid,histogramDate,instanceMap);
        histogramMap.put(guid,histogram);

    }
    String createHistogramDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy | HH.mm.ss");
        Date currDate = new Date();
        return sdf.format(currDate);

    }
    public HistogramByPropertyEntitiesDTO setHistogramPerProperty(String guid, String propName) {

        Map<Object, Integer> histogramByProperty = new LinkedHashMap<>();
        Histogram histogram = histogramMap.get(guid);
        histogram.getEntitiesInstances().forEach((sNameEntityIns, entityInstance) -> {
            Object propValue = entityInstance.getPropertyByName(propName).getValue();
            if (histogramByProperty.containsKey(propValue)) {
                histogramByProperty.put(propValue, histogramByProperty.get(propValue) + 1);
            } else {
                histogramByProperty.put(propValue, 1);
            }
        });
        HistogramByPropertyEntitiesDTO histogramByPropertyEntitiesDTO  = new HistogramByPropertyEntitiesDTO(histogramByProperty);

        return histogramByPropertyEntitiesDTO;
    }

    public HistoryRunningSimulationDTO createHistoryRunningSimulationDTO() {
        Map<String, String> history = new LinkedHashMap<>();
        for (Map.Entry<String, Histogram> entry : histogramMap.entrySet()) {
            history.put(entry.getKey(), entry.getValue().getSimulationTime());
        }
        HistoryRunningSimulationDTO historyRunningSimulationDTO = new HistoryRunningSimulationDTO(history);
        return historyRunningSimulationDTO;
    }
    private void createContext() {
        EntityInstanceManager entityInstanceManager = new EntityInstanceManagerImpl();
        AtomicInteger index = new AtomicInteger(0);

        worldDefinition.getEntities().forEach((key, value) -> {
            int population = value.getPopulation();
            for (int i = 0; i < population; i++) {
                int currentIndex = index.getAndIncrement();
                entityInstanceManager.createEntityInstance(value);
                entityInstanceManager.getInstances().get(currentIndex).setCoordinate(worldDefinition.getGrid().getRandomCoordinateInit(entityInstanceManager.getInstances().get(currentIndex)));
            }
        });
        context = new ContextImpl(null,entityInstanceManager,simEnvironment, null, worldDefinition.getGrid());
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
    public ActiveEnvironment getSimEnvironment() {
        return simEnvironment;
    }
    public void setSimEnvironment(ActiveEnvironment simEnvironment) {
        this.simEnvironment = simEnvironment;
    }
    public SimulationState getState()
    {
        return simState;
    }
    @Override
    public void setPause(Boolean pause) {
        isPause = pause;
        synchronized (pauseLock) {
            if (!isPause) {
                pauseLock.notify();
            }
        }
    }
    private RerunInfoDTO initReRunInfo() {
        Map<String,Integer> entPopMap = new HashMap<>();
        Map<String,String> envVarMap = new HashMap<>();
        worldDefinition.getEntities().forEach((s,e)->{
            entPopMap.put(s,e.getPopulation());
        });
        simEnvironment.getProperties().forEach((s,p)->{
            envVarMap.put(s,p.getValue().toString());
        });
        return new RerunInfoDTO(entPopMap,envVarMap);
    }
    @Override
    public RerunInfoDTO getReRunInfoDTO()
    {
        return rerunInfoDTO;
    }


    @Override
    public void setIsTerminated()
    {
        synchronized (pauseLock) {
            isPause = false;
            isStop = true;
            pauseLock.notify();
        }
    }
}
