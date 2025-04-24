package dao;

import model.Prescription;
import jakarta.persistence.EntityManager;
import net.datafaker.Faker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PrescriptionDAO extends GenericDAO<Prescription, String> {

    public PrescriptionDAO(Class<Prescription> clazz) {
        super(clazz);
    }

    public PrescriptionDAO(EntityManager em, Class<Prescription> clazz) {
        super(em, clazz);
    }
}
