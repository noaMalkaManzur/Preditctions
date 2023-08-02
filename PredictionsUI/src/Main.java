import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Actions.Action;
import Actions.Increase;
import Entities.*;
import static Utilities.Enums.eTypes.*;

public class Main
{
    public static void main(String[] args) {
        List<Entity> MyList = new ArrayList<>();
        Map<String, EntityProperty> entProperties = new HashMap<>();
        entProperties.put("lung-cancer-progress",new EntityProperty(eFloat,"lung-cancer-progress",0,12,false));
        entProperties.put("age",new EntityProperty(eDecimal,"age",15,50,true));
        entProperties.put("cigarets-per-month",new EntityProperty(eBool,"cigarets-per-month",0,500,true));
        MyList.add(new Entity("Smoker",100,entProperties));

        for(Entity MyEntity : MyList)
        {
            System.out.println(MyEntity.toString());
        }
        Action MyAction = new Increase(MyList.get(0),3.5,"lung-cancer-progress");
        MyAction.DoAction();
        for(Entity MyEntity : MyList)
        {
            System.out.println(MyEntity.toString());
        }


    }
}

