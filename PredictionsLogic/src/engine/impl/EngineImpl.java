package engine.impl;

import Defenitions.Actions.Calculation.CalculationDTO;
import Defenitions.Actions.Condition.impl.MultipleDTO;
import Defenitions.Actions.Condition.impl.SingleDTO;
import Defenitions.Actions.IncreaseDecrease.IncreaseDecreaseDTO;
import Defenitions.Actions.Kill.KillDTO;
import Defenitions.Actions.Proximity.ProximityDTO;
import Defenitions.Actions.Replace.ReplaceDTO;
import Defenitions.Actions.Set.SetDTO;
import Defenitions.Actions.api.ActionDTO;
import Defenitions.*;
import Enums.ActionTypeDTO;
import Enums.MathActionDTO;
import Enums.ModesDTO;
import Generated.*;
import Histogram.api.Histogram;
import Histogram.impl.HistogramImpl;
import Instance.ActiveEnvDTO;
import Instance.EnvPropertyInstanceDTO;
import action.api.Action;
import action.impl.*;
import action.impl.calculation.impl.DivideAction;
import action.impl.calculation.impl.MultiplyAction;
import action.impl.condition.api.ConditionAction;
import action.impl.condition.impl.MultipleAction;
import action.impl.condition.impl.SingleAction;
import action.impl.replace.impl.DerivedAction;
import action.impl.replace.impl.ScratchAction;
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
import definition.secondaryEntity.api.SecondaryEntityDefinition;
import definition.secondaryEntity.impl.SecondaryEntityDefinitionImpl;
import definition.value.generator.api.ValueGenerator;
import definition.value.generator.fixed.FixedValueGenerator;
import definition.value.generator.random.impl.bool.RandomBooleanGenerator;
import definition.value.generator.random.impl.numeric.RandomDoubleGenerator;
import definition.value.generator.random.impl.numeric.RandomIntegerGenerator;
import definition.value.generator.random.impl.string.RandomStringGenerator;
import definition.world.api.Termination;
import definition.world.api.WorldDefinition;
import definition.world.impl.Grid;
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
import expression.impl.*;
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
    private int rows;
    private int columns;
    private Boolean actionDTOFlag = false;
    RulesDTO rulesDTO;
    List<ActionDTO> actionDTOS;
    ActivationDTO activationDTO;
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
                    setEnvVariablesFromXML(envManager, prdWorld.getPRDEnvironment().getPRDEnvProperty());
                    world.setEnvVariables(envManager);
                    world.setGrid(getGridFromFile(prdWorld));
                    world.setEntities(getEntitiesFromXML(prdWorld.getPRDEntities()));
                    world.setRules(getRulesFromXML(prdWorld.getPRDRules()));
                    world.setTerminationTerm(getTerminationTermFromXML(prdWorld.getPRDTermination()));
                }
            }
        } catch (JAXBException | FileNotFoundException | BadFileSuffixException e) {
            throw new RuntimeException(e);
        }
    }
    private Grid getGridFromFile(PRDWorld prdWorld) {
        return new Grid(prdWorld.getPRDGrid().getRows(),prdWorld.getPRDGrid().getColumns());
    }

    //region Environment
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
                } else
                    MyValGen = new RandomIntegerGenerator(null, null);
                return new IntegerPropertyDefinition(prop.getPRDName(), PropertyType.DECIMAL, MyValGen, myPropRange, true);
            }
            case "FLOAT": {
                if (prop.getPRDRange() != null) {
                    myPropRange = new Range(prop.getPRDRange().getFrom(), prop.getPRDRange().getTo());
                    MyValGen = new RandomDoubleGenerator(prop.getPRDRange().getFrom(), prop.getPRDRange().getTo());
                } else
                    MyValGen = new RandomIntegerGenerator(null, null);
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
    public Map<String, EntityDefinition> getEntitiesFromXML(PRDEntities entities) {
        Map<String, EntityDefinition> convertedEntities = new HashMap<>();

        for (PRDEntity entity : entities.getPRDEntity()) {
            //todo: we get the population from the user
            EntityDefinition entityDefinition = new EntityDefinitionImpl(entity.getName(), 100);
            for (PRDProperty prdProperty : entity.getPRDProperties().getPRDProperty())
                if (entityDefinition.getProps().containsKey(prdProperty.getPRDName()))
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
            if (myPropRange != null)
                MyValGen = new RandomIntegerGenerator((int) prop.getPRDRange().getFrom(), (int) prop.getPRDRange().getTo());
            else
                MyValGen = new RandomIntegerGenerator(null, null);
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
            if (myPropRange != null)
                MyValGen = new RandomDoubleGenerator(prop.getPRDRange().getFrom(), prop.getPRDRange().getTo());
            else
                MyValGen = new RandomDoubleGenerator(null, null);
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
            if (validationEngine.isBoolean(prop.getPRDValue().getInit())) {
                MyValGen = new FixedValueGenerator(Boolean.parseBoolean(prop.getPRDValue().getInit()));
                isRandomInit = false;
            } else {
                throw new NotABooleanException("The value given was not of Boolean Type!");
            }
        }
        return new BooleanPropertyDefinition(prop.getPRDName(), PropertyType.BOOLEAN, MyValGen, isRandomInit);
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
        return new StringPropertyDefinition(prop.getPRDName(), PropertyType.STRING, MyValGen, isRandomInit);
    }

    //endregion
    //region Rules
    private Map<String, Rule> getRulesFromXML(PRDRules prdRules) {
        Map<String, Rule> convertedRules = new HashMap<>();
        List<RuleDTO> ruleDTO = new ArrayList<>();
        for (PRDRule rule : prdRules.getPRDRule()) {
            actionDTOS = new ArrayList<>();
            convertedRules.put(rule.getName(), convertRule(rule));
            ruleDTO.add(new RuleDTO(rule.getName(),activationDTO,actionDTOS));
        }
        rulesDTO = new RulesDTO(ruleDTO);
        return convertedRules;
    }

    private Rule convertRule(PRDRule rule) {
        ActivationImpl activation = convertActivationFromXML(rule);
        activationDTO = new ActivationDTO(activation.getProbability(),activation.getTicks());
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
        //Create ActivationDTO


        return activation;
    }

    private Action convertActionFromXML(PRDAction action) {
        SecondaryEntityDefinition secondaryEntity = null;
        String secondaryEntityDTO = null;
            switch (action.getType().toUpperCase()) {
                case "INCREASE":
                    if (validationEngine.checkEntityExist(action.getEntity(), world)) {
                        if (validationEngine.checkIfEntityHasProp(action.getProperty(), action.getEntity(), world)) {
                            List<Expression> expressionList = getExpression(action.getEntity(), action.getProperty(), action.getBy());
                            if (validationEngine.checkArgsAreNumeric(expressionList, action.getEntity(), action.getType(), world)){
                                if (action.getPRDSecondaryEntity() != null) {
                                    if(validationEngine.checkEntityExist(action.getPRDSecondaryEntity().getEntity(), world)) {
                                        ConditionAction conditionAction = convertConditionActionSecondEntity(action);
                                        secondaryEntity = new SecondaryEntityDefinitionImpl(action.getPRDSecondaryEntity().getEntity(), action.getPRDSecondaryEntity().getPRDSelection().getCount(), conditionAction);
                                        secondaryEntityDTO = action.getPRDSecondaryEntity().getEntity();
                                    }
                                }
                                if(!actionDTOFlag)
                                    actionDTOS.add(new IncreaseDecreaseDTO(ActionTypeDTO.INCREASE,action.getEntity(),secondaryEntityDTO,action.getProperty(), action.getBy()));
                                return new IncreaseAction(world.getEntities().get(action.getEntity()), expressionList, action.getProperty(), secondaryEntity);
                            }
                        }
                    }
                    break;
                case "DECREASE":
                    if (validationEngine.checkEntityExist(action.getEntity(), world)) {
                        if (validationEngine.checkIfEntityHasProp(action.getProperty(), action.getEntity(), world)) {
                            List<Expression> expressionList = getExpression(action.getEntity(), action.getProperty(), action.getBy());
                            if (validationEngine.checkArgsAreNumeric(expressionList, action.getEntity(), action.getType(), world)) {
                                if (action.getPRDSecondaryEntity() != null) {
                                    if(validationEngine.checkEntityExist(action.getPRDSecondaryEntity().getEntity(), world)) {
                                        ConditionAction conditionAction = convertConditionActionSecondEntity(action);
                                        secondaryEntity = new SecondaryEntityDefinitionImpl(action.getPRDSecondaryEntity().getEntity(), action.getPRDSecondaryEntity().getPRDSelection().getCount(), conditionAction);
                                        secondaryEntityDTO = action.getPRDSecondaryEntity().getEntity();
                                    }
                                }
                                if(!actionDTOFlag)
                                    actionDTOS.add(new IncreaseDecreaseDTO(ActionTypeDTO.DECREASE,action.getEntity(),secondaryEntityDTO,action.getProperty(), action.getBy()));
                                return new DecreaseAction(world.getEntities().get(action.getEntity()), expressionList, action.getProperty(), secondaryEntity);
                            }
                        }
                    }
                    break;
                case "CALCULATION":
                    if(validationEngine.checkEntityExist(action.getEntity(), world)){
                        if (validationEngine.checkIfEntityHasProp(action.getResultProp(), action.getEntity(), world))
                            return calculateAccordingToMultiOrDivide(action);
                    }
                    break;
                case "CONDITION":
                    if(validationEngine.checkEntityExist(action.getEntity(), world))
                        return ConditionActionBySingleOrByMulti(action);
                case "SET":
                    if(validationEngine.checkEntityExist(action.getEntity(), world)) {
                        if (validationEngine.checkIfEntityHasProp(action.getProperty(), action.getEntity(), world))
                            if (action.getPRDSecondaryEntity() != null) {
                                if(validationEngine.checkEntityExist(action.getPRDSecondaryEntity().getEntity(), world)) {
                                    ConditionAction conditionAction = convertConditionActionSecondEntity(action);
                                    secondaryEntity = new SecondaryEntityDefinitionImpl(action.getPRDSecondaryEntity().getEntity(), action.getPRDSecondaryEntity().getPRDSelection().getCount(), conditionAction);
                                    secondaryEntityDTO = action.getPRDSecondaryEntity().getEntity();
                                }
                            }
                        if(!actionDTOFlag)
                            actionDTOS.add(new SetDTO(ActionTypeDTO.SET,action.getEntity(),secondaryEntityDTO,action.getProperty(),action.getValue()));
                        return new SetAction(world.getEntities().get(action.getEntity()), getExpression(action.getEntity(), action.getProperty(), action.getValue()), action.getProperty(), secondaryEntity);
                    }
                    break;
                case "KILL":
                    if(validationEngine.checkEntityExist(action.getEntity(), world)) {
                        if (action.getPRDSecondaryEntity() != null) {
                            if(validationEngine.checkEntityExist(action.getPRDSecondaryEntity().getEntity(), world)) {
                                ConditionAction conditionAction = convertConditionActionSecondEntity(action);
                                secondaryEntity = new SecondaryEntityDefinitionImpl(action.getPRDSecondaryEntity().getEntity(), action.getPRDSecondaryEntity().getPRDSelection().getCount(), conditionAction);
                                secondaryEntityDTO = action.getPRDSecondaryEntity().getEntity();
                            }
                        }
                        if(!actionDTOFlag)
                            actionDTOS.add(new KillDTO(ActionTypeDTO.KILL,action.getEntity(),secondaryEntityDTO));
                        return new KillAction(world.getEntities().get(action.getEntity()),secondaryEntity);
                    }
                case "PROXIMITY":
                    if(validationEngine.checkEntityExist(action.getPRDBetween().getSourceEntity(), world) && validationEngine.checkEntityExist(action.getPRDBetween().getTargetEntity(), world))
                        return proximitiyAction(action);
                case "REPLACE":
                    if(validationEngine.checkEntityExist(action.getCreate(), world) && validationEngine.checkEntityExist(action.getKill(), world) )
                        return replaceAction(action);


        }
        return null;
    }

    private ConditionAction convertConditionActionSecondEntity(PRDAction action) {
        PRDCondition prdCondition = action.getPRDSecondaryEntity().getPRDSelection().getPRDCondition();
        String singularity = prdCondition.getSingularity();
        List<ConditionAction> conditionActionList = createConditionList(action.getPRDSecondaryEntity().getPRDSelection().getPRDCondition(), action);

        if(singularity.toLowerCase().equals("single")) {
             return new SingleAction(world.getEntities().get(prdCondition.getEntity()), getExpression(prdCondition.getEntity(),
                    prdCondition.getProperty(), prdCondition.getValue()), null, null, prdCondition.getProperty(), prdCondition.getOperator(), null);
        }
        else if(singularity.toLowerCase().equals("multiple")){
            return new MultipleAction(world.getEntities().get(prdCondition.getEntity()), getExpression(prdCondition.getEntity(),
                    prdCondition.getProperty(), prdCondition.getValue()), null, null, prdCondition.getProperty(), conditionActionList, prdCondition.getLogical(), null);
        }
        return null;
    }

    private Action replaceAction(PRDAction action) {
        SecondaryEntityDefinition secondaryEntity = null;
        String secondaryEntityDTO = null;
        if (action.getMode() != null && action.getKill() != null && action.getCreate() != null) {
            String entityNameToKill = action.getKill();
            String entityNameToCreate = action.getCreate();
            if (action.getMode().equals("scratch")) {
                if (action.getPRDSecondaryEntity() != null) {
                    if(validationEngine.checkEntityExist(action.getPRDSecondaryEntity().getEntity(), world)) {
                        ConditionAction conditionAction = convertConditionActionSecondEntity(action);
                        secondaryEntity = new SecondaryEntityDefinitionImpl(action.getPRDSecondaryEntity().getEntity(), action.getPRDSecondaryEntity().getPRDSelection().getCount(), conditionAction);
                        secondaryEntityDTO = action.getPRDSecondaryEntity().getEntity();
                    }
                }
                if(!actionDTOFlag)
                    actionDTOS.add(new ReplaceDTO(ActionTypeDTO.REPLACE,action.getEntity(),secondaryEntityDTO,action.getKill(),action.getCreate(), ModesDTO.SCRATCH));
                return new ScratchAction(null, null,entityNameToKill,entityNameToCreate, secondaryEntity);
            }
            else if (action.getMode().equals("derived")) {
                if (action.getPRDSecondaryEntity() != null) {
                    if(validationEngine.checkEntityExist(action.getPRDSecondaryEntity().getEntity(), world)) {
                        ConditionAction conditionAction = convertConditionActionSecondEntity(action);
                        secondaryEntity = new SecondaryEntityDefinitionImpl(action.getPRDSecondaryEntity().getEntity(), action.getPRDSecondaryEntity().getPRDSelection().getCount(), conditionAction);
                        secondaryEntityDTO = action.getPRDSecondaryEntity().getEntity();
                    }
                }
                if(!actionDTOFlag)
                    actionDTOS.add(new ReplaceDTO(ActionTypeDTO.REPLACE,action.getEntity(),secondaryEntityDTO,action.getKill(),action.getCreate(), ModesDTO.DERIVED));
                return new DerivedAction(null,null, entityNameToKill, entityNameToCreate, secondaryEntity);
            } else {
                throw new IllegalArgumentException("Not valid argument for condition");
            }
        } else {
            throw new IllegalArgumentException("replace arg was null!");
        }
    }

    private Action proximitiyAction(PRDAction action) {
        SecondaryEntityDefinition secondaryEntity = null;
        String secondaryEntityDTO = null;
        List<Action> proximityActionList = proximitiyActionList(action);
        EntityDefinition entityDefinition = world.getEntities().get(action.getPRDBetween().getSourceEntity());
        //todo: check if the value of in a numeric
        List<Expression> expressionList  = getExpression(action.getPRDBetween().getSourceEntity(),action.getPRDBetween().getTargetEntity(), action.getPRDEnvDepth().getOf());

        if (action.getPRDSecondaryEntity() != null) {
            if(validationEngine.checkEntityExist(action.getPRDSecondaryEntity().getEntity(), world)) {
                ConditionAction conditionAction = convertConditionActionSecondEntity(action);
                secondaryEntity = new SecondaryEntityDefinitionImpl(action.getPRDSecondaryEntity().getEntity(), action.getPRDSecondaryEntity().getPRDSelection().getCount(), conditionAction);
                secondaryEntityDTO = action.getPRDSecondaryEntity().getEntity();
            }
        }
        //ToDO: w8 for answer from behema
        if(!actionDTOFlag)
            actionDTOS.add(new ProximityDTO(ActionTypeDTO.PROXIMITY,action.getPRDBetween().getSourceEntity(),
                secondaryEntityDTO,action.getPRDBetween().getSourceEntity(),action.getPRDBetween().getTargetEntity()
                ,action.getPRDEnvDepth().getOf(),proximityActionList.size()));
        return new ProximityAction(entityDefinition, expressionList,proximityActionList, secondaryEntity);
    }

    private List<Action> proximitiyActionList(PRDAction action) {
        List<Action> proximityActionList = new ArrayList<>();
        actionDTOFlag = true;
        if (action.getPRDActions().getPRDAction() != null) {
            for (PRDAction indexAction : action.getPRDActions().getPRDAction()) {
                proximityActionList.add(convertActionFromXML(indexAction));
            }
        } else {
            throw new IllegalArgumentException("Argument Action was null");
        }
        actionDTOFlag = false;
        return proximityActionList;

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
        SecondaryEntityDefinition secondaryEntity = null;
        String secondaryEntityDTO = null;
        List<ConditionAction> conditionActionList = createConditionList(action.getPRDCondition(), action);
        String logic = action.getPRDCondition().getLogical();
        //todo: check with noam if need this propname
        //String propName = action.getPRDCondition().getProperty();
        List<Action> thenActionList = createThenActionList(action);
        List<Action> elseActionList = createElseActionList(action);
        if (action.getPRDSecondaryEntity() != null) {
            if(validationEngine.checkEntityExist(action.getPRDSecondaryEntity().getEntity(), world)) {
                ConditionAction conditionAction = convertConditionActionSecondEntity(action);
                secondaryEntity = new SecondaryEntityDefinitionImpl(action.getPRDSecondaryEntity().getEntity(), action.getPRDSecondaryEntity().getPRDSelection().getCount(), conditionAction);
                secondaryEntityDTO = action.getPRDSecondaryEntity().getEntity();
            }
        }
        if(!actionDTOFlag)
            actionDTOS.add(new MultipleDTO(ActionTypeDTO.CONDITION,action.getEntity(),secondaryEntityDTO,logic,thenActionList.size(),elseActionList.size(),conditionActionList.size()));
        return new MultipleAction(world.getEntities().get(action.getEntity()), null, thenActionList, elseActionList, null, conditionActionList, logic, secondaryEntity);
    }

    private List<ConditionAction> createConditionList(PRDCondition condition, PRDAction action) {
        List<ConditionAction> conditionActionList = new ArrayList<>();
        SecondaryEntityDefinition secondaryEntity = null;

        if (condition.getPRDCondition() != null) {
            for (PRDCondition prdCondition : condition.getPRDCondition()) {
                if (prdCondition.getSingularity().equals("single")) {
                    String valExpression = prdCondition.getValue();
                    String propertyName = prdCondition.getProperty();
                    //todo: change it too order number 6!!!!
                    propertyName = getPropertyNameByExpression(propertyName);
                    if (validationEngine.checkIfEntityHasProp(propertyName, prdCondition.getEntity(), world)) {
                        conditionActionList.add(new SingleAction(world.getEntities().get(prdCondition.getEntity()),
                                getExpression(prdCondition.getEntity(), propertyName, valExpression), null, null,
                                propertyName, prdCondition.getOperator(), secondaryEntity));
                    }
                } else if (prdCondition.getSingularity().equals("multiple")) {
                    List<ConditionAction> multiCondList = createConditionList(prdCondition, action);
                    conditionActionList.add(new MultipleAction(world.getEntities().get(prdCondition.getEntity()),
                            null, null, null, condition.getProperty(), multiCondList, prdCondition.getLogical(), secondaryEntity));
                }
            }
        }
        return conditionActionList;
    }

    private String getPropertyNameByExpression(String propertyName) {
        String propNameRes = propertyName;
        if(propertyName.toLowerCase().contains("ticks")) {
            propNameRes = getPropertyNameTicksAndEvaluate(propertyName);
        }
        else if(propertyName.toLowerCase().contains("environment")){
            propNameRes = getPropertyNameEnvironment(propertyName);
        }
        else if(propertyName.toLowerCase().contains("evaluate")){
            propNameRes = getPropertyNameTicksAndEvaluate(propertyName);
        }
        else if(propertyName.toLowerCase().contains("random") || propertyName.toLowerCase().contains("percent")){
            throw new IllegalArgumentException("for this expression the args are numeric!");
        }

        return propNameRes;
    }

    private String getPropertyNameEnvironment(String propertyName){
        int startIndex = propertyName.indexOf('(') + 1;
        int endIndex = propertyName.indexOf(')');

        if (startIndex != 0 && endIndex != -1) {
            return propertyName.substring(startIndex, endIndex);
        }
        else{
            throw new IllegalArgumentException("incorrect property name");
        }
    }

    private String getPropertyNameTicksAndEvaluate(String propertyNameToChange) {
        int startIndex = propertyNameToChange.indexOf('(') + 1;
        int endIndex = propertyNameToChange.indexOf(')');

        if (startIndex != -1 && endIndex != -1) {
            String components = propertyNameToChange.substring(startIndex, endIndex);
            String[] parts = components.split("\\.");

            if (parts.length == 2) {
                String propertyName = parts[1];
                return propertyName;
            } else {
                throw new IllegalArgumentException("Invalid input format");
            }
        }
        else {
            throw new IllegalArgumentException("Invalid input format");
        }
    }

    private Action SingleConditionCal(PRDAction action) {
        SecondaryEntityDefinition secondaryEntity = null;
        String secondaryEntityDTO = null;
        List<Action> thenActionList = createThenActionList(action);
        List<Action> elseActionList = createElseActionList(action);
        String propName = action.getPRDCondition().getProperty();
        String operator = action.getPRDCondition().getOperator();
        String value = action.getPRDCondition().getValue();

        if (validationEngine.checkIfEntityHasProp(propName, action.getEntity(), world)) {
            if (action.getPRDSecondaryEntity() != null) {
                if(validationEngine.checkEntityExist(action.getPRDSecondaryEntity().getEntity(), world)) {
                    ConditionAction conditionAction = convertConditionActionSecondEntity(action);
                    secondaryEntity = new SecondaryEntityDefinitionImpl(action.getPRDSecondaryEntity().getEntity(), action.getPRDSecondaryEntity().getPRDSelection().getCount(), conditionAction);
                    secondaryEntityDTO = action.getPRDSecondaryEntity().getEntity();
                }
            }
            if(!actionDTOFlag)
                actionDTOS.add(new SingleDTO(ActionTypeDTO.CONDITION,action.getEntity(),secondaryEntityDTO,propName,operator,value,thenActionList.size(),elseActionList.size()));
            return new SingleAction(world.getEntities().get(action.getEntity()), getExpression(action.getEntity(), propName, value), thenActionList, elseActionList, propName, operator, secondaryEntity);
        }
        //todo: handle this dont forget!!

        return null;
    }

    private List<Action> createThenActionList(PRDAction action) {
        List<Action> actionListSingle = new ArrayList<>();
        actionDTOFlag = true;
        if (action.getPRDThen().getPRDAction() != null) {
            for (PRDAction indexAction : action.getPRDThen().getPRDAction()) {
                actionListSingle.add(convertActionFromXML(indexAction));
            }
        } else {
            throw new IllegalArgumentException("Argument THEN was null");
        }
        actionDTOFlag = false;
        return actionListSingle;
    }

    private List<Action> createElseActionList(PRDAction action) {
        List<Action> actionListSingle = new ArrayList<>();
        actionDTOFlag = true;
        if (action.getPRDElse() != null) {
            for (PRDAction indexAction : action.getPRDElse().getPRDAction()) {
                actionListSingle.add(convertActionFromXML(indexAction));
            }
        }
        actionDTOFlag = false;
        return actionListSingle;
    }

    private Action calculateAccordingToMultiOrDivide(PRDAction action) {
        SecondaryEntityDefinition secondaryEntity = null;
        String secondaryEntityDTO = null;
        List<Expression> myExpression = new ArrayList<>();

        if (action.getPRDDivide() != null) {
            myExpression.add(getExpression(action.getEntity(), action.getResultProp(), action.getPRDDivide().getArg1()).get(0));
            myExpression.add(getExpression(action.getEntity(), action.getResultProp(), action.getPRDDivide().getArg2()).get(0));
            if (validationEngine.checkArgsAreNumeric(myExpression, action.getEntity(), action.getType(), world)) {
                if (action.getPRDSecondaryEntity() != null) {
                    if(validationEngine.checkEntityExist(action.getPRDSecondaryEntity().getEntity(), world)) {
                        ConditionAction conditionAction = convertConditionActionSecondEntity(action);
                        secondaryEntity = new SecondaryEntityDefinitionImpl(action.getPRDSecondaryEntity().getEntity(), action.getPRDSecondaryEntity().getPRDSelection().getCount(), conditionAction);                        secondaryEntityDTO = action.getPRDSecondaryEntity().getEntity();
                    }
                }
                if(!actionDTOFlag)
                    actionDTOS.add(new CalculationDTO(ActionTypeDTO.CALCULATION,action.getEntity(),secondaryEntityDTO,
                        action.getResultProp(),action.getPRDDivide().getArg1(),action.getPRDDivide().getArg2(), MathActionDTO.DIVIDE));
                return new DivideAction(ActionTypeDTO.CALCULATION, world.getEntities().get(action.getEntity()), myExpression, action.getResultProp(),secondaryEntity);
            }
        } else if (action.getPRDMultiply() != null) {
            myExpression.add(getExpression(action.getEntity(), action.getResultProp(), action.getPRDMultiply().getArg1()).get(0));
            myExpression.add(getExpression(action.getEntity(), action.getResultProp(), action.getPRDMultiply().getArg2()).get(0));
            if (validationEngine.checkArgsAreNumeric(myExpression, action.getEntity(), action.getType(), world)) {
                if (action.getPRDSecondaryEntity() != null) {
                    if(validationEngine.checkEntityExist(action.getPRDSecondaryEntity().getEntity(), world)) {
                        ConditionAction conditionAction = convertConditionActionSecondEntity(action);
                        secondaryEntity = new SecondaryEntityDefinitionImpl(action.getPRDSecondaryEntity().getEntity(), action.getPRDSecondaryEntity().getPRDSelection().getCount(), conditionAction);                        secondaryEntityDTO = action.getPRDSecondaryEntity().getEntity();
                    }
                }
                if(!actionDTOFlag)
                    actionDTOS.add(new CalculationDTO(ActionTypeDTO.CALCULATION,action.getEntity(),secondaryEntityDTO,
                        action.getResultProp(),action.getPRDMultiply().getArg1(),action.getPRDMultiply().getArg2(), MathActionDTO.MULTIPLY));
                return new MultiplyAction(ActionTypeDTO.CALCULATION, world.getEntities().get(action.getEntity()), myExpression, action.getResultProp(), secondaryEntity);
            }
        }

        throw new IllegalArgumentException("Input doesn't match the expected format");
    }

    List<Expression> getExpression(String entityName, String propName, String expressionVal) {
        List<Expression> myExpression = new ArrayList<>();
        if (expressionVal != null) {
            if (expressionVal.toLowerCase().contains("percent")) {
                handlePercentFunctionExpression(expressionVal, myExpression);
            } else if (expressionVal.toLowerCase().contains("evaluate")) {
                handleEvaluateExpression(expressionVal, myExpression);
            } else if (expressionVal.toLowerCase().contains("environment")) {
                handleEnvironmentFunctionExpression(expressionVal, myExpression);
            } else if (expressionVal.toLowerCase().contains("random")) {
                handleRandomFunctionExpression(expressionVal, myExpression);
            }else if (expressionVal.toLowerCase().contains("ticks")) {
                handleTicksFunctionExpression(expressionVal, myExpression);
            } else if (world.getEntities().get(entityName).getProps().containsKey(expressionVal)) {
                myExpression.add(new PropertyExpression(expressionVal));
            }
            //todo: this is for the proximity action that we have to get a float number only
            else {
                if (world.getEntities().containsKey(propName)) {
                    myExpression.add(new GeneralExpression(PropertyType.FLOAT, expressionVal));
                }
                else{
                    myExpression.add(new GeneralExpression(world.getEntities().get(entityName).getProps().get(propName).getType(), expressionVal));
                }
            }

        }
        return myExpression;
    }

    private void handleTicksFunctionExpression(String expressionVal, List<Expression> myExpression) {
        int startIndex = expressionVal.indexOf('(') + 1;
        int endIndex = expressionVal.indexOf(')');

        if (startIndex != -1 && endIndex != -1) {
            String components = expressionVal.substring(startIndex, endIndex);
            String[] parts = components.split("\\.");

            if (parts.length == 2) {
                String propertyName = parts[1];
                myExpression.add(new TickFunction(propertyName));
            } else {
                throw new IllegalArgumentException("Invalid input format");
            }
        }
        else {
            throw new IllegalArgumentException("Invalid input format");
        }
    }

    private void handleEvaluateExpression(String expressionVal, List<Expression> myExpression) {
        int startIndex = expressionVal.indexOf('(') + 1;
        int endIndex = expressionVal.indexOf(')');

        if (startIndex != -1 && endIndex != -1) {
            String components = expressionVal.substring(startIndex, endIndex);
            String[] args = components.split("\\.");

            if (args.length == 2) {
                if (validationEngine.checkIfEntityHasProp(args[1], args[0], world)) {
                    myExpression.add(new EvaluateExpression(args[0],args[1]));
                }
            } else {
                throw new IllegalArgumentException("There are not enough args for evaluate expression");
            }
        }
    }

    private void handlePercentFunctionExpression(String expressionVal, List<Expression> myExpression) {
        int openingIndex = expressionVal.indexOf('(');
        int closingIndex = expressionVal.lastIndexOf(')');
        List<String> args = new ArrayList<>();

        if (openingIndex != -1 && closingIndex != -1 && openingIndex < closingIndex) {
            String argsString = expressionVal.substring(openingIndex + 1, closingIndex);
            String[] arguments = argsString.split(",", 2);
            args.addAll(Arrays.asList(arguments));
        }
        Expression expression1 = getExpression(null, null, args.get(0)).get(0);
        Expression expression2 = getExpression(null, null, args.get(1)).get(0);
        myExpression.add(new PercentExpression(expression1,expression2));
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
        //return new TerminationImpl(120, 300,);
        Integer ticksCount = null;
        Integer secondCount = null;
        for (Object obj : prdTermination.getPRDBySecondOrPRDByTicks()) {
            if (obj instanceof PRDByTicks) {
                PRDByTicks prdByTicks = (PRDByTicks) obj;
                ticksCount = prdByTicks.getCount();
            } else if (obj instanceof PRDBySecond) {
                PRDBySecond prdBySecond = (PRDBySecond) obj;
                secondCount = prdBySecond.getCount();
            }
        }
        if (ticksCount != null && secondCount != null)
            return new TerminationImpl(ticksCount, secondCount,null);
        else if(prdTermination.getPRDByUser() != null)
            return new TerminationImpl(null, null,true);
        else
            throw new InvalidTerminationTermsException("Simulation Termination terms are invalid Please check them again!");
    }

    //endregion
    //endregion
    //region Command number 2
    public Map<String, EntityDefinitionDTO> getEntitiesDTO() {
        Map<String, EntityDefinitionDTO> entityDTO = new HashMap<>();
        for (Map.Entry<String, EntityDefinition> entDef : world.getEntities().entrySet()) {
            Map<String, EntityPropDefinitionDTO> propsDTO = new HashMap<>();
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

    public RulesDTO getRulesDTO() {
        return rulesDTO;
    }
    public GridDTO getGridDTO()
    {
        return new GridDTO(world.getGrid().getRows(),world.getGrid().getCols());
    }

    public TerminitionDTO getTerminationDTO(){
        return new TerminitionDTO(world.getTerminationTerm().getBySeconds(), world.getTerminationTerm().getByTicks(),world.getTerminationTerm().getByUser());
    }
    public SimulationInfoDTO getSimulationInfo(){
//        SimulationInfoDTO simulationInfoDTO = new SimulationInfoDTO(getEntitiesDTO(),getRulesDTO(),getTerminationDTO());
//        return simulationInfoDTO;
        return null;
    }

    @Override
    public WorldDefinitionDTO getWorldDefinitionDTO() {
        return null;
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
        List<Action> activeAction = new ArrayList<>();
        List <EntityInstance> secondaryEntityInstance  = new ArrayList<>();
        while (!isTerminated)
        {
            int finalTicks = ticks;

            context.getEntityManager().getInstances().forEach(entityInstance ->
                    entityInstance.setCoordinate(world.getGrid().getNextMove(entityInstance)));

            world.getRules().forEach((name, rule) ->
            {
                if (rule.getActivation().isActive(finalTicks)) {
                    rule.getActionsToPerform().forEach(action -> {
                        activeAction.add(action);
                    });
                }
            });

            context.getEntityManager().getInstances().forEach(entityInstance -> {
                activeAction.forEach(action -> {
                    if (action.getContextEntity().getName().equals(entityInstance.getEntityDef().getName())) {
                        if(action.hasSecondaryEntity()){
                            secondaryEntityInstance.add(entityInstance);
                        }
                        else{
                            action.invoke(context,finalTicks);
                        }
                    }
                });
            });


            ticks++;
            context.setCurrTick(ticks);
            activateKillAction();
            if(validationEngine.simulationEnded(ticks,simulationStart, world))
                isTerminated = true;
        }
        String endReason = getTerminationReason(ticks,simulationStart);
        createHistogram(Guid);
        return Guid+ "\n" + endReason;
    }
    /*
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
            context.getEntityManager().getInstances().forEach(entityInstance ->
            {
                entityInstance.setCoordinate(world.getGrid().getRandomCoordinate(entityInstance));
                context.setPrimaryInstance(entityInstance.getId());
                world.getRules().forEach((name, rule) ->
                {
                    if (rule.getActivation().isActive(finalTicks)) {
                        rule.getActionsToPerform().forEach(action -> {
                            action.invoke(context, finalTicks);
                        });
                    }
                });
            });
            ticks++;
            context.setCurrTick(ticks);
            activateKillAction();
            if(validationEngine.simulationEnded(ticks,simulationStart, world))
                isTerminated = true;
        }
        String endReason = getTerminationReason(ticks,simulationStart);
        createHistogram(Guid);
        return Guid+ "\n" + endReason;
    }*/
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
                        entityInstanceManager.createEntityInstance(value);
                        entityInstanceManager.getInstances().get(i).setCoordinate(world.getGrid().getRandomCoordinateInit(entityInstanceManager.getInstances().get(i)));

                    }
                });
        primaryEntityInstance = entityInstanceManager.getInstances().get(0);
        primaryEntStartPop = primaryEntityInstance.getEntityDef().getPopulation();
        //todo: give the values to rows and columns
        context = new ContextImpl(primaryEntityInstance,entityInstanceManager,activeEnvironment, null, rows, columns);

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

