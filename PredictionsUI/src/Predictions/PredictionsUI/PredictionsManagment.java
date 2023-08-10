package Predictions.PredictionsUI;

import engine.api.*;
import engine.impl.*;
import exceptions.BadFileSuffixException;

public class PredictionsManagment
{
    Engine engine = new EngineImpl();
    public void run()
    {
        String fileName = "C:\\Users\\noamz\\Downloads\\ex1-cigarets.xml";
        try {
            if (engine.isFileExist(fileName)) {
                if (engine.isXMLFile(fileName))
                {
                    engine.loadXmlFiles(fileName);

                } else
                {
                    throw new BadFileSuffixException("File Suffix wasn't an XML!");
                }

            }
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }
}
