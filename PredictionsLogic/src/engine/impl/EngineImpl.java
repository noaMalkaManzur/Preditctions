package engine.impl;

import Defenitions.*;
import Enums.ActionTypeDTO;
import Generated.*;
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
import execution.instance.environment.api.ActiveEnvironment;
import execution.instance.environment.impl.ActiveEnvironmentImpl;
import execution.instance.property.PropertyInstanceImpl;
import expression.api.Expression;
import expression.impl.EnvironmentFunction;
import expression.impl.GeneralExpression;
import expression.impl.PropertyExpression;
import expression.impl.RandomFunction;
import rule.Rule;
import rule.RuleImpl;
import simulationInfo.SimulationInfoDTO;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EngineImpl implements Engine {
    private WorldDefinition world = new WorldImpl();
    private Context context;
    private ActiveEnvironment activeEnvironment = new ActiveEnvironmentImpl();

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
                }
                MyValGen = new RandomIntegerGenerator((int) prop.getPRDRange().getFrom(), (int) prop.getPRDRange().getTo());
                return new IntegerPropertyDefinition(prop.getPRDName(), PropertyType.DECIMAL, MyValGen, myPropRange, true);
            }
            case "FLOAT": {
                if (prop.getPRDRange() != null) {
                    myPropRange = new Range(prop.getPRDRange().getFrom(), prop.getPRDRange().getTo());
                }
                MyValGen = new RandomDoubleGenerator(prop.getPRDRange().getFrom(), prop.getPRDRange().getTo());
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
            MyValGen = new RandomIntegerGenerator((int) prop.getPRDRange().getFrom(), (int) prop.getPRDRange().getTo());
        else {
            if (Integer.parseInt(prop.getPRDValue().getInit()) > prop.getPRDRange().getFrom()
                    || Integer.parseInt(prop.getPRDValue().getInit()) < prop.getPRDRange().getTo()) {
                MyValGen = new FixedValueGenerator(prop.getPRDValue().getInit());
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
            MyValGen = new RandomDoubleGenerator(prop.getPRDRange().getFrom(), prop.getPRDRange().getTo());
        else {
            if (Double.parseDouble(prop.getPRDValue().getInit()) > prop.getPRDRange().getFrom()
                    || Integer.parseInt(prop.getPRDValue().getInit()) < prop.getPRDRange().getTo()) {
                MyValGen = new FixedValueGenerator(prop.getPRDValue().getInit());
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
                MyValGen = new FixedValueGenerator(prop.getPRDValue().getInit());
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
    //ToDo:We need to think how our expressions are created we have problems with Environemt,Property.(Maybe we should send Context to calculate Expression method)
    private Map<String, Rule> getRulesFromXML(PRDRules prdRules) {
        Map<String, Rule> convertedRules = new HashMap<>();
        for (PRDRule rule : prdRules.getPRDRule()) {
            convertedRules.put(rule.getName(), convertRule(rule));
        }
        return convertedRules;
    }

    private Rule convertRule(PRDRule rule) {
        //ToDo:See how to implement activation terms
        Rule newRule = new RuleImpl(rule.getName());
        for (PRDAction action : rule.getPRDActions().getPRDAction())
            newRule.addAction(convertActionFromXML(action));
        return newRule;
    }

    private Action convertActionFromXML(PRDAction action) {
        switch (action.getType().toUpperCase()) {
            case "INCREASE":
                return new IncreaseAction(ActionTypeDTO.INCREASE, world.getEntities().get(action.getEntity()), getExpressionBy(action), action.getProperty());
            case "DECREASE":
                return new DecreaseAction(ActionTypeDTO.DECREASE, world.getEntities().get(action.getEntity()), getExpressionBy(action), action.getProperty());
            case "CALCULATION":
                return calculateAccordingToMultiOrDivide(action);
            case "CONDITION":
                return ConditionActionBySingleOrByMulti(action);
            case "SET":
                return new SetAction(ActionTypeDTO.SET,world.getEntities().get(action.getEntity()) , getExpressionValue(action) , action.getProperty());
            case "KILL":
                return new KillAction(ActionTypeDTO.KILL,world.getEntities().get(action.getEntity()), null);

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
        return new MultipleAction(ActionTypeDTO.CONDITION, world.getEntities().get(action.getEntity()), getExpressionValue(action), null, propName, logic, conditionActionList);
    }

    private List<ConditionAction> createConditionList(PRDAction action) {
        List<ConditionAction> conditionActionList = new ArrayList<>();
        for (PRDCondition prdCondition : action.getPRDCondition().getPRDCondition()) {
            if (prdCondition.getSingularity().equals("single")) {
                conditionActionList.add(new SingleAction(ActionTypeDTO.CONDITION, world.getEntities().get(action.getEntity()), getExpressionValue(action), null, prdCondition.getProperty(), prdCondition.getOperator()));
            } else if (prdCondition.getSingularity().equals("multi")) {
                conditionActionList.add(new MultipleAction(ActionTypeDTO.CONDITION, world.getEntities().get(action.getEntity()), getExpressionValue(action), null, action.getProperty(), prdCondition.getLogical(), conditionActionList));
            }
        }
        return conditionActionList;
    }

    private Action SingleConditionCal(PRDAction action){
        List<Action> actionListSingle = createActionListSingleCondition(action);
        String propName= action.getPRDCondition().getProperty();
        String operator = action.getPRDCondition().getOperator();
        return new SingleAction(ActionTypeDTO.CONDITION,world.getEntities().get(action.getEntity()), getExpressionValue(action), actionListSingle, propName, operator);
    }

    private List<Action> createActionListSingleCondition(PRDAction action) {
        List<Action> actionListSingle = new ArrayList<>();
        if(action.getPRDThen().getPRDAction()!= null){
            for (PRDAction indexAction : action.getPRDThen().getPRDAction()) {
                actionListSingle.add(convertActionFromXML(indexAction));
            }
        }
        else{
            throw new IllegalArgumentException("Argument THEN was null");
        }
        if(action.getPRDElse() != null) {
            for (PRDAction indexAction : action.getPRDElse().getPRDAction()) {
                actionListSingle.add(convertActionFromXML(indexAction));
            }
        }
        return actionListSingle;
    }

    private Action calculateAccordingToMultiOrDivide(PRDAction action) {
        //todo: need to do getExpression by arg!!!!
        if(action.getPRDDivide() != null){
            return new DivideAction(ActionTypeDTO.CALCULATION, world.getEntities().get(action.getEntity()), getExpressionValue(action),action.getResultProp());
        }
        else if(action.getPRDMultiply() != null){
            return new MultiplyAction(ActionTypeDTO.CALCULATION, world.getEntities().get(action.getEntity()), getExpressionValue(action),action.getResultProp());
        }
         throw new IllegalArgumentException("Input doesn't match the expected format");
    }

    private List<Expression> getExpressionArg(PRDAction action) {
        List<Expression> myExpression = new ArrayList<>();
        if(action.getPRDMultiply()!= null){
            String arg1 = action.getPRDMultiply().getArg1();
            String arg2 = action.getPRDMultiply().getArg2();
            if(arg1 != null && arg2 != null){

                if (arg1.contains("environment") ||arg2.contains("environment")) {
                    //todo: change this method to get the value maybe
                    handleEnvironmentFunctionExpressionBy(action, myExpression);
                } else if (arg1.contains("random") || arg2.contains("random")) {
                    handleRandomFunctionExpressionBy(action,myExpression);
                } else if (isNumeric(arg1) || isNumeric(arg2) ||world.getEnvVariables().getEnvVariables().containsKey(arg1)|| world.getEnvVariables().getEnvVariables().containsKey(arg2) ) {
                    myExpression.add(new GeneralExpression(action.getBy(), world.getEntities().get(action.getEntity()).getProps().get(action.getProperty()).getType()));
                } else if (world.getEntities().values().contains(action.getBy())) {
                    myExpression.add(new PropertyExpression(action.getBy()));
                }
            }
            else{
                throw new IllegalArgumentException("args for multiply was null!");
            }
        }

        return myExpression;

    }
    private List<Expression> getExpressionValue(PRDAction action) {

        List<Expression> myExpression = new ArrayList<>();

        if(action.getPRDCondition().getPRDCondition().size() != 0)
        {
            for(PRDCondition prdCondition: action.getPRDCondition().getPRDCondition()) {
                if (prdCondition.getValue().contains("environment")) {
                    handleRandomEnvironmenExpressionValue(prdCondition, myExpression);
                } else if (prdCondition.getValue().contains("random")) {
                    handleRandomFunctionExpressionValue(prdCondition, myExpression);
                } else if (isNumeric(prdCondition.getValue())) {
                    myExpression.add(new GeneralExpression(prdCondition.getValue(), world.getEntities().get(prdCondition.getEntity()).getProps().get(prdCondition.getProperty()).getType()));
                } else if (world.getEntities().values().contains(action.getPRDCondition().getValue())) {
                    myExpression.add(new PropertyExpression(prdCondition.getValue()));
                } else {
                    throw new InvalidByArgument("we do not support this kind of argument expression!");
                }
            }

        }

        return myExpression;
    }
    List<Expression> getExpressionBy(PRDAction action){
        List<Expression> myExpression = new ArrayList<>();
        if (action.getBy() != null) {
            if (action.getBy().contains("environment")) {
                handleEnvironmentFunctionExpressionBy(action, myExpression);
            } else if (action.getBy().contains("random")) {
                handleRandomFunctionExpressionBy(action,myExpression);
            } else if (isNumeric(action.getBy())) {
                myExpression.add(new GeneralExpression(action.getBy(), world.getEntities().get(action.getEntity()).getProps().get(action.getProperty()).getType()));
            } else if (world.getEntities().values().contains(action.getBy())) {
                myExpression.add(new PropertyExpression(action.getBy()));
            }
            else
            {
                throw new InvalidByArgument("we do not support this kind of argument expression!");
            }
        }
        return myExpression;
    }

   void handleRandomFunctionExpressionBy(PRDAction action, List<Expression> myExpression){

       int startIndex = action.getBy().indexOf('(') + 1;
       int endIndex = action.getBy().indexOf(')');

       if (startIndex != 0 && endIndex != -1) {
           String arg = action.getBy().substring(startIndex, endIndex);
           if (isNumeric(arg) || world.getEnvVariables().getEnvVariables().containsKey(arg)) {
               myExpression.add(new RandomFunction(arg));
           } else {
               throw new IllegalArgumentException("Invalid arg for function expression");
           }
       } else {
           throw new IllegalArgumentException("Input doesn't match the expected format");
       }
   }
    void handleRandomEnvironmenExpressionValue(PRDCondition prdCondition, List<Expression> myExpression){

        int startIndex = prdCondition.getValue().indexOf('(') + 1;
        int endIndex = prdCondition.getValue().indexOf(')');

        if (startIndex != 0 && endIndex != -1) {
            String arg = prdCondition.getValue().substring(startIndex, endIndex);
            if (isNumeric(arg) || world.getEnvVariables().getEnvVariables().containsKey(arg)) {
                myExpression.add(new EnvironmentFunction(arg));
            } else {
                throw new IllegalArgumentException("Invalid arg for function Expression");
            }
        } else {
            throw new IllegalArgumentException("Input doesn't match the expected format");
        }
    }
    void handleRandomFunctionExpressionValue(PRDCondition prdCondition, List<Expression> myExpression){

        int startIndex = prdCondition.getValue().indexOf('(') + 1;
        int endIndex = prdCondition.getValue().indexOf(')');

        if (startIndex != 0 && endIndex != -1) {
            String arg = prdCondition.getValue().substring(startIndex, endIndex);
            if (isNumeric(arg) || world.getEnvVariables().getEnvVariables().containsKey(arg)) {
                myExpression.add(new RandomFunction(arg));
            } else {
                throw new IllegalArgumentException("Invalid arg for function Expression");
            }
        } else {
            throw new IllegalArgumentException("Input doesn't match the expected format");
        }
    }
    void handleEnvironmentFunctionExpressionBy(PRDAction action, List<Expression> myExpression){

        int startIndex = action.getBy().indexOf('(') + 1;
        int endIndex = action.getBy().indexOf(')');

        if (startIndex != 0 && endIndex != -1) {
            String arg = action.getBy().substring(startIndex, endIndex);
            if (isNumeric(arg) ||  world.getEnvVariables().getEnvVariables().containsKey(arg)) {
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
        for (char c : numberStr.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;

    }
    //endregion
    //region Termination
    private Termination getTerminationTermFromXML(PRDTermination prdTermination){
        int ticksCount =-1;
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
        if(ticksCount > 0 && secondCount > 0)
            return new TerminationImpl(ticksCount,secondCount);
        else
            throw new InvalidTerminationTermsException("Simulation Termination terms are invalid Please check them again!");
    }
    //endregion
    //endregion
    //region Command number 2
    public Map<String, EntityDefinitionDTO> getEntityDTO()
    {
        Map<String, EntityDefinitionDTO> entityDTO = new  HashMap<>();
        Map<String, EntityPropDefinitionDTO> propsDTO = new HashMap<>();
        for(Map.Entry<String,EntityDefinition> entDef : world.getEntities().entrySet())
        {
            String name = entDef.getKey();
            int pop = entDef.getValue().getPopulation();
            EntityPropDefinitionDTO propertyDefinitionDTO;
            for(Map.Entry<String,PropertyDefinition> propDef : entDef.getValue().getProps().entrySet())
            {
                propertyDefinitionDTO = new EntityPropDefinitionDTO(propDef.getKey(),propDef.getValue().getType(),propDef.getValue().getRandomInit(),propDef.getValue().getRange());
                propsDTO.put(propertyDefinitionDTO.getName(),propertyDefinitionDTO);
            }
            entityDTO.put(name,new EntityDefinitionDTO(name,pop,propsDTO));
        }
        return entityDTO;
    }
    
    public  Map<String, RulesDTO> getRulesDTO(){

        Map<String, RulesDTO> rulesDTO = new HashMap<>();
        List<ActionDTO> actionsDTOS = new ArrayList<>();
        for(Map.Entry<String, Rule> ruleDTO : world.getRules().entrySet()){
            String name = ruleDTO.getKey();
            ActivationDTO activationDTO = new ActivationDTO(ruleDTO.getValue().getActivation().getProbability(), ruleDTO.getValue().getActivation().getTicks());
          for(Action action: ruleDTO.getValue().getActionsToPerform()){
              actionsDTOS.add(new ActionDTO(action.getActionType()));
            }
          rulesDTO.put(name,new RulesDTO(name, activationDTO, actionsDTOS));
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

    @Override
    public void ShowUserEnvVariables() {

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
    //ToDo:Implement me!
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
    //endregion
    //endregion


}

