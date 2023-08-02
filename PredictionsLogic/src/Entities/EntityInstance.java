package Entities;

import java.util.Map;
import java.util.Random;

public class EntityInstance
{
    Entity entityInstance;
    public static final int MAXSTRLEN =50;
    public static final int MINSTRLEN = 10;
    public EntityInstance(Entity entityInstance) {
        this.entityInstance = entityInstance;
        Map<String,EntityProperty> MyPropList = entityInstance.getEntProperties();
        for(Map.Entry<String, EntityProperty> MyEntry : MyPropList.entrySet())
        {
            EntityProperty MyProp = MyEntry.getValue();
            if(MyProp.isRandom_init())
            {
                MyProp.setPropVal(randomInit(MyProp));
            }
        }
    }
    private Object randomInit(EntityProperty entProp)
    {
        Random MyRand = new Random();
        Object MyRandVal = new Object();
        switch (entProp.getType())
        {
            case eDecimal:
                MyRandVal = MyRand.nextInt((int)(entProp.getRangeTo()-entProp.getRangeFrom()+1))+ 10;
                return MyRandVal;
            case eFloat:
                MyRandVal = MyRand.nextFloat()* entProp.getRangeFrom()+(entProp.getRangeTo()-entProp.getRangeFrom());
                return MyRandVal;
            case eBool:
                MyRandVal = MyRand.nextBoolean();
                return MyRandVal;
            case eString:
                int MyStrLen = MyRand.nextInt(MAXSTRLEN-MINSTRLEN +1)+10;
                //Random string;
                return MyRandVal;
            default:
                return null;
        }
    }
}
