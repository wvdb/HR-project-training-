package be.vdab.training.domain;

import be.vdab.training.enums.Country;
import be.vdab.training.utilities.DateUtility;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

/**
 * Created by wvdbrand on 24/08/2017.
 */
public abstract class Worker extends DatabaseEntity implements Workable {
    protected String firstName;
    protected String middleName;
    protected String lastName;

    private Gender gender;
    private Date hireDate;
    private Date birthDate;

    protected Department department;
    private Set<Project> projects;
    protected Set<Remuneration> remunerations;

    private String socialSecurityNumber;
    private Country country;

    public Worker() {
        super();
    }

    public Worker(String firstName, Gender gender, Date hireDate) {
        super();
        this.firstName = firstName;
        this.gender = gender;
        this.hireDate = hireDate;
    }

    public enum Gender {
        MALE, FEMALE, OTHER
    }

    public String getMiddleName() {
        if (middleName == null ) {
            middleName = "";
        }
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        if (lastName == null ) {
            lastName = "";
        }
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getFullName(String firstName, String middleName, String lastName) {
        return firstName + " " + middleName + " " + lastName;
    }

    @Override
    public String getFullName(String... partOfNames) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String partOfName : partOfNames) {
            stringBuilder.append(partOfName);
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }

    public String getFirstName() {
//        if (firstName == null ) {
//            firstName = "";
//        }
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    abstract public Float calculateTotalIncentiveCost();

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Set<Project> getProjects() {
        if (projects == null) {
            projects = new HashSet<>();
        }
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public Set<Remuneration> getRemunerations() {
        if (remunerations == null) {
            remunerations = new HashSet<>();
        }
        return remunerations;
    }

    protected abstract void setRemunerations(Set<Remuneration> remunerations);

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        if (birthDate == null) {
            throw new IllegalArgumentException("birthDate is unknown");
        }

        this.birthDate = birthDate;

        Date currentDate = new Date();
        LocalDate localCurrentDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        LocalDate localBirthDate = DateUtility.convertDateToLocalDate(birthDate);

        if (localCurrentDate.minusYears(18).compareTo(localBirthDate) < 0) {
            throw new IllegalArgumentException("birthDate should be at least 18 years in the past");
        }
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }


    public Worker withCountry(Country country) {
        this.country = country;
        return this;
    }

    public Worker withHireDate(Date hireDate) {
        this.hireDate = hireDate;
        return this;
    }

    public Worker withBirthDate(Date birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public Worker withProjects(Set<Project> projects) {
        this.projects = projects;
        return this;
    }


    public Worker withSocialSecurityNumber(String socialSecurityNumber) {
        this.setSocialSecurityNumber(socialSecurityNumber);
        return this;
    }

    public String[] validateWorkerWithArray() {
        String[] errorMessages = new String[10];

//        int index=0;
////
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

    public List<Exception> validateWorkerWithListOfExceptions() {
        List<Exception> exceptions = new ArrayList<>();
//        List<Exception> exceptions = null;

        if (getBirthDate() == null) {
            exceptions.add(new IllegalArgumentException("birthdate is empty"));
        }
        if (getHireDate() == null) {
            exceptions.add(new IllegalArgumentException("hiredate is empty"));
        }
        if (getFirstName() == null) {
            exceptions.add(new IllegalArgumentException("firstname is empty"));
        }
        if (getMiddleName() == null) {
            exceptions.add(new IllegalArgumentException("middlename is empty"));
        }
        if (getLastName() == null) {
            exceptions.add(new IllegalArgumentException("lastname is empty"));
        }

        if (exceptions.size() > 0 ) {
            return exceptions;
        }

        if (!isHireDateAfterBirthDate()) {
            exceptions.add(new IllegalArgumentException("hiredate is not after birthdate"));
            return exceptions;
        }
        if (!isBirthDatePartOfSocialSecurityNumber()) {
            exceptions.add(new IllegalArgumentException("birth date does not match SSN"));
            return exceptions;
        }

        return exceptions;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Worker worker = (Worker) o;

        if (!getFirstName().equals(worker.getFirstName())) return false;
        if (!getMiddleName().equals(worker.getMiddleName())) return false;
        return getLastName().equals(worker.getLastName());

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getFirstName().hashCode();
        result = 31 * result + getMiddleName().hashCode();
        result = 31 * result + getLastName().hashCode();
        return result;
    }

    public class Address extends DatabaseEntity {
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
            if (Worker.this.getCountry() != null && zip != null) {
                // TODO : to explain syntax of OuterClass.this
                if (   Worker.this.getCountry() == Country.BELGIUM && zip.length() != BELGIUM_ZIP_LENGTH
                        || Worker.this.getCountry() == Country.NETHERLANDS && zip.length() != NETHERLANDS_ZIP_LENGTH) {
                    System.err.println("ZIP code has an invalid length. Expected length = "
                            + (Worker.this.getCountry() == Country.BELGIUM ? BELGIUM_ZIP_LENGTH : NETHERLANDS_ZIP_LENGTH));
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
            if (!(o instanceof Worker.Address)) {
                return false;
            }
            if (!super.equals(o)) {
                return false;
            }

            Worker.Address address = (Worker.Address) o;

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
