package histogramDTO;

public class HistogramByAmountEntitiesDTO {
    int populationBeforeSimulation;
    int populationAfterSimulation;
    public HistogramByAmountEntitiesDTO(int populationAfterSimulation, int populationBeforeSimulation){
        this.populationAfterSimulation=populationAfterSimulation;
        this.populationBeforeSimulation= populationBeforeSimulation;
    }
    public int getPopulationAfterSimulation(){
        return populationAfterSimulation;
    }

    public int getPopulationBeforeSimulation(){
        return populationBeforeSimulation;
    }
}
