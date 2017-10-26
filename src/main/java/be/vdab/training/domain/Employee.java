package be.vdab.training.domain;

import be.vdab.training.enums.AddressType;
import be.vdab.training.enums.Country;
import be.vdab.training.utilities.DateUtility;

import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * Created by wvdbrand on 24/08/2017.
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

    public boolean isValid() {
        // verify required properties
        if (getBirthDate() == null ||
                getHireDate() == null ||
                getFirstName() == null ||
                getMiddleName() == null ||
                getLastName() == null ||
                getSocialSecurityNumber() == null ||
                getCountry() == null ) {
            return false;
        }
        if (!isHireDateAfterBirthDate()) {
            return false;
        }
        if (!isBirthDatePartOfSocialSecurityNumber()) {
            return false;
        }
        return true;
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

    private class Address extends DatabaseEntity {
        public static final int BELGIUM_ZIP_LENGTH = 4;
        public static final int NETHERLANDS_ZIP_LENGTH = 6;
        private String street;
        private String houseNo;
        private String zip;
        private String city;

        public Address(String street, String houseNo, String zip, String city) {
            this.street = street;
            this.houseNo = houseNo;
            // to invoke the setter of ZIP
            this.setZip(zip);
            this.city = city;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getHouseNo() {
            return houseNo;
        }

        public void setHouseNo(String houseNo) {
            this.houseNo = houseNo;
        }

        public String getZip() {
            return zip;
        }

        public void setZip(String zip) {
            this.zip = zip;
            if (Employee.this.getCountry() != null && zip != null) {
                // TODO : to explain syntax of OuterClass.this
                if (   Employee.this.getCountry() == Country.BELGIUM && zip.length() != BELGIUM_ZIP_LENGTH
                    || Employee.this.getCountry() == Country.NETHERLANDS && zip.length() != NETHERLANDS_ZIP_LENGTH) {
                    System.err.println("ZIP code has an invalid length. Expected length = "
                            + (Employee.this.getCountry() == Country.BELGIUM ? BELGIUM_ZIP_LENGTH : NETHERLANDS_ZIP_LENGTH));
                }
            }
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        @Override
        public String toString() {
            return "Address{" +
                    "street='" + street + '\'' +
                    ", houseNo='" + houseNo + '\'' +
                    ", zip='" + zip + '\'' +
                    ", city='" + city + '\'' +
                    "} " + super.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Address)) {
                return false;
            }
            if (!super.equals(o)) {
                return false;
            }

            Address address = (Address) o;

            if (!city.equals(address.city)) {
                return false;
            }
            if (!houseNo.equals(address.houseNo)) {
                return false;
            }
            if (!street.equals(address.street)) {
                return false;
            }
            if (!zip.equals(address.zip)) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + street.hashCode();
            result = 31 * result + houseNo.hashCode();
            result = 31 * result + zip.hashCode();
            result = 31 * result + city.hashCode();
            return result;
        }
    }


}
