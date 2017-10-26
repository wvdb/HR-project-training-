package be.vdab.training.domain;

import java.util.Date;
import java.util.Set;

/**
 * Created by wvdbrand on 24/08/2017.
 */
public class Director extends Worker {
    public final static int MAX_NUMBER_OF_REMUNERATIONS_FOR_DIRECTOR = 4;

    public Director(String name, Integer age, Gender gender, Date hireDate) {
        super(name, gender, hireDate);
    }

    @Override
    public void setRemunerations(Set<Remuneration> remunerations) {
        if (remunerations.size() < MAX_NUMBER_OF_REMUNERATIONS_FOR_DIRECTOR) {
            this.remunerations = remunerations;
        }
        else {
            System.err.println("This director is getting too many remunerations");
        }
    }

    @Override
    public Float calculateTotalIncentiveCost() {
        float totalIncentiveCost = 0;

        for (Remuneration remuneration : this.getRemunerations()) {
            if (remuneration instanceof MealVoucher) {
                totalIncentiveCost += remuneration.getCost() * MealVoucher.NUMBER_OF_OCCURRANCES;
            } else {
                totalIncentiveCost += remuneration.getCost() * Remuneration.NUMBER_OF_OCCURRANCES;
            }
        }
        return totalIncentiveCost;
    }

}
