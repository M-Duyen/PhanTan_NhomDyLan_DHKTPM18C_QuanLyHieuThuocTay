package dao;

import entity.Medicine;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import java.util.List;

public class Medicine_DAO {
    private EntityManager em;
    public Medicine_DAO() {
        em = Persistence.createEntityManagerFactory("mariadb").createEntityManager();
    }
    public List<Medicine> getAll(){
        return em.createQuery("SELECT m FROM Medicine m", Medicine.class).getResultList();
    }
    
    public boolean create(Medicine medicine) {
        try {
            em.getTransaction().begin();
            em.persist(medicine);
            em.getTransaction().begin();
            return true;
        }catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean update(Medicine medicine) {
        try {
            em.getTransaction().begin();
            em.merge(medicine);
            em.getTransaction().commit();
            return true;
        }catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean delete(Medicine medicine) {
        try {
            em.getTransaction().begin();
            em.remove(medicine);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
//        //test datafaker
//        Faker faker = new Faker();
//        EntityManager em = Persistence.createEntityManagerFactory("mariadb").createEntityManager();
//        EntityTransaction tr = em.getTransaction();
//        tr.begin();
//
//        try {
//            for (int i = 0; i < 10; i++) {
//                Category category = new Category();
//                category.setCategoryID("C" + faker.number().digits(5));
//                category.setCategoryName(faker.commerce().department());
//                em.persist(category);
//
//                Vendor vendor = new Vendor();
//                vendor.setVendorID("V" + faker.number().digits(5));
//                vendor.setVendorName(faker.company().name());
//                vendor.setCountry(faker.address().country());
//                em.persist(vendor);
//
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
//            }
//
//            tr.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//            tr.rollback();
//        }
    }
}
