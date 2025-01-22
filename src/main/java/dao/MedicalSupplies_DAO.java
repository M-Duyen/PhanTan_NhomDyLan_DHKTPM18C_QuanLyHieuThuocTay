package dao;

import entity.MedicalSupplies;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import net.datafaker.Faker;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MedicalSupplies_DAO {
    private EntityManager em;

    public MedicalSupplies_DAO() {
        em = Persistence.createEntityManagerFactory("mariadb").createEntityManager();
    }

    public List<MedicalSupplies> getAll() {
        return em.createQuery("SELECT ms FROM MedicalSupplies ms", MedicalSupplies.class).getResultList();
    }

    public MedicalSupplies getById(String id) {
        return em.find(MedicalSupplies.class, id);
    }

    public boolean create(MedicalSupplies medicalSupplies) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            em.persist(medicalSupplies);
            tr.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            tr.rollback();
            return false;
        }
    }

    public boolean update(MedicalSupplies medicalSupplies) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            em.merge(medicalSupplies);
            tr.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            tr.rollback();
            return false;
        }
    }

    public boolean delete(String id) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            MedicalSupplies medicalSupplies = em.find(MedicalSupplies.class, id);
            if (medicalSupplies != null) {
                em.remove(medicalSupplies);
            }
            tr.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            tr.rollback();
            return false;
        }
    }

    public static void main(String[] args) {
            MedicalSupplies_DAO dao = new MedicalSupplies_DAO();
            Faker faker = new Faker();

            for (int i = 0; i < 10; i++) {
                MedicalSupplies ms = new MedicalSupplies();
                ms.setProductID("MS" + faker.number().digits(5));
                ms.setProductName(faker.commerce().productName());
                ms.setRegistrationNumber(faker.number().digits(6));
                ms.setPurchasePrice(faker.number().randomDouble(2, 100, 1000));
                ms.setTaxPercentage(faker.number().randomDouble(2, 5, 15));
                ms.setEndDate(LocalDateTime.now().toLocalDate());
                ms.setMedicalSupplyType(faker.medical().toString());
                ms.setUnitNote(faker.lorem().sentence());

                boolean isCreated = dao.create(ms);
                System.out.println("Đã thêm: " + isCreated);
            }
            List<MedicalSupplies> supplies = dao.getAll();
            supplies.forEach(s -> System.out.println(s.toString()));

            if (!supplies.isEmpty()) {
                MedicalSupplies ms = supplies.get(0);
                System.out.println(ms);
            }
    }
}
