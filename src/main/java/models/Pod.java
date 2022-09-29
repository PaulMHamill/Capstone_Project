package models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "pod")
public class Pod extends Unit implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String podName;

    @Column
    private PodType type;

    @Column
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
