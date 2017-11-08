package be.vdab.training.domain;

import be.vdab.training.enums.AddressType;
import be.vdab.training.utilities.DateUtility;

import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * This class contains the properties and the methods of an Employee. It contains
 * an validateEmployee method. <b>This method should be invoked first before persisting
 * the employee.</b>
 *
 * @author Wim Van den Brande
 * @version 0.1
 * @see Worker
 */
public class Employee extends Worker {
    public final static int MAX_NUMBER_OF_REMUNERATIONS_FOR_EMPLOYEE = 2;
    private static int numberOfEmployees;
    private static int numberOfFemaleEmployees;

    private Map<AddressType, Address> addresses;
    private Manager manager;
    private boolean validEmployee = true;

    public Employee(Manager manager, String name, Integer age, Gender gender, Date hireDate) {
        super(name, gender, hireDate);
        this.manager = manager;
        numberOfEmployees+= 1;
        if (Gender.FEMALE == gender) {
            numberOfFemaleEmployees += 1;
        }
        if (numberOfEmployees > 5 && (numberOfFemaleEmployees < numberOfEmployees / 2) && Gender.MALE == gender) {
           System.err.println("We are getting too many male employees. Employee " + this + " should be rejected.");
           this.validEmployee = false;
        }
    }

    public Employee() {
        super();
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + getId() +
                ", name=" + getFirstName() +
                ", remunerations=" + getRemunerations() +
                ", projects=" + getProjects() +
                ", department=" + department +
                '}';
    }

    @Override
    public void setRemunerations(Set<Remuneration> remunerations) {
        if (remunerations.size() <= MAX_NUMBER_OF_REMUNERATIONS_FOR_EMPLOYEE) {
            this.remunerations = remunerations;
        }
        else {
            System.err.println("Employee " + this + " is getting too many remunerations");
            // option 2
            this.validEmployee = false;
        }
    }

    @Override
    public Float calculateTotalIncentiveCost() {
        float totalIncentiveCost = 0;
//        if (objects == null) {
//            throw new IllegalArgumentException("An Employee should have at least one remuneration.");
//        }
//        if (objects.length > 1) {
//            throw new IllegalArgumentException("An Employee cannot have more than one remuneration.");
//        }

//        if (this.getRemunerations() == null || this.getRemunerations().size() == 0) {
//            throw new MyDomainException("A worker should have at least one remuneration.", "Employee");
//            // NO ADDITIONAL LOGGING
////            throw new MyDomainException2("A worker should have at least one remuneration.", "Employee");
//        }

        for (Remuneration remuneration : this.getRemunerations()) {
            if (remuneration instanceof MealVoucher) {
                totalIncentiveCost += remuneration.getCost() * MealVoucher.NUMBER_OF_OCCURRANCES;
            } else {
                totalIncentiveCost += remuneration.getCost() * Remuneration.NUMBER_OF_OCCURRANCES;
            }
        }
        return totalIncentiveCost;
    }

    public final static Float calculateTotalIncentiveCost(Remuneration[] remunerations) {
        float totalIncentiveCost = 0;
        if (remunerations == null || remunerations.length == 0) {
            throw new IllegalArgumentException("An Employee should have at least one remuneration.");
        }
        if (remunerations.length > 2) {
            throw new IllegalArgumentException("An Employee cannot have more than one remuneration.");
        }

        for (Remuneration remuneration : remunerations) {
            if (remuneration instanceof MealVoucher) {
                totalIncentiveCost += remuneration.getCost() * MealVoucher.NUMBER_OF_OCCURRANCES;
            } else {
                totalIncentiveCost += remuneration.getCost() * Remuneration.NUMBER_OF_OCCURRANCES;
            }
        }
        return totalIncentiveCost;
    }

//    public List<Exception> validateEmployee() {
    public String[] validateEmployee() {
        String[] errorMessages = new String[10];

//        int index=0;
//
//        errorMessages[index++] = getBirthDate() == null ? "birthdate is empty" : null;
//        errorMessages[index++] = getHireDate() == null ? "hiredate is empty" : null;
//        errorMessages[index++] = getFirstName() == null ? "firstname is empty" : null;
//        errorMessages[index++] = getMiddleName() == null ? "middlename is empty" : null;
//        errorMessages[index++] = getLastName() == null ? "lastname is empty" : null;
//
        // verify required properties
        if (getBirthDate() == null ||
                getHireDate() == null ||
                getFirstName() == null ||
                getMiddleName() == null ||
                getLastName() == null ||
                getSocialSecurityNumber() == null ||
                getCountry() == null ) {
            errorMessages[0] = "a property hasn't been set";
            return errorMessages;
        }
        if (!isHireDateAfterBirthDate()) {
            errorMessages[0] = "hire date is not after birth date";
            return errorMessages;
        }
        if (!isBirthDatePartOfSocialSecurityNumber()) {
            errorMessages[0] = "birth date does not match SSN";
            return errorMessages;
        }
        return errorMessages;
    }

    private boolean isHireDateAfterBirthDate() {
        return getHireDate().after(getBirthDate());
    }

    private boolean isBirthDatePartOfSocialSecurityNumber() {
        // TODO : to use Pattern class
        if (getSocialSecurityNumber().length() <= 7) {
            return false;
        }
        String yearOfSocialSecurityNumber = getSocialSecurityNumber().substring(0,2);
        String monthOfSocialSecurityNumber = getSocialSecurityNumber().substring(3,5);
        String dayOfSocialSecurityNumber = getSocialSecurityNumber().substring(6,8);
        // TODO : to disagree with recommended simplification of Intellij
        // TODO : to fix for year as of 2000
        if (Integer.parseInt(yearOfSocialSecurityNumber) + 1900 !=
                DateUtility.convertDateToLocalDate(getBirthDate()).getYear() ||
            Integer.parseInt(monthOfSocialSecurityNumber) !=
                DateUtility.convertDateToLocalDate(getBirthDate()).getMonth().getValue() ||
            Integer.parseInt(dayOfSocialSecurityNumber) !=
                DateUtility.convertDateToLocalDate(getBirthDate()).getDayOfMonth()
                ) {
            return false;
        }
        return true;
    }

    public Employee withManager(Manager manager) {
        this.manager = manager;
        return this;
    }


}
