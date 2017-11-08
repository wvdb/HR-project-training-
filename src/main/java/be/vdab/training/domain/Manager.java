package be.vdab.training.domain;

import java.util.Date;
import java.util.Set;

/**
 * Created by wvdbrand on 24/08/2017.
 */
public class Manager extends Worker {
    public final static int MAX_NUMBER_OF_REMUNERATIONS_FOR_MANAGER = 3;

    private Set<Employee> subordinates;
    private double financialTarget;

    public Manager(String name, Integer age, Gender gender, Date hireDate) {
        super(name, gender, hireDate);
    }

    public Manager(String firstName) {
        super.firstName = firstName;
    }

    public Set<Employee> getSubordinates() {
        return subordinates;
    }

    public void setSubordinates(Set<Employee> subordinates) {
        this.subordinates = subordinates;
    }

    public double getFinancialTarget() {
        return financialTarget;
    }

    public void setFinancialTarget(double financialTarget) {
        this.financialTarget = financialTarget;
    }

    @Override
    public void setRemunerations(Set<Remuneration> remunerations) {
        if (remunerations.size() < MAX_NUMBER_OF_REMUNERATIONS_FOR_MANAGER) {
            this.remunerations = remunerations;
        }
        else {
            System.err.println("This manager is getting too many remunerations");
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
