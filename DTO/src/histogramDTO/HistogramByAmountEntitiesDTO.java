package histogramDTO;

public class HistogramByAmountEntitiesDTO {
    int populationAfterSimulation;
    public HistogramByAmountEntitiesDTO(int populationAfterSimulation){
        this.populationAfterSimulation=populationAfterSimulation;
    }
    int getPopulationAfterSimulation(){
        return populationAfterSimulation;
    }


}
