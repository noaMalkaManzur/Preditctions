package engine.impl;

import Defenitions.EntityDefinitionDTO;
import Defenitions.PropertyDefinitionDTO;
import Generated.*;
import action.api.Action;
import action.impl.IncreaseAction;
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
import expression.api.Expression;
import expression.impl.GeneralExpression;
import expression.impl.RandomFunction;
import rule.Rule;
import rule.RuleImpl;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EngineImpl implements Engine
{
    private WorldDefinition world = new WorldImpl();
    @Override
    public boolean isFileExist(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        if(file.exists())
            return true;
        else
        {
            throw new FileNotFoundException("File was not found in our system");
        }
    }
    @Override
    public boolean isXMLFile(String fileName) throws BadFileSuffixException {
        if(fileName.endsWith(".xml"))
            return true;
        throw new BadFileSuffixException("The file was not an XML file!");
    }

    @Override
    public void loadXmlFiles(String fileName)
    {
        try {
            if(isFileExist(fileName))
            {
                if(isXMLFile(fileName))
                {
                    File file = new File(fileName);
                    JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
                    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                    PRDWorld prdWorld = (PRDWorld) jaxbUnmarshaller.unmarshal(file);
                    EnvVariablesManager envManager = new EnvVariableManagerImpl();
                    setEnvVariablesFromXML(envManager,prdWorld.getPRDEvironment().getPRDEnvProperty());
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
        switch (prop.getType().toUpperCase())
        {
            case "DECIMAL":
            {
                if(prop.getPRDRange() != null)
                {
                    myPropRange = new Range(prop.getPRDRange().getFrom(),prop.getPRDRange().getTo());
                }
                MyValGen = new RandomIntegerGenerator((int) prop.getPRDRange().getFrom(), (int) prop.getPRDRange().getTo());
                return new IntegerPropertyDefinition(prop.getPRDName(), PropertyType.DECIMAL,MyValGen,myPropRange,true);
            }
            case "FLOAT":
            {
                if(prop.getPRDRange() != null)
                {
                    myPropRange = new Range(prop.getPRDRange().getFrom(),prop.getPRDRange().getTo());
                }
                MyValGen = new RandomDoubleGenerator(prop.getPRDRange().getFrom(),prop.getPRDRange().getTo());
                return new FloatPropertyDefinition(prop.getPRDName(), PropertyType.DECIMAL,MyValGen,myPropRange,true);
            }
            case "BOOLEAN":
            {
                MyValGen = new RandomBooleanGenerator();
                return new BooleanPropertyDefinition(prop.getPRDName(), PropertyType.DECIMAL,MyValGen,true);

            }
            case "STRING":
            {
                MyValGen = new RandomStringGenerator();
                return new StringPropertyDefinition(prop.getPRDName(), PropertyType.DECIMAL,MyValGen,true);
            }
            default:
                throw new InvalidTypeException("Invalid Type");
        }
    }
    //endregion
    //region Entities
    //ToDo:Ask if entity name and population needs Validation.
    public Map<String, EntityDefinition> getEntitiesFromXML(PRDEntities entities)
    {
        Map<String,EntityDefinition> convertedEntities = new HashMap<>();

        for(PRDEntity entity:entities.getPRDEntity())
        {
            EntityDefinition entityDefinition = new EntityDefinitionImpl(entity.getName(), entity.getPRDPopulation());
            for(PRDProperty prdProperty : entity.getPRDProperties().getPRDProperty())
                entityDefinition.addPropertyDefinition(convertProperty(prdProperty));
            convertedEntities.put(entity.getName(),entityDefinition);
        }
        return convertedEntities;
    }
    public PropertyDefinition convertProperty(PRDProperty prop)
    {
        switch (prop.getType().toUpperCase())
        {
            case "DECIMAL":
            {
                return createIntegerProp(prop);
            }
            case "FLOAT":
            {
                return createFloatProperty(prop);
            }
            case "BOOLEAN":
            {
                return createBooleanProperty(prop);
            }
            case "STRING":
            {
                return createStringProperty(prop);
            }
            default:
                throw new InvalidTypeException("Invalid Type");
        }
    }
    private PropertyDefinition createIntegerProp(PRDProperty prop)
    {
        ValueGenerator MyValGen;
        Boolean isRandomInit = true;
        Range myPropRange = null;
        if(prop.getPRDRange() != null)
        {
            myPropRange = new Range(prop.getPRDRange().getFrom(),prop.getPRDRange().getTo());
        }
        if(prop.getPRDValue().isRandomInitialize())
            MyValGen = new RandomIntegerGenerator((int) prop.getPRDRange().getFrom(), (int) prop.getPRDRange().getTo());
        else
        {
            if(Integer.parseInt(prop.getPRDValue().getInit()) > prop.getPRDRange().getFrom()
                    ||Integer.parseInt(prop.getPRDValue().getInit()) < prop.getPRDRange().getTo()) {
                MyValGen = new FixedValueGenerator(prop.getPRDValue().getInit());
                isRandomInit = false;
            }
            else {
                throw new ValueOutOfBoundsException("Init value was out of bounds!");
            }
        }
        return new IntegerPropertyDefinition(prop.getPRDName(), PropertyType.DECIMAL,MyValGen,myPropRange,isRandomInit);
    }
    private PropertyDefinition createFloatProperty(PRDProperty prop)
    {
        ValueGenerator MyValGen;
        Boolean isRandomInit = true;
        Range myPropRange = null;
        if(prop.getPRDRange() != null)
        {
            myPropRange = new Range(prop.getPRDRange().getFrom(),prop.getPRDRange().getTo());
        }
        if(prop.getPRDValue().isRandomInitialize())
            MyValGen = new RandomDoubleGenerator(prop.getPRDRange().getFrom(),prop.getPRDRange().getTo());
        else
        {
            if(Double.parseDouble(prop.getPRDValue().getInit()) > prop.getPRDRange().getFrom()
                    ||Integer.parseInt(prop.getPRDValue().getInit()) < prop.getPRDRange().getTo()) {
                MyValGen = new FixedValueGenerator(prop.getPRDValue().getInit());
                isRandomInit = false;
            }
            else {
                throw new ValueOutOfBoundsException("Init value was out of bounds!");
            }
        }
        return new FloatPropertyDefinition(prop.getPRDName(), PropertyType.DECIMAL,MyValGen,myPropRange,isRandomInit);
    }
    private PropertyDefinition createBooleanProperty(PRDProperty prop)
    {
        ValueGenerator MyValGen;
        Boolean isRandomInit = true;
        if(prop.getPRDValue().isRandomInitialize())
            MyValGen = new RandomBooleanGenerator();
        else
        {
            if(isBoolean(prop.getPRDValue().getInit())) {
                MyValGen = new FixedValueGenerator(prop.getPRDValue().getInit());
                isRandomInit = false;
            }
            else
            {
                throw new NotABooleanException("The value given was not of Boolean Type!");
            }
        }
        return new BooleanPropertyDefinition(prop.getPRDName(), PropertyType.DECIMAL,MyValGen,isRandomInit);
    }
    private PropertyDefinition createStringProperty(PRDProperty prop)
    {
        //ToDo: Check if any string validation is needed?
        ValueGenerator MyValGen;
        Boolean isRandomInit = true;
        if(prop.getPRDValue().isRandomInitialize())
            MyValGen = new RandomStringGenerator();
        else {
            MyValGen = new FixedValueGenerator(prop.getPRDValue().getInit());
            isRandomInit = false;
        }
        return new StringPropertyDefinition(prop.getPRDName(), PropertyType.DECIMAL,MyValGen,isRandomInit);
    }
    public boolean isBoolean(String value) {
        return value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false");
    }
    //endregion
    //region Rules
    //ToDo:We need to think how our expressions are created we have problems with Environemt,Property.(Maybe we should send Context to calculate Expression method)
    private Map<String, Rule> getRulesFromXML(PRDRules prdRules) {
        Map<String, Rule> convertedRules = new HashMap<>();
        for(PRDRule rule : prdRules.getPRDRule())
        {
            convertedRules.put(rule.getName(),convertRule(rule));
        }
        return convertedRules;
    }

    private Rule convertRule(PRDRule rule)
    {
        //ToDo:See how to implement activation terms
        Rule newRule = new RuleImpl(rule.getName());
        for(PRDAction action : rule.getPRDActions().getPRDAction())
            newRule.addAction(convertActionFromXML(action));
        return newRule;
    }

    private Action convertActionFromXML(PRDAction action) {
        switch (action.getType().toUpperCase())
        {
            case "INCREASE":
                return new IncreaseAction(world.getEntities().get(action.getEntity()),getExpressionBy(action), action.getProperty());
        }
        return null;
    }

    private List<Expression> getExpressionBy(PRDAction action)
    {
        List<Expression> myExpression = new ArrayList<>();
        if(action.getBy() != null)
        {
            if(action.getBy().contains("environment"))
            {
                //myExpression = new EnvironmentFunction(action.getProperty(),);
            }
            else if(action.getBy().contains("random"))
            {
                int startIndex = action.getBy().indexOf('(') + 1;
                int endIndex = action.getBy().indexOf(')');

                if (startIndex != 0 && endIndex != -1) {
                    String numberStr = action.getBy().substring(startIndex, endIndex);
                    if (isNumeric(numberStr)) {
                        myExpression.add(new RandomFunction(numberStr));
                    } else {
                        throw new IllegalArgumentException("Invalid number format");
                    }
                } else {
                    throw new IllegalArgumentException("Input doesn't match the expected format");
                }
            }
            else if(isNumeric(action.getBy()))
            {
                myExpression.add(new GeneralExpression(action.getBy(),world.getEntities().get(action.getEntity()).getProps().get(action.getProperty()).getType()));
            }
        }
        return myExpression;
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

    private List<EntityDefinitionDTO> getEntityDTO()
    {
        List<EntityDefinitionDTO> entityDTO = new ArrayList<>();
        Map<String,PropertyDefinitionDTO> propsDTO = new HashMap<>();
        for(Map.Entry<String,EntityDefinition> entDef : world.getEntities().entrySet())
        {
            String name = entDef.getKey();
            int pop = entDef.getValue().getPopulation();
            PropertyDefinitionDTO propertyDefinitionDTO;
            for(Map.Entry<String,PropertyDefinition> propDef : entDef.getValue().getProps().entrySet())
            {
                propertyDefinitionDTO = new PropertyDefinitionDTO(propDef.getKey(),propDef.getValue().getType(),propDef.getValue().getRandomInit(),propDef.getValue().getRange());
                propsDTO.put(propertyDefinitionDTO.getName(),propertyDefinitionDTO);
            }
            entityDTO.add(new EntityDefinitionDTO(name,pop,propsDTO));
        }
        return entityDTO;
    }



}

