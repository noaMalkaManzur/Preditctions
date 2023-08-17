package Histogram.api;

import execution.instance.enitty.EntityInstance;

import java.util.Map;

public interface Histogram {
    String getGuid();
    String getSimulationTime();
    void setSimulationTime(String simulationTime);
    void setGuid(String guid);
    int getPopAfterSimulation();
    int getPopBeforeSimulation();
    Map<Integer, EntityInstance> getEntitiesInstances();
    Map<Object, Integer> getHistogramByProperty();
    void setHistogramByProperty(Map<Object, Integer> histogramByProperty);

}
