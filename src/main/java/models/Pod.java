package models;

public class Pod extends Unit{
    private String podName;
    private PodType type;
    private double nightlyRate;

    public Pod(String podName, PodType podType, double nightlyRate) {
        super(podType.getCapacity());
        this.podName = podName;
        this.type = podType;
        this.nightlyRate = nightlyRate;
    }

    public String getPodName() {
        return podName;
    }

    public void setPodName(String podName) {
        this.podName = podName;
    }

    public double getNightlyRate() {
        return nightlyRate;
    }

    public void setNightlyRate(double nightlyRate) {
        this.nightlyRate = nightlyRate;
    }
}
