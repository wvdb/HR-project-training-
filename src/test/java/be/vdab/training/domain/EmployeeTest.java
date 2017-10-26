package be.vdab.training.domain;

import be.vdab.training.enums.Country;
import be.vdab.training.utilities.DateUtility;
import org.junit.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by admin on 24/10/2017.
 */
public class EmployeeTest {
    @Test
    public void testEmployeeFirstName() {
        Employee employee = new Employee();
        employee.setFirstName("wim");

        assertEquals("getter en setter van first-name", "wim", employee.getFirstName());
    }

    @Test
    public void testEmployeeHireDateHappyFlow1() {
        Employee employee = new Employee(null, "employee 1", 49, Worker.Gender.MALE, Date.from(Instant.now()));

        Remuneration[] remunerations = new Remuneration[2];
        remunerations[0] = new MealVoucher("dagelijkse maaltijdcheque", 7.00);
        remunerations[1] = new Salary(2000D);

        employee.setRemunerations(new HashSet<>(Arrays.asList(remunerations)));
        employee.setBirthDate(DateUtility.convertLocalDateToDate(LocalDate.of(1967, 11, 2)));

        employee.withHireDate(DateUtility.convertLocalDateToDate(LocalDate.of(2014, 3, 1)))
                .withCountry(Country.BELGIUM)
                .withSocialSecurityNumber("67.11.02-367.87");

        assertTrue("employee should be valid", employee.isValid());
    }

    @Test
    public void testEmployeeHireDateSadFlow1() {
        Employee employee = new Employee(null, "employee 1", 49, Worker.Gender.MALE, Date.from(Instant.now()));

        employee.withBirthDate(DateUtility.convertLocalDateToDate(LocalDate.of(1967, 11, 2)))
                .withHireDate(DateUtility.convertLocalDateToDate(LocalDate.of(1914, 3, 1)))
                .withCountry(Country.BELGIUM)
                .withSocialSecurityNumber("67.11.02-367.87");

        assertEquals("employee should be invalid (because of HireDate)", false, employee.isValid());
    }

    @Test
    public void testEmployeeSocialSecurityNumberSadFlow1() {
        Employee employee = new Employee(null, "employee 1", 49, Worker.Gender.MALE, Date.from(Instant.now()));
        String[] invalidSocialSecurityNumbers = {null, "123", "67.12.02-367.87"};

        employee.withBirthDate(DateUtility.convertLocalDateToDate(LocalDate.of(1967, 11, 2)))
                .withHireDate(DateUtility.convertLocalDateToDate(LocalDate.of(2014, 3, 1)))
                .withCountry(Country.BELGIUM);

        for (String invalidSocialSecurityNumber : invalidSocialSecurityNumbers) {
            employee.setSocialSecurityNumber(invalidSocialSecurityNumber);
            assertEquals("employee should be invalid (because of SocialSecurityNumber). Test value = " + invalidSocialSecurityNumber, false, employee.isValid());
        }
    }

}
