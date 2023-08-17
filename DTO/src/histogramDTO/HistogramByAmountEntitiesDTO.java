package histogramDTO;

public class HistogramByAmountEntitiesDTO {
    int populationBeforeSimulation;
    int populationAfterSimulation;
    public HistogramByAmountEntitiesDTO(int populationAfterSimulation, int populationBeforeSimulation){
        this.populationAfterSimulation=populationAfterSimulation;
        this.populationBeforeSimulation= populationBeforeSimulation;
    }
    int getPopulationAfterSimulation(){
        return populationAfterSimulation;
    }

    int getPopulationBeforeSimulation(){
        return populationBeforeSimulation;
    }
}
