package histogramDTO;

import java.util.Map;

public class HistogramByPropertyEntitiesDTO {
    Map<Object, Integer> histogramByProperty;
    public HistogramByPropertyEntitiesDTO(Map<Object, Integer> histogramByProperty){
        this.histogramByProperty=histogramByProperty;
    }
    public Map<Object, Integer> getHistogramByProperty(){
        return histogramByProperty;
    }

}
