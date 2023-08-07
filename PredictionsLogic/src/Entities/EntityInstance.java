package Entities;

import Generated.PRDEntity;
import Generated.PRDProperty;
import Utilities.Utilities;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class EntityInstance
{
    Entity entityData;
    Map<String,Object> propValues = new HashMap<>();

    public EntityInstance(Entity entityData, PRDProperty prdProperty) {
        this.entityData = entityData;
        Map<String,EntityProperty> MyPropList = entityData.getEntProperties();
        for(Map.Entry<String, EntityProperty> MyEntry : MyPropList.entrySet())
        {
            String MyEntryKey = MyEntry.getKey();
            EntityProperty MyEntryVal = MyEntry.getValue();
            if(MyEntryVal.isRandom_init())
            {
                propValues.put(MyEntryKey,Utilities.randomInit(MyEntryVal));
            }
            else
            {
                propValues.put(MyEntryKey,prdProperty.getPRDValue().getInit());
            }
        }
    }

    public Map<String, Object> getPropValues() {
        return propValues;
    }

    public void setPropValues(Map<String, Object> propValues) {
        this.propValues = propValues;
    }

    public Entity getEntityData() {
        return entityData;
    }


}
