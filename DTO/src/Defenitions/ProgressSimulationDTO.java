package Defenitions;

import java.util.List;
import java.util.Map;

public class ProgressSimulationDTO {
    private final long seconds;
    private final Integer currTick;
    List<EntPopDTO> entPopDTO;

    public ProgressSimulationDTO(long bySeconds, Integer byTicks, List<EntPopDTO> entPopDTO) {
        this.seconds = bySeconds;
        this.currTick = byTicks;
        this.entPopDTO = entPopDTO;

    }
    public long getSeconds(){
        return seconds;
    }
    public Integer getCurrTick(){
        return currTick;
    }
    public List<EntPopDTO> getEntitiesUpdateData(){return entPopDTO;}

}
