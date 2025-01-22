import entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import net.datafaker.Faker;

public class Runner {
    private static EntityManager em;
    private static Faker faker = new Faker();

    public static void main(String[] args) {
        em = Persistence.createEntityManagerFactory("mariadb").createEntityManager();
        EntityTransaction tr = em.getTransaction();
        tr.begin();
        try {
            for (int i = 0; i < 10; i++) {
                AdministrationRoute administrationRoute = new AdministrationRoute();
                administrationRoute.setAdministrationRouteID("AD-" + faker.idNumber().invalid());
                administrationRoute.setAdministrationRouteName(faker.medical().hospitalName());
                em.persist(administrationRoute);

                Category category = new Category();
                category.setCategoryID("C-" + faker.idNumber().invalid());
                category.setCategoryName(faker.food().spice());
                em.persist(category);

                Prescription prescription = new Prescription();
                prescription.setPrescriptionID("P-" + faker.idNumber().invalid());
                prescription.setCreatedDate(faker.date().birthdayLocalDate().atStartOfDay());
                prescription.setDiagnosis(faker.medical().symptoms());
                prescription.setMedicalFacility(faker.medical().hospitalName());
                em.persist(prescription);

                Vendor vendor = new Vendor();
                vendor.setVendorID("V-" + faker.idNumber().invalid());
                vendor.setVendorName(faker.company().name());
                vendor.setCountry(faker.address().country());
                em.persist(vendor);
                Manager manager = generateManager();
                em.persist(manager);

                for (int j = 0; j < 5; j++) {

                    Employee employee = generateEmployee();
                    em.persist(employee);

                    Account account = generateAccount(manager, employee);
                    em.persist(account);



                }
                Customer customer = generateCustomer();
                em.persist(customer);


            }
            tr.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tr.rollback();
        }
    }

    private static Manager generateManager() {
        Manager manager = new Manager();
        manager.setManagerID(faker.idNumber().valid().substring(0,5));
        manager.setManagerName(faker.name().fullName());
        manager.setBirthDate(faker.date().birthdayLocalDate());
        manager.setPhoneNumber(faker.phoneNumber().cellPhone());
        return manager;
    }

    private static Account generateAccount(Manager manager, Employee emp) {
        Account account = new Account();
        account.setAccountID(faker.idNumber().valid());
        account.setPassword(faker.internet().password());
        account.setManager(manager);
        account.setEmployee(emp);
        return account;
    }

    private static Employee generateEmployee() {
        Employee employee = new Employee();
        employee.setEmployeeID(faker.idNumber().valid());
        employee.setEmployeeName(faker.name().fullName());
        employee.setPhoneNumber(faker.phoneNumber().cellPhone());
        employee.setBirthDate(faker.date().birthdayLocalDate());
        employee.setGender(faker.bool().bool());
        employee.setAddress(faker.address().fullAddress());
        employee.setEmail(faker.internet().emailAddress());
        employee.setStatus(faker.bool().bool());
        employee.setDegree(faker.educator().university());
        return employee;
    }

    private static Customer generateCustomer() {
        Customer customer = new Customer();
        customer.setCustomerID(faker.idNumber().valid().substring(0,10));
        customer.setCustomerName(faker.name().fullName());
        customer.setPhoneNumber(faker.phoneNumber().cellPhone().substring(0,10));
        customer.setBrithDate(faker.date().birthdayLocalDate());
        customer.setGender(faker.bool().bool());
        customer.setAddr(faker.address().fullAddress());
        customer.setEmail(faker.internet().emailAddress());
        return customer;
    }
}
