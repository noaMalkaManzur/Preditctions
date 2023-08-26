package engine.Validaton.impl;

import Generated.PRDAction;
import Generated.PRDEnvProperty;
import definition.environment.api.EnvVariablesManager;
import definition.property.api.PropertyType;
import definition.property.api.Range;
import definition.world.api.WorldDefinition;
import engine.Validaton.api.ValidationEngine;
import exceptions.*;
import expression.api.Expression;
import expression.api.eExpression;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class ValidationEngineImpl implements ValidationEngine {


    @Override
    public boolean isFileExist(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        if (file.exists())
            return true;
        else {
            throw new MyFileNotFoundException("File was not found in our system");
        }
    }

    @Override
    public boolean isXMLFile(String fileName) throws BadFileSuffixException {
        if (fileName.endsWith(".xml"))
            return true;
        throw new BadFileSuffixException("The file was not an XML file!");
    }

    @Override
    public boolean isValidEnvProp(PRDEnvProperty prdEnvProperty, EnvVariablesManager envManager) throws EnvironemtVariableAlreadyExist {
        if(envManager.getEnvVariables().containsKey(prdEnvProperty.getPRDName()))
            throw new EnvironemtVariableAlreadyExist("This environment variable name already exist name:" + prdEnvProperty.getPRDName());
        return true;
    }

    @Override
    public boolean isBoolean(String value) {
        return value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false");
    }

    @Override
    public boolean checkEntityExist(PRDAction action, WorldDefinition world) {
        if(action.getEntity() == null && action.getType().equals("proximity")){
            return true;
        }
        if(!world.getEntities().containsKey(action.getEntity()))
        {
            throw new EntityNotExistException("Entity: " + action.getEntity() + " does not exist in this world.");
        }
        return true;
    }

    @Override
    public boolean checkIfEntityHasProp(String Property, String Entity, WorldDefinition world) {
        if(world.getEntities().get(Entity).getProps().containsKey(Property))
            return true;
        throw new PropertyNotExistException("Entity: " + Entity + " does not have property named:" + Property);
    }

    @Override

    public boolean checkArgsAreNumeric(List<Expression> myExpression, String entity,String type, WorldDefinition world)
    {
        myExpression.forEach(expression -> {
            if(expression.getType() != eExpression.FUNCTION)
            {
                Object propVal = expression.getArg();
                if(expression.getType() == eExpression.PROPERTY) {
                    if(checkIfEntityHasProp((String) propVal,entity, world))
                    {
                        if(!(world.getEntities().get(entity).getProps().get((String)propVal).getType() == PropertyType.DECIMAL ||
                                world.getEntities().get(entity).getProps().get((String)propVal).getType() == PropertyType.FLOAT))
                            throw new PropertyTypeNotFittingException("Property:" + (String)propVal + "is not a numeric property");
                    }
                }
                else
                {
                    try{
                        PropertyType.DECIMAL.parse((String) propVal);
                    }
                    catch(Exception ignore)
                    {
                        try{
                            PropertyType.FLOAT.parse((String) propVal);
                        }
                        catch (Exception e)
                        {
                            throw new ArgumentNotNumericTypeException("The given argument " + (String)propVal +" is not of Numeric type in action:" + type);
                        }

                    }
                }
            }
        });
        return true;
    }

    @Override
    public boolean isNumeric(String numberStr) {
        if (numberStr == null || numberStr.isEmpty()) {
            return false;
        }
        boolean hasDecimalPoint = false;
        boolean hasNegativeSign = false;

        for (int i = 0; i < numberStr.length(); i++) {
            char c = numberStr.charAt(i);

            if (i == 0 && c == '-') {
                hasNegativeSign = true;
                continue;
            }

            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
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
    public boolean isValidStringVar(String userInput) {
        return true;
    }

    @Override
    public boolean simulationEnded(int ticks, Instant simulationStart, WorldDefinition world) {
        long diff = Duration.between(simulationStart, Instant.now()).toMillis()/ 1000;
        return (world.getTerminationTerm().getByTicks() == ticks || diff >= world.getTerminationTerm().getBySeconds());
    }


}
