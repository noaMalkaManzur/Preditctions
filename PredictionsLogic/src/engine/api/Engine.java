package engine.api;

import definition.world.api.WorldDefinition;
import exceptions.BadFileSuffixException;

import java.io.FileNotFoundException;

public interface Engine
{
    boolean isFileExist(String fileName) throws FileNotFoundException;
    boolean isXMLFile(String fileName) throws BadFileSuffixException;
    void loadXmlFiles(String fileName);


}
