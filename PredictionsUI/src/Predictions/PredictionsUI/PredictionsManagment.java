package Predictions.PredictionsUI;

import definition.world.api.WorldDefinition;
import definition.world.impl.WorldImpl;
import engine.api.*;
import engine.impl.*;
import exceptions.BadFileSuffixException;

public class PredictionsManagment
{
    WorldDefinition myWorld = new WorldImpl();
    Engine engine = new EngineImpl();
    public void run()
    {
        //ToDo: Implement user input method.
        String fileName = "ex1-cigarets.xml";
        try {
            engine.loadXmlFiles(fileName);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }
}
