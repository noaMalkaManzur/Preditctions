package engine.impl;

import Generated.ObjectFactory;
import Generated.PRDWorld;
import engine.api.Engine;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;

public class EngineImpl implements Engine
{

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
    public boolean isXMLFile(String fileName) {
        return fileName.endsWith(".xml");
    }

    @Override
    public void loadXmlFiles(String fileName)
    {
        try {
            File file = new File(fileName);
            JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            PRDWorld Factory = (PRDWorld) jaxbUnmarshaller.unmarshal(file);
            
        } catch (JAXBException e) {
            e.printStackTrace();
        }


    }
}

