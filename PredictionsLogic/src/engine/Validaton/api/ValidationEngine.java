package engine.Validaton.api;

import Defenitions.EnvPropertyDefinitionDTO;
import Generated.PRDEnvProperty;
import definition.environment.api.EnvVariablesManager;
import definition.property.api.Range;
import definition.world.api.WorldDefinition;
import exceptions.BadFileSuffixException;
import exceptions.EnvironemtVariableAlreadyExist;
import expression.api.Expression;

import java.io.FileNotFoundException;
import java.time.Instant;
import java.util.List;

public interface ValidationEngine {
    boolean isFileExist(String fileName) throws FileNotFoundException;
    boolean isXMLFile(String fileName) throws BadFileSuffixException;
    boolean isValidEnvProp(PRDEnvProperty prdEnvProperty, EnvVariablesManager envManager) throws EnvironemtVariableAlreadyExist;
    boolean isValidBooleanVar(String value);
    boolean checkEntityExist(String entityName, WorldDefinition world);
    boolean checkIfEntityHasProp(String Property,String Entity, WorldDefinition world);
    boolean checkArgsAreNumeric(List<Expression> myExpression, String entity,String type, WorldDefinition world);
    boolean isNumeric(String numberStr);
    boolean isValidIntegerVar(String userInput, Range range);
    boolean isValidDoubleVar(String userInput, Range range);
    boolean isValidStringVar(String userInput);
    boolean simulationEnded(int ticks, Instant simulationStart, WorldDefinition world);

    boolean isValidUserInput(EnvPropertyDefinitionDTO envPropertyDefinitionDTO, String userEnvInput);

}
