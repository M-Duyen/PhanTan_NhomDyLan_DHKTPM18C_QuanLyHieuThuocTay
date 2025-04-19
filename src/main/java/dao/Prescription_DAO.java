package dao;

import entity.Prescription;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import net.datafaker.Faker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Prescription_DAO extends GenericDAO<Prescription, String> {

    public Prescription_DAO(Class<Prescription> clazz) {
        super(clazz);
    }

    public Prescription_DAO(EntityManager em, Class<Prescription> clazz) {
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

}
