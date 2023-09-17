package Instance;

import java.util.List;
import java.util.Map;

public class EntityMangerDTO
{
    private final int count;
    private Map<String,EntityInstanceDTO> instances;

    public EntityMangerDTO(int count, Map<String,EntityInstanceDTO> instances) {
        this.count = count;
        this.instances = instances;
    }

    public int getCount() {
        return count;
    }

    public Map<String,EntityInstanceDTO> getInstances() {
        return instances;
    }
}
