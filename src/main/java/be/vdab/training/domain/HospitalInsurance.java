package be.vdab.training.domain;

/**
 * Created by admin on 29/09/2017.
 */
public class HospitalInsurance extends Remuneration{
    String name;

    public HospitalInsurance(String name, double cost) {
        super(cost);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
