package histogramDTO;

import java.util.Map;

public class HistoryRunningSimulationDTO {

    Map<String, String> historyRunningSimulation;
    public HistoryRunningSimulationDTO(Map<String, String> historyRunningSimulation){
        this.historyRunningSimulation=historyRunningSimulation;
    }
    public Map<String, String> getHistoryRunningSimulation(){
        return historyRunningSimulation;
    }

}
