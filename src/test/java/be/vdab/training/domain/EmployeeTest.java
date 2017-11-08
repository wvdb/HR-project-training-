package be.vdab.training.domain;

import be.vdab.training.enums.Country;
import be.vdab.training.utilities.DateUtility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by admin on 24/10/2017.
 */
public class EmployeeTest {
    private static final Logger LOGGER = LogManager.getLogger(EmployeeTest.class);

    @Test
    public void testEmployeeFirstName() {
        LOGGER.debug("this is a dummy log statement");
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

        employee.withHireDate(DateUtility.convertLocalDateToDate(LocalDate.of(2014, 3, 1))).withCountry(Country.BELGIUM).withSocialSecurityNumber("67.11.02-367.87");

        assertNull("employee should be valid", employee.validateEmployee()[0]);
    }

    @Test
    @Ignore
    public void testEmployeeHireDateSadFlow1() {
        Employee employee = new Employee(null, "employee 1", 49, Worker.Gender.MALE, Date.from(Instant.now()));

        employee.withBirthDate(DateUtility.convertLocalDateToDate(LocalDate.of(1967, 11, 2)))
                .withHireDate(DateUtility.convertLocalDateToDate(LocalDate.of(1914, 3, 1)))
                .withCountry(Country.BELGIUM).withSocialSecurityNumber("67.11.02-367.87");

        assertEquals("employee should be invalid (because of HireDate)", false, employee.validateEmployee());
    }

    @Test
    @Ignore
    public void testEmployeeSocialSecurityNumberSadFlow1() {
        Employee employee = new Employee(null, "employee 1", 49, Worker.Gender.MALE, Date.from(Instant.now()));
        String[] invalidSocialSecurityNumbers = {null, "123", "67.12.02-367.87"};

        employee.withBirthDate(DateUtility.convertLocalDateToDate(LocalDate.of(1967, 11, 2))).withHireDate(DateUtility.convertLocalDateToDate(LocalDate.of(2014, 3, 1))).withCountry(Country.BELGIUM);

        for (String invalidSocialSecurityNumber : invalidSocialSecurityNumbers) {
            employee.setSocialSecurityNumber(invalidSocialSecurityNumber);
            assertEquals("employee should be invalid (because of SocialSecurityNumber). Test value = " + invalidSocialSecurityNumber, false, employee.validateEmployee());
        }
    }

    @Test
    public void testEmployeePropertiesAreSet() {
        Employee employee = new Employee(null, "employee 1", 49, Worker.Gender.MALE, null);

        assertEquals("employee should be invalid (because of missing property)", "a property hasn't been set", employee.validateEmployee()[0]);
    }
}
