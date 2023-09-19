package Defenitions;

import java.util.Map;

public class ProgressSimulationDTO {
    private final long bySeconds;
    private final Integer byTicks;
    Map<String, Integer> entitiesUpdateData;

    public ProgressSimulationDTO(long bySeconds, Integer byTicks, Map<String, Integer> entitiesUpdateData) {
        this.bySeconds = bySeconds;
        this.byTicks = byTicks;
        this.entitiesUpdateData = entitiesUpdateData;

    }
    long getBySecondsProgress(){
        return bySeconds;
    }
    Integer getByTicksProgress(){
        return byTicks;
    }
    Map<String, Integer> getEntitiesUpdateData(){return entitiesUpdateData;}

}
