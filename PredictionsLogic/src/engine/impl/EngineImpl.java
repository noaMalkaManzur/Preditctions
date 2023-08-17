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
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class EngineImpl implements Engine {
    private WorldDefinition world = new WorldImpl();
    private Context context;
    Map<String, Histogram> histogramMap = new HashMap<>();
    private ActiveEnvironment activeEnvironment = new ActiveEnvironmentImpl();
    private int primaryEntStartPop;

    //region Command number 1
    @Override
    public boolean isFileExist(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        if (file.exists())
            return true;
        else {
            throw new FileNotFoundException("File was not found in our system");
        }
    }

    @Override
    public boolean isXMLFile(String fileName) throws BadFileSuffixException {
        if (fileName.endsWith(".xml"))
            return true;
        throw new BadFileSuffixException("The file was not an XML file!");
    }

    @Override
    public void loadXmlFiles(String fileName) {
        try {
            if (isFileExist(fileName)) {
                if (isXMLFile(fileName)) {
                    File file = new File(fileName);
                    JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
                    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                    PRDWorld prdWorld = (PRDWorld) jaxbUnmarshaller.unmarshal(file);
                    EnvVariablesManager envManager = new EnvVariableManagerImpl();
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
        prdEnvProperty.stream().map(this::convertEnvProp).forEach(envManager::addEnvironmentVariable);
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
                return new IntegerPropertyDefinition(prop.getPRDName(), PropertyType.DECIMAL, MyValGen, myPropRange, true);
            }
            case "FLOAT": {
                if (prop.getPRDRange() != null) {
                    myPropRange = new Range(prop.getPRDRange().getFrom(), prop.getPRDRange().getTo());
                    MyValGen = new RandomDoubleGenerator(prop.getPRDRange().getFrom(), prop.getPRDRange().getTo());
                }
                else
                    MyValGen = new RandomIntegerGenerator(null,null);
                return new FloatPropertyDefinition(prop.getPRDName(), PropertyType.FLOAT, MyValGen, myPropRange, true);
            }
            case "BOOLEAN": {
                MyValGen = new RandomBooleanGenerator();
                return new BooleanPropertyDefinition(prop.getPRDName(), PropertyType.BOOLEAN, MyValGen, true);

            }
            case "STRING": {
                MyValGen = new RandomStringGenerator();
                return new StringPropertyDefinition(prop.getPRDName(), PropertyType.STRING, MyValGen, true);
            }
            default:
                throw new InvalidTypeException("Invalid Type");
        }
    }

    //endregion
    //region Entities
    //ToDo:Ask if entity name and population needs Validation.
    public Map<String, EntityDefinition> getEntitiesFromXML(PRDEntities entities) {
        Map<String, EntityDefinition> convertedEntities = new HashMap<>();

        for (PRDEntity entity : entities.getPRDEntity()) {
            EntityDefinition entityDefinition = new EntityDefinitionImpl(entity.getName(), entity.getPRDPopulation());
            for (PRDProperty prdProperty : entity.getPRDProperties().getPRDProperty())
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
        return new IntegerPropertyDefinition(prop.getPRDName(), PropertyType.DECIMAL, MyValGen, myPropRange, isRandomInit);
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
        return new FloatPropertyDefinition(prop.getPRDName(), PropertyType.FLOAT, MyValGen, myPropRange, isRandomInit);
    }

    private PropertyDefinition createBooleanProperty(PRDProperty prop) {
        ValueGenerator MyValGen;
        Boolean isRandomInit = true;
        if (prop.getPRDValue().isRandomInitialize())
            MyValGen = new RandomBooleanGenerator();
        else {
            if (isBoolean(prop.getPRDValue().getInit())) {
                MyValGen = new FixedValueGenerator(Boolean.parseBoolean(prop.getPRDValue().getInit()));
                isRandomInit = false;
            } else {
                throw new NotABooleanException("The value given was not of Boolean Type!");
            }
        }
        return new BooleanPropertyDefinition(prop.getPRDName(), PropertyType.BOOLEAN, MyValGen, isRandomInit);
    }

    private PropertyDefinition createStringProperty(PRDProperty prop) {
        //ToDo: Check if any string validation is needed?
        ValueGenerator MyValGen;
        Boolean isRandomInit = true;
        if (prop.getPRDValue().isRandomInitialize())
            MyValGen = new RandomStringGenerator();
        else {
            MyValGen = new FixedValueGenerator(prop.getPRDValue().getInit());
            isRandomInit = false;
        }
        return new StringPropertyDefinition(prop.getPRDName(), PropertyType.STRING, MyValGen, isRandomInit);
    }

    public boolean isBoolean(String value) {
        return value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false");
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
        switch (action.getType().toUpperCase()) {
            case "INCREASE":
                return new IncreaseAction(ActionTypeDTO.INCREASE, world.getEntities().get(action.getEntity()), getExpression(action.getEntity(), action.getProperty(), action.getBy()), action.getProperty());
            case "DECREASE":
                return new DecreaseAction(ActionTypeDTO.DECREASE, world.getEntities().get(action.getEntity()), getExpression(action.getEntity(), action.getProperty(), action.getBy()), action.getProperty());
            case "CALCULATION":
                return calculateAccordingToMultiOrDivide(action);
            case "CONDITION":
                return ConditionActionBySingleOrByMulti(action);
            case "SET":
                return new SetAction(ActionTypeDTO.SET, world.getEntities().get(action.getEntity()), getExpression(action.getEntity(), action.getProperty(), action.getValue()), action.getProperty());
            case "KILL":
                return new KillAction(ActionTypeDTO.KILL, world.getEntities().get(action.getEntity()), null);

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

        List<ConditionAction> conditionActionList = createConditionList(action);
        String logic = action.getPRDCondition().getLogical();
        String propName = action.getPRDCondition().getProperty();
        List<Action> actionList = createActionListSingleCondition(action);
        return new MultipleAction(ActionTypeDTO.CONDITION, world.getEntities().get(action.getEntity()),null, actionList, null, logic, conditionActionList);
    }

    private List<ConditionAction> createConditionList(PRDAction action) {
        int i = 0;
        List<ConditionAction> conditionActionList = new ArrayList<>();
        //List<Action> actionList = createActionListSingleCondition(action);
        if (action.getPRDCondition().getPRDCondition().size() != 0) {
            for (PRDCondition prdCondition : action.getPRDCondition().getPRDCondition()) {
                if (prdCondition.getSingularity().equals("single")) {
                    String valExpression = action.getPRDCondition().getPRDCondition().get(i).getValue();
                    String propertyName = action.getPRDCondition().getPRDCondition().get(i).getProperty();
                    conditionActionList.add(new SingleAction(ActionTypeDTO.CONDITION, world.getEntities().get(action.getEntity()), getExpression(action.getEntity(), propertyName, valExpression), null, prdCondition.getProperty(), prdCondition.getOperator()));
                    i++;
                } else if (prdCondition.getSingularity().equals("multi")) {
                    //todo:check with noam if the index is ok for the multi or to keep other index?
                    String valExpression = action.getPRDCondition().getPRDCondition().get(i).getValue();
                    String propertyName = action.getPRDCondition().getPRDCondition().get(i).getProperty();
                    conditionActionList.add(new MultipleAction(ActionTypeDTO.CONDITION, world.getEntities().get(action.getEntity()), getExpression(action.getEntity(), propertyName, valExpression), null, action.getProperty(), prdCondition.getLogical(), conditionActionList));
                    i++;
                }
            }
        } else {
            throw new IllegalArgumentException("Condition list is empty!");
        }
        return conditionActionList;
    }

    private Action SingleConditionCal(PRDAction action) {
        List<Action> actionListSingle = createActionListSingleCondition(action);
        String propName = action.getPRDCondition().getProperty();
        String operator = action.getPRDCondition().getOperator();
        String value = action.getPRDCondition().getValue();
        return new SingleAction(ActionTypeDTO.CONDITION, world.getEntities().get(action.getEntity()), getExpression(action.getEntity(), propName,value), actionListSingle, propName, operator);
    }

    private List<Action> createActionListSingleCondition(PRDAction action) {
        List<Action> actionListSingle = new ArrayList<>();
        if (action.getPRDThen().getPRDAction() != null) {
            for (PRDAction indexAction : action.getPRDThen().getPRDAction()) {
                actionListSingle.add(convertActionFromXML(indexAction));
            }
        } else {
            throw new IllegalArgumentException("Argument THEN was null");
        }
        if (action.getPRDElse() != null) {
            for (PRDAction indexAction : action.getPRDElse().getPRDAction()) {
                actionListSingle.add(convertActionFromXML(indexAction));
            }
        }
        return actionListSingle;
    }

    private Action calculateAccordingToMultiOrDivide(PRDAction action) {
        List<Expression> myExpression = new ArrayList<>();
        if (action.getPRDDivide() != null) {
            myExpression.add(getExpression(action.getEntity(), action.getResultProp(), action.getPRDDivide().getArg1()).get(0));
            myExpression.add(getExpression(action.getEntity(), action.getResultProp(), action.getPRDDivide().getArg2()).get(0));
            return new DivideAction(ActionTypeDTO.CALCULATION, world.getEntities().get(action.getEntity()), myExpression, action.getResultProp());
        } else if (action.getPRDMultiply() != null) {
            myExpression.add(getExpression(action.getEntity(), action.getResultProp(), action.getPRDMultiply().getArg1()).get(0));
            myExpression.add(getExpression(action.getEntity(), action.getResultProp(), action.getPRDMultiply().getArg2()).get(0));
            return new MultiplyAction(ActionTypeDTO.CALCULATION, world.getEntities().get(action.getEntity()), myExpression, action.getResultProp());
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
                //todo:check about getting a string
            } else if (isNumeric(expressionVal) || isBoolean(expressionVal)) {
                myExpression.add(new GeneralExpression(expressionVal, world.getEntities().get(entityName).getProps().get(propName).getType()));
            } else if (world.getEntities().get(entityName).getProps().containsKey(expressionVal)) {
                myExpression.add(new PropertyExpression(expressionVal));
            } else {
                throw new InvalidByArgument("we do not support this kind of argument expression!");
            }
        }
        return myExpression;
    }

    void handleRandomFunctionExpression(String ExpressionVal, List<Expression> myExpression) {

        int startIndex = ExpressionVal.indexOf('(') + 1;
        int endIndex = ExpressionVal.indexOf(')');

        if (startIndex != 0 && endIndex != -1) {
            String arg = ExpressionVal.substring(startIndex, endIndex);
            if (isNumeric(arg) || world.getEnvVariables().getEnvVariables().containsKey(arg)) {
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
            if (isNumeric(arg) || world.getEnvVariables().getEnvVariables().containsKey(arg)) {
                myExpression.add(new EnvironmentFunction(arg));
            } else {
                throw new IllegalArgumentException("Invalid arg for function expression");
            }
        } else {
            throw new IllegalArgumentException("Input doesn't match the expected format");
        }
    }

    private boolean isNumeric(String numberStr) {
        if (numberStr == null || numberStr.isEmpty()) {
            return false;
        }
        boolean hasDecimalPoint = false; // For tracking decimal points in the input
        for (char c : numberStr.toCharArray()) {
            if (!Character.isDigit(c)) {
                if (c == '.' && !hasDecimalPoint) {
                    hasDecimalPoint = true;
                } else {
                    return false;
                }
            }
        }
        return true;
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
    public Map<String, EntityDefinitionDTO> getEntityDTO() {
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
        SimulationInfoDTO simulationInfoDTO = new SimulationInfoDTO(getEntityDTO(),getRulesDTO(),getTerminationDTO());
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
    public boolean isValidIntegerVar(String userInput, Range range) {
        try {
            int value = Integer.parseInt(userInput);
            if(range != null)
                return value >= range.getRangeFrom() && value <= range.getRangeTo();
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public boolean isValidDoubleVar(String userInput, Range range) {
        try {
            double value = Double.parseDouble(userInput);
            if(range != null)
                return value >= range.getRangeFrom() && value <= range.getRangeTo();
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public boolean isValidBooleanVar(String userInput) {
        return userInput.equalsIgnoreCase("true") || userInput.equalsIgnoreCase("false");
    }

    @Override
    public boolean isValidStringVar(String userInput) {
        return true;
    }

    @Override
    public void addEnvVarToActiveEnv(Object userValue, String name) {
        PropertyDefinition myPropDef = world.getEnvVariables().getEnvVariables().get(name);
        if(userValue != null)
            activeEnvironment.addPropertyInstance(new PropertyInstanceImpl(myPropDef,userValue));
        else
            activeEnvironment.addPropertyInstance(new PropertyInstanceImpl(myPropDef,myPropDef.generateValue()));

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
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy | HH.mm.ss");
        Date currDate = new Date();
        String HistogramDate = sdf.format(currDate);
        Instant simulationStart = Instant.now();
        //endregion
        createContext();
        int ticks = 0;
        boolean isTerminated = false;
        while (!isTerminated)
        {
            int finalTicks = ticks;
            for(int id = 0; id < primaryEntStartPop;id++) {
                context.setPrimaryInstacne(id);
                world.getRules().forEach((name, rule) ->
                {
                    if (rule.getActivation().isActive(finalTicks)) {
                        rule.getActionsToPerform().forEach(action -> {
                            action.invoke(context);
                        });
                    }
                });
            }
            ticks++;
            activateKillAction();
            System.out.println("#tick:"+ticks+ "    Curr Pop:" + context.getEntityManager().getCurrPopulation());
            if(simulationEnded(ticks,simulationStart))
                isTerminated = true;
        }
        createHistogram(Guid, HistogramDate);
        return Guid;
    }

    private void activateKillAction() {
        context.getEntityManager().getKillList().forEach(idToKill ->
        {
            context.getEntityManager().executeKill(idToKill);
        });
        context.getEntityManager().clearKillList();
    }

    private boolean simulationEnded(int ticks,Instant simulationStart) {
        long diff = Duration.between(simulationStart, Instant.now()).toMillis()/ 1000;
        return (world.getTerminationTerm().getByTicks() == ticks || diff >= world.getTerminationTerm().getBySeconds());
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
    public HistogramByAmountEntitiesDTO createHistogramByAmountEntitiesDTO(String guid){
        Histogram histogram = histogramMap.get(guid);
        HistogramByAmountEntitiesDTO histogramByAmountEntitiesDTO = new HistogramByAmountEntitiesDTO(histogram.getPopAfterSimulation(),histogram.getPopBeforeSimulation());
        return histogramByAmountEntitiesDTO;
    }
    public HistogramByPropertyEntitiesDTO createHistogramByProperty(String guid, String propName){
        Map<Object, Integer> histogramByproperty = histogramMap.get(guid).getHistogramByProperty();
        HistogramByPropertyEntitiesDTO histogramByPropertyEntitiesDTO = new HistogramByPropertyEntitiesDTO(histogramByproperty);
        return  histogramByPropertyEntitiesDTO;
    }

    void createHistogram(String guid, String date){
        Map<Integer, EntityInstance> instanceMap = new HashMap<>();
        context.getEntityManager().getInstances().forEach(instance->{
            instanceMap.put(instance.getId(), instance);
        });
        Histogram histogram = new HistogramImpl(guid,date,instanceMap,primaryEntStartPop,context.getEntityManager().getCurrPopulation());
        histogramMap.put(guid,histogram);
    }
    
    void setHistogramPerProperty(String guid, String propName) {

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
        histogram.setHistogramByProperty(histogramByProperty);
    }
}





