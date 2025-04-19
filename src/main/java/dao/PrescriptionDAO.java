package dao;

import entity.Prescription;
import jakarta.persistence.EntityManager;
import net.datafaker.Faker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PrescriptionDAO extends GenericDAO<Prescription, String> {
    private EntityManager em;

    public PrescriptionDAO(Class<Prescription> clazz) {
        super(clazz);
    }

    public PrescriptionDAO(EntityManager em, Class<Prescription> clazz) {
        super(em, clazz);
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
        //Prescription_DAO prescription_dao = new Prescription_DAO();
//        prescription_dao.getAll().forEach(System.out::println);

//        Prescription prescription = new Prescription("1", prescription_dao.convertStringToLacalDateTime("2025-01-20T10:15:30"), "Flu", "Bach Mai");
////        prescription_dao.create(prescription);
//
//        prescription.setDiagnosis("Covid");
//        prescription_dao.update(prescription);

//        System.out.println(prescription_dao.read("1"));

        //prescription_dao.delete("1");
    }
}
