import entity.AdministrationRoute;
import entity.Category;
import entity.Prescription;
import entity.Vendor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import net.datafaker.Faker;

public class Runner {
    public static void main(String[] args) {
        EntityManager em = Persistence.createEntityManagerFactory("mariadb").createEntityManager();
        EntityTransaction tr = em.getTransaction();
        Faker faker = new Faker();
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


            }
            tr.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tr.rollback();
        }


    }
}
