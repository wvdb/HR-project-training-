package be.vdab.training.domain;

/**
 * Created by admin on 29/09/2017.
 */
public class MealVoucher extends Remuneration{
    public final static int NUMBER_OF_OCCURRANCES = 25;

    String name;

    public MealVoucher(String name, double cost) {
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
