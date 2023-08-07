import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Actions.Action;
import Actions.Increase;
import Entities.*;
import static Utilities.Enums.eTypes.*;

import Generated.PRDProperty;
import Generated.PRDValue;
import Utilities.Utilities;

public class Main
{
    public static void main(String[] args) {
        List<Entity> MyList = new ArrayList<>();
        Map<String, EntityProperty> entProperties = new HashMap<>();
        entProperties.put("lung-cancer-progress",new EntityProperty(eFloat,"lung-cancer-progress",0,12,false));
        entProperties.put("age",new EntityProperty(eDecimal,"age",15,50,true));
        entProperties.put("cigarets-per-month",new EntityProperty(eBool,"cigarets-per-month",0,500,true));
        Entity MyEntity = new Entity("Smoker",100,entProperties);
        PRDProperty prdProperty = new PRDProperty();
        PRDValue prdValue = new PRDValue();
        prdValue.setInit("5");
        prdProperty.setPRDValue(prdValue);
        EntityInstance myInstance = new EntityInstance(MyEntity,prdProperty);


        System.out.println(Utilities.generateRandomString(50));
        System.out.println(Utilities.generateRandomString(50));
        System.out.println(Utilities.random(50));



    }
}

