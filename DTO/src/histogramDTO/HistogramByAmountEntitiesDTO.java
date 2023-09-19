package histogramDTO;

public class    HistogramByAmountEntitiesDTO {
    String name;
    int populationBeforeSimulation;
    int populationAfterSimulation;

    public HistogramByAmountEntitiesDTO(String name, int populationBeforeSimulation, int populationAfterSimulation) {
        this.name = name;
        this.populationBeforeSimulation = populationBeforeSimulation;
        this.populationAfterSimulation = populationAfterSimulation;
    }

    public int getPopulationAfterSimulation(){
        return populationAfterSimulation;
    }

    public int getPopulationBeforeSimulation(){
        return populationBeforeSimulation;
    }

    public String getName() {
        return name;
    }
}
