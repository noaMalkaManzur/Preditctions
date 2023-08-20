package engine.impl;

import Defenitions.*;
import Enums.ActionTypeDTO;
import Generated.*;
import Histogram.api.Histogram;
import Histogram.impl.HistogramImpl;
import Instance.ActiveEnvDTO;
import Instance.EnvPropertyInstanceDTO;
import action.api.Action;
import action.impl.DecreaseAction;
import action.impl.IncreaseAction;
import action.impl.KillAction;
import action.impl.SetAction;
import action.impl.calculation.impl.DivideAction;
import action.impl.calculation.impl.MultiplyAction;
import action.impl.condition.api.ConditionAction;
import action.impl.condition.impl.MultipleAction;
import action.impl.condition.impl.SingleAction;
import definition.entity.EntityDefinition;
import definition.entity.EntityDefinitionImpl;
import definition.environment.api.EnvVariablesManager;
import definition.environment.impl.EnvVariableManagerImpl;
import definition.property.api.PropertyDefinition;
import definition.property.api.PropertyType;
import definition.property.api.Range;
import definition.property.impl.BooleanPropertyDefinition;
import definition.property.impl.FloatPropertyDefinition;
import definition.property.impl.IntegerPropertyDefinition;
import definition.property.impl.StringPropertyDefinition;
import definition.value.generator.api.ValueGenerator;
import definition.value.generator.fixed.FixedValueGenerator;
import definition.value.generator.random.impl.bool.RandomBooleanGenerator;
import definition.value.generator.random.impl.numeric.RandomDoubleGenerator;
import definition.value.generator.random.impl.numeric.RandomIntegerGenerator;
import definition.value.generator.random.impl.string.RandomStringGenerator;
import definition.world.api.Termination;
import definition.world.api.WorldDefinition;
import definition.world.impl.TerminationImpl;
import definition.world.impl.WorldImpl;
import engine.Validaton.api.ValidationEngine;
import engine.Validaton.impl.ValidationEngineImpl;
import engine.api.Engine;
import exceptions.*;
import execution.context.Context;
import execution.context.ContextImpl;
import execution.instance.enitty.EntityInstance;
import execution.instance.enitty.manager.EntityInstanceManager;
import execution.instance.enitty.manager.EntityInstanceManagerImpl;
import execution.instance.environment.api.ActiveEnvironment;
import execution.instance.environment.impl.ActiveEnvironmentImpl;
import execution.instance.property.PropertyInstanceImpl;
import expression.api.Expression;
import expression.impl.EnvironmentFunction;
import expression.impl.GeneralExpression;
import expression.impl.PropertyExpression;
import expression.impl.RandomFunction;
import histogramDTO.HistogramByAmountEntitiesDTO;
import histogramDTO.HistogramByPropertyEntitiesDTO;
import histogramDTO.HistoryRunningSimulationDTO;
import rule.ActivationImpl;
import rule.Rule;
import rule.RuleImpl;
import simulationInfo.SimulationInfoDTO;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

public class EngineImpl implements Engine {
    private WorldDefinition world;
    private Context context;
    Map<String, Histogram> histogramMap = new HashMap<>();
    private ActiveEnvironment activeEnvironment;
    private int primaryEntStartPop;
    private int currTick;
    ValidationEngine validationEngine = new ValidationEngineImpl();

    //region Command number 1


    @Override
    public void loadXmlFiles(String fileName) {
        try {
            if (validationEngine.isFileExist(fileName)) {
                if (validationEngine.isXMLFile(fileName)) {
                    File file = new File(fileName);
                    JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
                    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                    PRDWorld prdWorld = (PRDWorld) jaxbUnmarshaller.unmarshal(file);
                    EnvVariablesManager envManager = new EnvVariableManagerImpl();
                    world = new WorldImpl();
                    activeEnvironment = new ActiveEnvironmentImpl();
                    setEnvVariablesFromXML(envManager, prdWorld.getPRDEvironment().getPRDEnvProperty());
                    world.setEnvVariables(envManager);
                    world.setEntities(getEntitiesFromXML(prdWorld.getPRDEntities()));
                    world.setRules(getRulesFromXML(prdWorld.getPRDRules()));
                    world.setTerminationTerm(getTerminationTermFromXML(prdWorld.getPRDTermination()));
                }
            }
        } catch (JAXBException | FileNotFoundException | BadFileSuffixException e) {
            throw new RuntimeException(e);
        }
    }

    //region Enviroment
    private void setEnvVariablesFromXML(EnvVariablesManager envManager, List<PRDEnvProperty> prdEnvProperty) {
        prdEnvProperty.stream()
                .filter(envProp -> {
                        return validationEngine.isValidEnvProp(envProp, envManager);
                })
                .map(this::convertEnvProp)
                .forEach(envManager::addEnvironmentVariable);
    }

    private PropertyDefinition convertEnvProp(PRDEnvProperty prop) {
        ValueGenerator MyValGen;
        Range myPropRange = null;
        switch (prop.getType().toUpperCase()) {
            case "DECIMAL": {
                if (prop.getPRDRange() != null) {
                    myPropRange = new Range(prop.getPRDRange().getFrom(), prop.getPRDRange().getTo());
                    MyValGen = new RandomIntegerGenerator((int) prop.getPRDRange().getFrom(), (int) prop.getPRDRange().getTo());
                }
                else
                    MyValGen = new RandomIntegerGenerator(null,null);
                return new IntegerPropertyDefinition(prop.getPRDName(), PropertyType.DECIMAL, MyValGen, myPropRange, true, currTick);
            }
            case "FLOAT": {
                if (prop.getPRDRange() != null) {
                    myPropRange = new Range(prop.getPRDRange().getFrom(), prop.getPRDRange().getTo());
                    MyValGen = new RandomDoubleGenerator(prop.getPRDRange().getFrom(), prop.getPRDRange().getTo());
                }
                else
                    MyValGen = new RandomIntegerGenerator(null,null);
                return new FloatPropertyDefinition(prop.getPRDName(), PropertyType.FLOAT, MyValGen, myPropRange, true, currTick);
            }
            case "BOOLEAN": {
                MyValGen = new RandomBooleanGenerator();
                return new BooleanPropertyDefinition(prop.getPRDName(), PropertyType.BOOLEAN, MyValGen, true, currTick);

            }
            case "STRING": {
                MyValGen = new RandomStringGenerator();
                return new StringPropertyDefinition(prop.getPRDName(), PropertyType.STRING, MyValGen, true, currTick);
            }
            default:
                throw new InvalidTypeException("Invalid Type");
        }
    }
    //endregion
    //region Entities
    public Map<String, EntityDefinition> getEntitiesFromXML(PRDEntities entities) {
        Map<String, EntityDefinition> convertedEntities = new HashMap<>();

        for (PRDEntity entity : entities.getPRDEntity()) {
            EntityDefinition entityDefinition = new EntityDefinitionImpl(entity.getName(), entity.getPRDPopulation());
            for (PRDProperty prdProperty : entity.getPRDProperties().getPRDProperty())
                if(entityDefinition.getProps().containsKey(prdProperty.getPRDName()))
                    throw new PropertyAlreadyExsitException("Entity:" + entity.getName() + " already have a property name:" + prdProperty.getPRDName());
                else
                    entityDefinition.addPropertyDefinition(convertProperty(prdProperty));
            convertedEntities.put(entity.getName(), entityDefinition);
        }
        return convertedEntities;
    }
    public PropertyDefinition convertProperty(PRDProperty prop) {
        switch (prop.getType().toUpperCase()) {
            case "DECIMAL": {
                return createIntegerProp(prop);
            }
            case "FLOAT": {
                return createFloatProperty(prop);
            }
            case "BOOLEAN": {
                return createBooleanProperty(prop);
            }
            case "STRING": {
                return createStringProperty(prop);
            }
            default:
                throw new InvalidTypeException("Invalid Type");
        }
    }
    private PropertyDefinition createIntegerProp(PRDProperty prop) {
        ValueGenerator MyValGen;
        Boolean isRandomInit = true;
        Range myPropRange = null;
        if (prop.getPRDRange() != null) {
            myPropRange = new Range(prop.getPRDRange().getFrom(), prop.getPRDRange().getTo());
        }
        if (prop.getPRDValue().isRandomInitialize())
            if(myPropRange != null)
                MyValGen = new RandomIntegerGenerator((int) prop.getPRDRange().getFrom(), (int) prop.getPRDRange().getTo());
            else
                MyValGen = new RandomIntegerGenerator(null,null);
        else {
            if (Integer.parseInt(prop.getPRDValue().getInit()) > prop.getPRDRange().getFrom()
                    || Integer.parseInt(prop.getPRDValue().getInit()) < prop.getPRDRange().getTo()) {
                MyValGen = new FixedValueGenerator(Integer.parseInt(prop.getPRDValue().getInit()));
                isRandomInit = false;
            } else {
                throw new ValueOutOfBoundsException("Init value was out of bounds!");
            }
        }
        return new IntegerPropertyDefinition(prop.getPRDName(), PropertyType.DECIMAL, MyValGen, myPropRange, isRandomInit, currTick);
    }
    private PropertyDefinition createFloatProperty(PRDProperty prop) {
        ValueGenerator MyValGen;
        Boolean isRandomInit = true;
        Range myPropRange = null;
        if (prop.getPRDRange() != null) {
            myPropRange = new Range(prop.getPRDRange().getFrom(), prop.getPRDRange().getTo());
        }
        if (prop.getPRDValue().isRandomInitialize())
            if(myPropRange != null)
                MyValGen = new RandomDoubleGenerator(prop.getPRDRange().getFrom(), prop.getPRDRange().getTo());
            else
                MyValGen = new RandomDoubleGenerator(null,null);
        else {
            if (Double.parseDouble(prop.getPRDValue().getInit()) > prop.getPRDRange().getFrom()
                    || Integer.parseInt(prop.getPRDValue().getInit()) < prop.getPRDRange().getTo()) {
                MyValGen = new FixedValueGenerator(Double.parseDouble(prop.getPRDValue().getInit()));
                isRandomInit = false;
            } else {
                throw new ValueOutOfBoundsException("Init value was out of bounds!");
            }
        }
        return new FloatPropertyDefinition(prop.getPRDName(), PropertyType.FLOAT, MyValGen, myPropRange, isRandomInit, currTick);
    }
    private PropertyDefinition createBooleanProperty(PRDProperty prop) {
        ValueGenerator MyValGen;
        Boolean isRandomInit = true;
        if (prop.getPRDValue().isRandomInitialize())
            MyValGen = new RandomBooleanGenerator();
        else {
            if (validationEngine.isBoolean(prop.getPRDValue().getInit())) {
                MyValGen = new FixedValueGenerator(Boolean.parseBoolean(prop.getPRDValue().getInit()));
                isRandomInit = false;
            } else {
                throw new NotABooleanException("The value given was not of Boolean Type!");
            }
        }
        return new BooleanPropertyDefinition(prop.getPRDName(), PropertyType.BOOLEAN, MyValGen, isRandomInit, currTick);
    }
    private PropertyDefinition createStringProperty(PRDProperty prop) {
        ValueGenerator MyValGen;
        Boolean isRandomInit = true;
        if (prop.getPRDValue().isRandomInitialize())
            MyValGen = new RandomStringGenerator();
        else {
            MyValGen = new FixedValueGenerator(prop.getPRDValue().getInit());
            isRandomInit = false;
        }
        return new StringPropertyDefinition(prop.getPRDName(), PropertyType.STRING, MyValGen, isRandomInit, currTick);
    }

    //endregion
    //region Rules
    private Map<String, Rule> getRulesFromXML(PRDRules prdRules) {
        Map<String, Rule> convertedRules = new HashMap<>();
        for (PRDRule rule : prdRules.getPRDRule()) {
            convertedRules.put(rule.getName(), convertRule(rule));
        }
        return convertedRules;
    }
    private Rule convertRule(PRDRule rule) {
        ActivationImpl activation = convertActivationFromXML(rule);
        Rule newRule = new RuleImpl(rule.getName(), activation);
        for (PRDAction action : rule.getPRDActions().getPRDAction())
            newRule.addAction(convertActionFromXML(action));
        return newRule;
    }
    private ActivationImpl convertActivationFromXML(PRDRule rule) {
        ActivationImpl activation = new ActivationImpl();
        if (rule.getPRDActivation() == null) {
            return activation;
        }
        if (rule.getPRDActivation().getProbability() != null) {
            activation.setProbability(rule.getPRDActivation().getProbability());
        }
        if (rule.getPRDActivation().getTicks() != null) {
            activation.setTicks(rule.getPRDActivation().getTicks());
        }

        return activation;
    }
    private Action convertActionFromXML(PRDAction action) {
        if(validationEngine.checkEntityExist(action, world)) {
            switch (action.getType().toUpperCase()) {
                case "INCREASE":
                    if(validationEngine.checkIfEntityHasProp(action.getProperty(),action.getEntity(), world))
                    {
                        List<Expression> expressionList = getExpression(action.getEntity(),action.getProperty(), action.getBy());
                        if(validationEngine.checkArgsAreNumeric(expressionList,action.getEntity(),action.getType(), world))
                            return new IncreaseAction(world.getEntities().get(action.getEntity()),expressionList, action.getProperty(), currTick);
                    }
                    break;
                case "DECREASE":
                    if(validationEngine.checkIfEntityHasProp(action.getProperty(),action.getEntity(), world)) {
                        List<Expression> expressionList = getExpression(action.getEntity(),action.getProperty(), action.getBy());
                        if(validationEngine.checkArgsAreNumeric(expressionList,action.getEntity(),action.getType(), world))
                            return new DecreaseAction(world.getEntities().get(action.getEntity()), expressionList, action.getProperty(), currTick);
                    }
                    break;
                case "CALCULATION":
                    if(validationEngine.checkIfEntityHasProp(action.getResultProp(),action.getEntity(), world))
                        return calculateAccordingToMultiOrDivide(action);
                    break;
                case "CONDITION":
                    return ConditionActionBySingleOrByMulti(action);
                case "SET":
                    if(validationEngine.checkIfEntityHasProp(action.getProperty(),action.getEntity(), world))
                        return new SetAction(world.getEntities().get(action.getEntity()), getExpression(action.getEntity(), action.getProperty(), action.getValue()), action.getProperty(), currTick);
                    break;
                case "KILL":
                    return new KillAction(world.getEntities().get(action.getEntity()));
            }
        }
        return null;
    }

    private Action ConditionActionBySingleOrByMulti(PRDAction action) {

        if (action.getPRDCondition() != null) {
            if (action.getPRDCondition().getSingularity().equals("single")) {
                return SingleConditionCal(action);
            } else if (action.getPRDCondition().getSingularity().equals("multiple")) {
                return MultipleCondition(action);
            } else {
                throw new IllegalArgumentException("Not valid argument for condition");
            }
        } else {
            throw new IllegalArgumentException("Condition is null");
        }
    }
    private Action MultipleCondition(PRDAction action) {

        List<ConditionAction> conditionActionList = createConditionList(action.getPRDCondition());
        String logic = action.getPRDCondition().getLogical();
        String propName = action.getPRDCondition().getProperty();
        List<Action> thenActionList = createThenActionList(action);
        List<Action> elseActionList = createElseActionList(action);
        return new MultipleAction(world.getEntities().get(action.getEntity()),null, thenActionList, elseActionList,null,conditionActionList, logic, currTick);
    }
    private List<ConditionAction> createConditionList(PRDCondition condition) {
        List<ConditionAction> conditionActionList = new ArrayList<>();
        if (condition.getPRDCondition() != null) {
            for (PRDCondition prdCondition : condition.getPRDCondition()) {
                String valExpression = prdCondition.getValue();
                String propertyName = prdCondition.getProperty();
                if (prdCondition.getSingularity().equals("single")) {
                    if(validationEngine.checkIfEntityHasProp(propertyName,prdCondition.getEntity(), world)){
                        conditionActionList.add(new SingleAction(world.getEntities().get(prdCondition.getEntity()),
                            getExpression(prdCondition.getEntity(), propertyName, valExpression), null, null,
                            prdCondition.getProperty(), prdCondition.getOperator(), currTick));
                    }
                } else if (prdCondition.getSingularity().equals("multiple")) {
                    List<ConditionAction> multiCondList = createConditionList(prdCondition);
                    conditionActionList.add(new MultipleAction(world.getEntities().get(prdCondition.getEntity()),
                            null, null, null, condition.getProperty(), multiCondList, prdCondition.getLogical(), currTick));
                }
            }
        }
        return conditionActionList;
    }
    private Action SingleConditionCal(PRDAction action) {
        List<Action> thenActionList = createThenActionList(action);
        List<Action> elseActionList = createElseActionList(action);
        String propName = action.getPRDCondition().getProperty();
        String operator = action.getPRDCondition().getOperator();
        String value = action.getPRDCondition().getValue();
        if(validationEngine.checkIfEntityHasProp(propName,action.getEntity(), world))
            return new SingleAction(world.getEntities().get(action.getEntity()), getExpression(action.getEntity(), propName,value), thenActionList,elseActionList, propName, operator, currTick);
        return null;
    }
    private List<Action> createThenActionList(PRDAction action)
    {
        List<Action> actionListSingle = new ArrayList<>();
        if (action.getPRDThen().getPRDAction() != null) {
            for (PRDAction indexAction : action.getPRDThen().getPRDAction()) {
                actionListSingle.add(convertActionFromXML(indexAction));
            }
        } else {
            throw new IllegalArgumentException("Argument THEN was null");
        }
        return  actionListSingle;
    }
    private List<Action> createElseActionList(PRDAction action)
    {
        List<Action> actionListSingle = new ArrayList<>();
        if (action.getPRDElse() != null) {
            for (PRDAction indexAction : action.getPRDElse().getPRDAction()) {
                actionListSingle.add(convertActionFromXML(indexAction));
            }
        }
        return  actionListSingle;
    }

    private Action calculateAccordingToMultiOrDivide(PRDAction action) {
        List<Expression> myExpression = new ArrayList<>();
        if (action.getPRDDivide() != null) {
            myExpression.add(getExpression(action.getEntity(), action.getResultProp(), action.getPRDDivide().getArg1()).get(0));
            myExpression.add(getExpression(action.getEntity(), action.getResultProp(), action.getPRDDivide().getArg2()).get(0));
            if(validationEngine.checkArgsAreNumeric(myExpression,action.getEntity(),action.getType(), world))
                return new DivideAction(ActionTypeDTO.CALCULATION, world.getEntities().get(action.getEntity()), myExpression, action.getResultProp(), currTick);
        } else if (action.getPRDMultiply() != null) {
            myExpression.add(getExpression(action.getEntity(), action.getResultProp(), action.getPRDMultiply().getArg1()).get(0));
            myExpression.add(getExpression(action.getEntity(), action.getResultProp(), action.getPRDMultiply().getArg2()).get(0));
            if(validationEngine.checkArgsAreNumeric(myExpression,action.getEntity(),action.getType(), world))
                return new MultiplyAction(ActionTypeDTO.CALCULATION, world.getEntities().get(action.getEntity()), myExpression, action.getResultProp(), currTick);
        }

        throw new IllegalArgumentException("Input doesn't match the expected format");
    }

    List<Expression> getExpression(String entityName, String propName, String expressionVal) {
        List<Expression> myExpression = new ArrayList<>();
        if (expressionVal != null) {
            if (expressionVal.contains("environment")) {
                handleEnvironmentFunctionExpression(expressionVal, myExpression);
            } else if (expressionVal.contains("random")) {
                handleRandomFunctionExpression(expressionVal, myExpression);
            }
            else if (world.getEntities().get(entityName).getProps().containsKey(expressionVal)) {
                myExpression.add(new PropertyExpression(expressionVal));
            } else {
                myExpression.add(new GeneralExpression(world.getEntities().get(entityName).getProps().get(propName).getType(), expressionVal));
            }
        }
        return myExpression;
    }
    void handleRandomFunctionExpression(String ExpressionVal, List<Expression> myExpression) {

        int startIndex = ExpressionVal.indexOf('(') + 1;
        int endIndex = ExpressionVal.indexOf(')');

        if (startIndex != 0 && endIndex != -1) {
            String arg = ExpressionVal.substring(startIndex, endIndex);
            if (validationEngine.isNumeric(arg) || world.getEnvVariables().getEnvVariables().containsKey(arg)) {
                myExpression.add(new RandomFunction(arg));
            } else {
                throw new IllegalArgumentException("Invalid arg for function expression");
            }
        } else {
            throw new IllegalArgumentException("Input doesn't match the expected format");
        }
    }
    void handleEnvironmentFunctionExpression(String ExpressionVal, List<Expression> myExpression) {

        int startIndex = ExpressionVal.indexOf('(') + 1;
        int endIndex = ExpressionVal.indexOf(')');

        if (startIndex != 0 && endIndex != -1) {
            String arg = ExpressionVal.substring(startIndex, endIndex);
            if (validationEngine.isNumeric(arg) || world.getEnvVariables().getEnvVariables().containsKey(arg)) {
                myExpression.add(new EnvironmentFunction(arg));
            } else {
                throw new IllegalArgumentException("Invalid arg for function expression");
            }
        } else {
            throw new IllegalArgumentException("Input doesn't match the expected format");
        }
    }

    //endregion
    //region Termination
    private Termination getTerminationTermFromXML(PRDTermination prdTermination) {
        int ticksCount = -1;
        int secondCount = -1;
        for (Object obj : prdTermination.getPRDByTicksOrPRDBySecond()) {
            if (obj instanceof PRDByTicks) {
                PRDByTicks prdByTicks = (PRDByTicks) obj;
                ticksCount = prdByTicks.getCount();
            } else if (obj instanceof PRDBySecond) {
                PRDBySecond prdBySecond = (PRDBySecond) obj;
                secondCount = prdBySecond.getCount();
            }
        }
        if (ticksCount > 0 && secondCount > 0)
            return new TerminationImpl(ticksCount, secondCount);
        else
            throw new InvalidTerminationTermsException("Simulation Termination terms are invalid Please check them again!");
    }

    //endregion
    //endregion
    //region Command number 2
    public Map<String, EntityDefinitionDTO> getEntitiesDTO() {
        Map<String, EntityDefinitionDTO> entityDTO = new HashMap<>();
        Map<String, EntityPropDefinitionDTO> propsDTO = new HashMap<>();
        for (Map.Entry<String, EntityDefinition> entDef : world.getEntities().entrySet()) {
            String name = entDef.getKey();
            int pop = entDef.getValue().getPopulation();

            EntityPropDefinitionDTO propertyDefinitionDTO;
            for (Map.Entry<String, PropertyDefinition> propDef : entDef.getValue().getProps().entrySet()) {
                propertyDefinitionDTO = new EntityPropDefinitionDTO(propDef.getKey(), propDef.getValue().getType(), propDef.getValue().getRandomInit(), propDef.getValue().getRange());
                propsDTO.put(propertyDefinitionDTO.getName(), propertyDefinitionDTO);
            }
            entityDTO.put(name, new EntityDefinitionDTO(name, pop, propsDTO));
        }
        return entityDTO;
    }

    public Map<String, RulesDTO> getRulesDTO() {

        Map<String, RulesDTO> rulesDTO = new HashMap<>();
        for (Map.Entry<String, Rule> ruleEntry : world.getRules().entrySet()) {
            String ruleName = ruleEntry.getKey();
            Rule rule = ruleEntry.getValue();
            ActivationDTO activationDTO = new ActivationDTO(rule.getActivation().getProbability(), rule.getActivation().getTicks());
            List<ActionDTO> actionsDTOS = new ArrayList<>();
            for (Action action : rule.getActionsToPerform()) {
                actionsDTOS.add(new ActionDTO(action.getActionType()));
            }
            rulesDTO.put(ruleName, new RulesDTO(ruleName, activationDTO, actionsDTOS));
        }
        return rulesDTO;
    }

    public TerminitionDTO getTerminationDTO(){
        TerminitionDTO terminitionDTO = new TerminitionDTO(world.getTerminationTerm().getBySeconds(), world.getTerminationTerm().getByTicks());
        return terminitionDTO;
    }
    public SimulationInfoDTO getSimulationInfo(){
        SimulationInfoDTO simulationInfoDTO = new SimulationInfoDTO(getEntitiesDTO(),getRulesDTO(),getTerminationDTO());
        return simulationInfoDTO;
    }

    //endregion
    //region Command number 3
    @Override
    public EnvironmentDefinitionDTO getEnvDTO()
    {
        Map<String, EnvPropertyDefinitionDTO> envVariables = new HashMap<>();
        for(Map.Entry<String,PropertyDefinition> prop :world.getEnvVariables().getEnvVariables().entrySet())
        {
            envVariables.put(prop.getKey(),new EnvPropertyDefinitionDTO(prop.getKey(),
                    prop.getValue().getType(), prop.getValue().getRange()));
        }
        return new EnvironmentDefinitionDTO(envVariables);
    }
    //region validation

    @Override
    public void addEnvVarToActiveEnv(Object userValue, String name) {
        PropertyDefinition myPropDef = world.getEnvVariables().getEnvVariables().get(name);
        if(userValue != null)
            activeEnvironment.addPropertyInstance(new PropertyInstanceImpl(myPropDef,userValue));
        else
            activeEnvironment.addPropertyInstance(new PropertyInstanceImpl(myPropDef,myPropDef.generateValue()));
    }
    @Override
    public void initRandomEnvVars(String name)
    {
        if(!checkIfEnvIsInitialized(name))
        {
            PropertyDefinition myPropDef = world.getEnvVariables().getEnvVariables().get(name);
            activeEnvironment.addPropertyInstance(new PropertyInstanceImpl(myPropDef,myPropDef.generateValue()));
        }
    }
    public void clearActiveEnv()
    {
        if(activeEnvironment.getProperties().size() > 0)
        {
            activeEnvironment = new ActiveEnvironmentImpl();
        }
    }
    public boolean checkIfEnvIsInitialized(String name)
    {
        return activeEnvironment.getProperties().containsKey(name);
    }
    public ActiveEnvDTO ShowUserEnvVariables() {
        Map<String, EnvPropertyInstanceDTO> envPropertyInstanceDTO = new HashMap<>();
        activeEnvironment.getProperties().forEach((key, value) ->
                {
                    envPropertyInstanceDTO.put(key,new EnvPropertyInstanceDTO(
                            new EnvPropertyDefinitionDTO(value.getPropertyDefinition().getName(),value.getPropertyDefinition().getType(),
                                    value.getPropertyDefinition().getRange()),value.getValue()));
                });
        return new ActiveEnvDTO(envPropertyInstanceDTO);
    }

    @Override
    public String runSimulation()
    {
        //region HistogramCreation
        String Guid = UUID.randomUUID().toString();
        Instant simulationStart = Instant.now();
        //endregion
        createContext();
        int ticks = 0;
        boolean isTerminated = false;
        while (!isTerminated)
        {
            int finalTicks = ticks;
            currTick = ticks;
            context.getEntityManager().getInstances().forEach(entityInstance ->
            {
                context.setPrimaryInstacne(entityInstance.getId());
                world.getRules().forEach((name, rule) ->
                {
                    if (rule.getActivation().isActive(finalTicks)) {
                        rule.getActionsToPerform().forEach(action -> {
                            action.invoke(context);
                        });
                    }
                });
            });
            ticks++;
            activateKillAction();
            if(validationEngine.simulationEnded(ticks,simulationStart, world))
                isTerminated = true;
        }
        String endReason = getTerminationReason(ticks,simulationStart);
        createHistogram(Guid);
        return Guid+ "\n" + endReason;
    }
    //todo: ask noam if i can delete this arg: simulationStart
    private String getTerminationReason(int ticks, Instant simulationStart) {
        String ticksMsg = "The simulation has ended because " + ticks +" ticks has passed";
        String secondsMsg = "The simulation has ended because more then " + world.getTerminationTerm().getBySeconds() +" seconds has passed";
        if(world.getTerminationTerm().getByTicks() == ticks)
            return ticksMsg;
        else
            return secondsMsg;

    }

    private void activateKillAction() {
        context.getEntityManager().getKillList().forEach(idToKill ->
        {
            context.getEntityManager().executeKill(idToKill);
        });
        context.getEntityManager().clearKillList();
    }

    private void createContext() {
        EntityInstanceManager entityInstanceManager = new EntityInstanceManagerImpl();
        EntityInstance primaryEntityInstance = null;
        world.getEntities().forEach((key,value)->
                {
                    for(int i = 0;i < value.getPopulation();i++)
                    {
                        entityInstanceManager.create(value);
                    }
                });
        primaryEntityInstance = entityInstanceManager.getInstances().get(0);
        primaryEntStartPop = primaryEntityInstance.getEntityDef().getPopulation();
        context = new ContextImpl(primaryEntityInstance,entityInstanceManager,activeEnvironment);

    }
    //endregion
    //endregion
    //region Command number 4

    void createHistogram(String guid){
        String histogramDate = createHistogramDate();
        Map<Integer, EntityInstance> instanceMap = new HashMap<>();
        context.getEntityManager().getInstances().forEach(instance->{
            instanceMap.put(instance.getId(), instance);
        });
        Histogram histogram = new HistogramImpl(guid,histogramDate,instanceMap,primaryEntStartPop,context.getEntityManager().getCurrPopulation());
        histogramMap.put(guid,histogram);
    }
    String createHistogramDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy | HH.mm.ss");
        Date currDate = new Date();
        return sdf.format(currDate);

    }
    public HistogramByPropertyEntitiesDTO setHistogramPerProperty(String guid, String propName) {

        Map<Object, Integer> histogramByProperty = new HashMap<>();
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


    public HistogramByAmountEntitiesDTO createHistogramByAmountEntitiesDTO(String guid,String name){
        Histogram histogram = histogramMap.get(guid);
        return new HistogramByAmountEntitiesDTO(name,histogram.getPopBeforeSimulation(),histogram.getPopAfterSimulation());
    }
    public HistoryRunningSimulationDTO createHistoryRunningSimulationDTO() {
        Map<String, String> history = new HashMap<>();
        for (Map.Entry<String, Histogram> entry : histogramMap.entrySet()) {
            history.put(entry.getKey(), entry.getValue().getSimulationTime());

        }
        HistoryRunningSimulationDTO historyRunningSimulationDTO = new HistoryRunningSimulationDTO(history);
        return historyRunningSimulationDTO;
    }
    //endregion

}





