package rule;

public interface Activation {

    boolean isActive(int tickNumber);
    public Double getProbability();
    public Integer getTicks();
}
