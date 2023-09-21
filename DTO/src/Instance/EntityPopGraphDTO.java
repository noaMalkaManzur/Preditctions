package Instance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EntityPopGraphDTO {
    List<Map<String,InstancesPerTickDTO>> graphData;

    public EntityPopGraphDTO() {
        this.graphData = new ArrayList<>();
    }

    public List<Map<String, InstancesPerTickDTO>> getGraphData() {
        return graphData;
    }
}
