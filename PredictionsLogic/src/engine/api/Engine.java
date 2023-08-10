package engine.api;

import java.io.FileNotFoundException;

public interface Engine
{
    boolean isFileExist(String fileName) throws FileNotFoundException;
    boolean isXMLFile(String fileName);

    void loadXmlFiles(String fileName);


}
