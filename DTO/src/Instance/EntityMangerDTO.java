package Instance;

import java.util.List;

public class EntityMangerDTO
{
    private final int count;
    private List<EntityInstanceDTO> instances;

    public EntityMangerDTO(int count, List<EntityInstanceDTO> instances) {
        this.count = count;
        this.instances = instances;
    }

    public int getCount() {
        return count;
    }

    public List<EntityInstanceDTO> getInstances() {
        return instances;
    }
}
