package dao;

import model.MedicalSupply;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import net.datafaker.Faker;

import java.time.LocalDateTime;
import java.util.List;

public class MedicalSuppliesDAO {
    private EntityManager em;

    public MedicalSuppliesDAO() {
        em = Persistence.createEntityManagerFactory("mariadb").createEntityManager();
    }

    public List<MedicalSupply> getAll() {
        return em.createQuery("SELECT ms FROM MedicalSupply ms", MedicalSupply.class).getResultList();
    }

    public MedicalSupply getById(String id) {
        return em.find(MedicalSupply.class, id);
    }

    public boolean create(MedicalSupply medicalSupplies) {
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

    public boolean update(MedicalSupply medicalSupplies) {
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
            MedicalSupply medicalSupplies = em.find(MedicalSupply.class, id);
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
            MedicalSuppliesDAO dao = new MedicalSuppliesDAO();
            Faker faker = new Faker();

            for (int i = 0; i < 10; i++) {
                MedicalSupply ms = new MedicalSupply();
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
            List<MedicalSupply> supplies = dao.getAll();
            supplies.forEach(s -> System.out.println(s.toString()));

            if (!supplies.isEmpty()) {
                MedicalSupply ms = supplies.get(0);
                System.out.println(ms);
            }
    }
}
