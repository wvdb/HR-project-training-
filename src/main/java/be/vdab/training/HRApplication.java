package be.vdab.training;

import be.vdab.training.domain.*;
import be.vdab.training.enums.Country;
import be.vdab.training.exceptions.MyCustomizedException;
import be.vdab.training.utilities.DateUtility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.XMLReaders;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

public class HRApplication {
    private static final Logger LOGGER = LogManager.getLogger(HRApplication.class);

    private static final String CONTACT_XML = "C:\\wim\\oak3 - cronos- training\\cursus_data_input_output\\Contact.xml";
    private static final String CONTACT_XML_WITH_XSD
            = "C:\\wim\\oak3 - cronos- training\\cursus_data_input_output\\Contact_with_xsd.xml";
    private static final String CONTACT_XML_WITH_XSD2
            = "C:\\wim\\oak3 - cronos- training\\cursus_data_input_output\\Contact_with_xsd2.xml";
    private static final String CONTACT_XML_7C
            = "C:\\wim\\oak3 - cronos- training\\cursus_data_input_output\\Contact_with_xsd_7C.xml";
    private static final String CONTACT_XML_7D
            = "Contact_with_xsd_7D.xml";

    public static int numberOfEmployees = 0;
    public static int numberOfManagers = 0;
    public static int numberOfDirectors = 0;

    public static void main(String[] args) throws MyCustomizedException, IOException, JDOMException {
//        HRApplication.jDOM_opdracht2();
//        HRApplication.jDOM_opdracht_4_en_5();
//        HRApplication.jDOM_opdracht7A();
//        HRApplication.jDOM_opdracht7B();
//        HRApplication.jDOM_opdracht7C();
        HRApplication hrApplication = new HRApplication();
        hrApplication.jDOM_opdracht7D();
//        HRApplication.myCompany_98();
//        HRApplication.myCompany_99();
    }

    private static void jDOM_opdracht2() throws IOException {
        Document document = new Document();
//        DocType dtd = new DocType("contact", "Contact.dtd");
        DocType dtd = new DocType("contact", "xxx.dtd");
        document.setDocType(dtd);

        Element contact = new Element("contact");
        document.setRootElement(contact);

        Element name = new Element("name");
        name.addContent("van den brande");

        Element address = new Element("address");
        address.addContent("Edegem");

        Element country = new Element("country");
        country.addContent("Belgium");

        Element phone1 = new Element("phone");
        Attribute attribute1 = new Attribute("type", "private_mobile");
        phone1.setAttribute(attribute1);
        phone1.addContent("+32485717182");

        Element phone2 = new Element("phone");
        Attribute attribute2 = new Attribute("type", "private_fixed");
        phone2.setAttribute(attribute2);
        phone2.addContent("+323485123456");

        Element notes = new Element("notes");
        notes.addContent("Java Software Engineer & Seasoned");

        contact.addContent(name);
        contact.addContent(address);
        contact.addContent(country);
        contact.addContent(phone1);
        contact.addContent(phone2);
        contact.addContent(notes);

        printDocument(document);
    }

    private static void jDOM_opdracht_4_en_5() throws IOException, JDOMException {
        SAXBuilder builder = new SAXBuilder();
        File file  = new File (CONTACT_XML);
        
        Document document = builder.build(file);
        printDocument(document);

        Element root = document.getRootElement();

        Element country = root.getChild("country");
        Element phone = root.getChild("phone");

        System.out.println("Country = " + country.getValue());
        System.out.println("Country = " + country.getText());
        System.out.println("Phone = " + phone.getValue() + ", type = " + phone.getAttributeValue("type"));
    }

    private static void jDOM_opdracht7A() throws IOException {
        // example : http://www.studytrails.com/java/xml/jdom2/java-xml-jdom2-saxbuilder-xsd-validating/

        Document document = new Document();

        Element contact = new Element("contact");
        Namespace sns = Namespace.getNamespace("xsi", "http://www.w3.org:2001/XMLSchema-instance");

//        contact.setAttribute("schemaLocation", "file:///C:/wim/oak3 - cronos- training/cursus_data_input_output/Contact.xsd", sns);
        contact.setAttribute("schemaLocation", "http://www.noelvaes.eu/contact Contact.xsd");

        document.setRootElement(contact);

        Element name = new Element("name");
        name.addContent("van den brande");

        Element address = new Element("address");
        address.addContent("Edegem");

        Element country = new Element("country");
        country.addContent("Belgium");

        Element phone1 = new Element("phone");
        Attribute attribute1 = new Attribute("type", "private_mobile");
        phone1.setAttribute(attribute1);
        phone1.addContent("+32485717182");

        Element phone2 = new Element("phone");
        Attribute attribute2 = new Attribute("type", "private_fixed");
        phone2.setAttribute(attribute2);
        phone2.addContent("+323485123456");

        Element notes = new Element("notes");
        notes.addContent("Java Software Engineer & Seasoned");

        contact.addContent(name);
        contact.addContent(address);
        contact.addContent(country);
        contact.addContent(phone1);
        contact.addContent(phone2);
        contact.addContent(notes);

        printDocument(document);
    }

    private static void jDOM_opdracht7B() throws IOException, JDOMException {
        SAXBuilder builder = new SAXBuilder(XMLReaders.XSDVALIDATING);
        Document myJdom = builder.build(new File(CONTACT_XML_WITH_XSD2));

        System.out.println("(jDOM_opdracht7B) Our XML is XSD compliant");

        printDocument(myJdom);
    }

    private static void jDOM_opdracht7C() throws IOException, JDOMException {
        SAXBuilder builder = new SAXBuilder(XMLReaders.XSDVALIDATING);
        Document myJdom = builder.build(new File(CONTACT_XML_7C));

        System.out.println("(jDOM_opdracht7C) Our XML is XSD compliant");

        printDocument(myJdom);
    }

    private void jDOM_opdracht7D() throws IOException, JDOMException {
        // TODO: to explain other options of XMLReaders (NONVALIDATING / DTDVALIDATING)
        SAXBuilder builder = new SAXBuilder(XMLReaders.XSDVALIDATING);

        // TODO: emphasize we are using a resource file
        // resource files should be located in target/classes

        ClassLoader classLoader = this.getClass().getClassLoader();
        Document myJdom = builder.build(new File(classLoader.getResource(CONTACT_XML_7D).getFile()));

        System.out.println("(jDOM_opdracht7D) Our XML is XSD compliant");

        printDocument(myJdom);
    }

    private static void printDocument(Document document) throws IOException {
        XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
        xmlOutputter.output(document, System.out);
    }

    private static void myCompany_98() {
        Employee employee1 = new Employee(null, "employee 1", 49, Worker.Gender.MALE, Date.from(Instant.now()));
        Manager manager = new Manager("vrouw van employee 1");

        PairWorker<Employee, Employee> pairWorker1 = new PairWorker<>(employee1, employee1);
        pairWorker1.pairProgramming();

        PairWorker<Employee, Manager> pairWorker2 = new PairWorker<>(employee1, manager);
        pairWorker2.pairProgramming();
    }

    private static void myCompany_99() {
        Worker[] workers = new Worker[10];
        int index = 0;

        Employee employee1 = new Employee(null, "employee 1", 49, Worker.Gender.MALE, Date.from(Instant.now()));

        employee1.setRemunerations(getCorrectNumberOfRemunerationsForEmployee(1000));
        employee1.setBirthDate(DateUtility.convertLocalDateToDate(LocalDate.of(1967, 11, 2)));

        employee1.withHireDate(DateUtility.convertLocalDateToDate(LocalDate.of(2014, 3, 1)));
        employee1.withCountry(Country.BELGIUM);
        // withManager first (manager returns an employee, worker returns a worker) !!!
        employee1.withManager(null).withSocialSecurityNumber("67.11.02-367.87");

        Employee.Address address1 = employee1.new Address("street 1", "house no 1", "zip 1", "commune 1");
        Employee.Address address2 = employee1.new Address("street 2", "house no 2", "2650", "Edegem");
        Employee.Address address3 = employee1.new Address("street 2", "house no 2", "2650", "Edegem");

        // TODO : to emphasize usage of placeholders
        LOGGER.info("testje van logging {} {}", null, null);
        LOGGER.info("Total cost of employee: {} = {}.", employee1, employee1.calculateTotalIncentiveCost());
        workers[index++] = employee1;

        Employee employee2 = new Employee(null, "employee 2", 49, Worker.Gender.MALE, Date.from(Instant.now()));
        employee2.setRemunerations(getInCorrectNumberOfRemunerationsForEmployee(2000));
        workers[index++] = employee2;

        Employee employee3 = new Employee(null, "employee 3", 49, Worker.Gender.MALE, Date.from(Instant.now()));
        employee3.setRemunerations(getCorrectNumberOfRemunerationsForEmployee(3000));
        workers[index++] = employee3;

        Employee employee4 = new Employee(null, "employee 4", 49, Worker.Gender.MALE, Date.from(Instant.now()));
        employee4.setRemunerations(getCorrectNumberOfRemunerationsForEmployee(4000));
        workers[index++] = employee4;

        Employee employee5 = new Employee(null, "employee 5", 49, Worker.Gender.MALE, Date.from(Instant.now()));
        employee5.setRemunerations(getCorrectNumberOfRemunerationsForEmployee(5000));
        workers[index++] = employee5;

        Employee employee6 = new Employee(null, "employee 6", 49, Worker.Gender.MALE, Date.from(Instant.now()));
        employee6.setRemunerations(getCorrectNumberOfRemunerationsForEmployee(6000));
        workers[index++] = employee6;

        Employee employee7 = new Employee(null, "employee 6", 49, null, Date.from(Instant.now()));
        employee7.setRemunerations(getCorrectNumberOfRemunerationsForEmployee(6000));
        workers[index++] = employee7;

        Employee employee8 = new Employee(null, "employee 5", 49, Worker.Gender.MALE, Date.from(Instant.now()));
        employee8.setRemunerations(getCorrectNumberOfRemunerationsForEmployee(5000));
        workers[index++] = employee8;

        countWorkerTypes(workers);
        LOGGER.info("Number of valid Employees = " + numberOfEmployees);
        LOGGER.info("Number of valid Managers = " + numberOfManagers);
        LOGGER.info("Number of valid Director = " + numberOfDirectors);
        
        LOGGER.info("Number of unique workers = " + getListWithUniqueEmployees(workers).size());
    }


    private static Set<Remuneration> getCorrectNumberOfRemunerationsForEmployee(float salary) {
        Remuneration[] remunerations = new Remuneration[2];
        remunerations[0] = new MealVoucher("dagelijkse maaltijdcheque", 7.00);
        remunerations[1] = new Salary(salary);

        return new HashSet<>(Arrays.asList(remunerations));
    }

    private static Set<Remuneration> getInCorrectNumberOfRemunerationsForEmployee(float salary) {
        Remuneration[] remunerations = new Remuneration[3];
        remunerations[0] = getCorrectNumberOfRemunerationsForEmployee(salary).stream().findFirst().get();
        // TODO : second
        remunerations[1] = getCorrectNumberOfRemunerationsForEmployee(salary).stream().findFirst().get();
        remunerations[2] = new MobilePhone("I-phone", "0485717182", 1000.00);
        return new HashSet<>(Arrays.asList(remunerations));
    }

    private static List<Worker> getListWithUniqueEmployees(Worker[] arrayOfWorkers) {
        List<Worker> workers = new ArrayList<>();

        for (int i = 0; i < arrayOfWorkers.length && arrayOfWorkers[i] != null; i++) {
            boolean workerIsUnique = true;

            for(int j = i+1; j < arrayOfWorkers.length && arrayOfWorkers[j] != null; j++) {
                if (arrayOfWorkers[i].getFirstName().equals(arrayOfWorkers[j].getFirstName()) &&
                    arrayOfWorkers[i].getMiddleName().equals(arrayOfWorkers[j].getMiddleName()) &&
                    arrayOfWorkers[i].getLastName().equals(arrayOfWorkers[j].getLastName())) {
                    // worker is not unique -- we ignore this worker
                    workerIsUnique = false;
                }
            }

            if (workerIsUnique) {
                // we've got a unique worker - let's add it to our list
                workers.add(arrayOfWorkers[i]);
            }
        }

        return workers;
    }

    private static void countWorkerTypes(Worker[] workers) {
        for(Worker worker: workers) {
            if (worker instanceof Employee) {
                Employee employee = (Employee) worker;
                // option 1
//                if (isEligibleForHRSystem(employee.getRemunerations(), Employee.MAX_NUMBER_OF_REMUNERATIONS_FOR_EMPLOYEE)) {
//                    if (employee.validateWorkerWithArray()) {
//                        numberOfEmployees++;
//                    }
//                }

//                // option 2
//                if (employee.validateWorkerWithArray(errorMessage)) {
//                    numberOfEmployees++;
//                }
            } else if (worker instanceof  Manager) {
                Manager manager = (Manager) worker;
                if (isEligibleForHRSystem(manager.getRemunerations(), Manager.MAX_NUMBER_OF_REMUNERATIONS_FOR_MANAGER)) {
                    numberOfManagers++;
                }
            } else {
                if (worker != null ) {
                    Director director = (Director) worker;
                    if (isEligibleForHRSystem(director.getRemunerations(), Director.MAX_NUMBER_OF_REMUNERATIONS_FOR_DIRECTOR)) {
                        numberOfDirectors++;
                    }
                }
            }
        }

    }

    private static boolean isEligibleForHRSystem(Set<Remuneration> remunerations, int maxNumberOfRemunerationsAllowed) {
        return remunerations != null && remunerations.size() <= maxNumberOfRemunerationsAllowed;
    }
}
