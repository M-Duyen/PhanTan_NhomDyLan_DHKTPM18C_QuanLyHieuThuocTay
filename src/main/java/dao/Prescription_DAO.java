package dao;

import entity.Prescription;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import net.datafaker.Faker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Prescription_DAO {
    private EntityManager em;

    public Prescription_DAO() {
        em = Persistence.createEntityManagerFactory("mariadb").createEntityManager();
    }
    public List<Prescription> getAll() {
        return em.createQuery("SELECT p FROM Prescription p").getResultList();
    }

    public boolean create(Prescription prescription) {
        try {
            em.getTransaction().begin();
            em.persist(prescription);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }

    public Prescription read(String id) {
        return em.find(Prescription.class, id);
    }

    public boolean update(Prescription prescription) {
        try {
            em.getTransaction().begin();
            em.merge(prescription);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean delete(String id) {
        try {
            Prescription prescription = em.find(Prescription.class, id);
            em.getTransaction().begin();
            em.remove(prescription);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public LocalDateTime convertStringToLacalDateTime(String date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;
        return LocalDateTime.parse(date, dateTimeFormatter);
    }

    public static Prescription createSamplePrescription(Faker faker) {
        Prescription prescription = new Prescription();
        prescription.setPrescriptionID("PC" + faker.number().digits(3));
        return prescription;
    }

    public static void main(String[] args) {
        Prescription_DAO prescription_dao = new Prescription_DAO();
//        prescription_dao.getAll().forEach(System.out::println);

//        Prescription prescription = new Prescription("1", prescription_dao.convertStringToLacalDateTime("2025-01-20T10:15:30"), "Flu", "Bach Mai");
////        prescription_dao.create(prescription);
//
//        prescription.setDiagnosis("Covid");
//        prescription_dao.update(prescription);

//        System.out.println(prescription_dao.read("1"));

        prescription_dao.delete("1");
    }
}
