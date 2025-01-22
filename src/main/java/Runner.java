import entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import net.datafaker.Faker;

import java.time.LocalDate;
import java.util.HashMap;

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

//                Product product = new Product();
//                product.setProductID("P" + faker.number().digits(8));
//                product.setProductName(faker.commerce().productName());
//                product.setRegistrationNumber("REG" + faker.number().digits(6));
//                product.setPurchasePrice(faker.number().randomDouble(2, 100, 1000));
//                product.setTaxPercentage(faker.number().randomDouble(2, 5, 15));
//                product.setEndDate(LocalDate.now().plusDays(faker.number().numberBetween(30, 365)));
//                product.setCategory(category);
//                product.setVendor(vendor);
//                product.setUnitNote(faker.lorem().sentence());
//
//                HashMap<PackagingUnit, ProductUnit> unitDetails = new HashMap<>();
//                PackagingUnit unit = PackagingUnit.BOX;
//                ProductUnit productUnit = new ProductUnit();
//                productUnit.setSellPrice(faker.number().randomDouble(2, 150, 500));
//                productUnit.setInStock(faker.number().numberBetween(10, 100));
//                unitDetails.put(unit, productUnit);
//                product.setUnitDetails(unitDetails);
//
//                em.persist(product);
//
//                Medicine medicine = new Medicine();
//                medicine.setProductID("PM" + faker.number().digits(8));
//                medicine.setProductName(faker.medical().toString());
//                medicine.setRegistrationNumber("REG" + faker.number().digits(6));
//                medicine.setPurchasePrice(faker.number().randomDouble(2, 200, 2000));
//                medicine.setTaxPercentage(faker.number().randomDouble(2, 10, 20));
//                medicine.setEndDate(LocalDate.now().plusDays(faker.number().numberBetween(60, 720)));
//                medicine.setCategory(category);
//                medicine.setVendor(vendor);
//                medicine.setUnitNote(faker.lorem().sentence());
//                medicine.setActiveIngredient(faker.lorem().word());
//                medicine.setConversionUnit(faker.medical().toString());
//                medicine.setUnitDetails(unitDetails);
//
//                AdministrationRoute route = new AdministrationRoute();
//                route.setAdministrationRouteID("AR" + faker.number().digits(4));
//                route.setAdministrationRouteName(faker.lorem().word());
//                em.persist(route);
//
//                medicine.setAdministrationRoute(route);
//
//                em.persist(medicine);


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
        String accountId = faker.idNumber().valid();
        if (accountId.length() > 6) { // Giả sử cột account_id có VARCHAR(10)
            accountId = accountId.substring(0, 6); // Cắt chuỗi để vừa với cột
        }
        account.setAccountID(accountId);
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
