package definition.property.api;

public class Range
{
    private final double rangeFrom;
    private final double rangeTo;

    public Range(double rangeFrom, double rangeTo) {
        this.rangeFrom = rangeFrom;
        this.rangeTo = rangeTo;
    }

    public double getRangeFrom() {
        return rangeFrom;
    }

    public double getRangeTo() {
        return rangeTo;
    }

    @Override
    public String toString() {
        return Double.toString(rangeFrom) + " - " + Double.toString(rangeTo);
    }
}
