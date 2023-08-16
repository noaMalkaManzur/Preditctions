package Histogram.api;

import definition.entity.EntityDefinition;

import java.time.Instant;

public interface Histogram {
    String getGuid();
    Instant getSimulationTime();

}
