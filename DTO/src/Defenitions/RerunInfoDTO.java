package Defenitions;

import java.util.Map;

public class RerunInfoDTO {
    Map<String,Integer> entPop;
    Map<String,String> envVal;

    public RerunInfoDTO(Map<String, Integer> entPop, Map<String, String> envVal) {
        this.entPop = entPop;
        this.envVal = envVal;
    }

    public Map<String, Integer> getEntPop() {
        return entPop;
    }

    public Map<String, String> getEnvVal() {
        return envVal;
    }
}
