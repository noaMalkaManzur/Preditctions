package engine.api;

import Defenitions.EnvironmentDefinitionDTO;
import definition.property.api.Range;
import exceptions.BadFileSuffixException;

import java.io.FileNotFoundException;

public interface Engine
{
    boolean isFileExist(String fileName) throws FileNotFoundException;
    boolean isXMLFile(String fileName) throws BadFileSuffixException;
    void loadXmlFiles(String fileName);

    EnvironmentDefinitionDTO getEnvDTO();


    boolean isValidIntegerVar(String userInput, Range range);

    boolean isValidDoubleVar(String userInput, Range range);

    boolean isValidBooleanVar(String userInput);

    boolean isValidStringVar(String userInput);

    void addEnvVarToActiveEnv(Object userValue, String name);
}
