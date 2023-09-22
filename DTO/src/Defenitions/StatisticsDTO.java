package Defenitions;

public class StatisticsDTO {
    private final double averageValue;
    private final double averageTickNumbSinceChangeValue;

    public StatisticsDTO(double averageValue, double averageTickNumbSinceChangeValue) {
        this.averageValue = averageValue;
        this.averageTickNumbSinceChangeValue = averageTickNumbSinceChangeValue;
    }
    double getAverageValue(){return averageValue;}
    double getAverageTickNumbSinceChangeValue(){return averageTickNumbSinceChangeValue;}
}
