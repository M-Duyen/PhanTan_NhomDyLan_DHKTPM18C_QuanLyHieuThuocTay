package dao;

import model.Prescription;
import jakarta.persistence.EntityManager;
import net.datafaker.Faker;
import org.apache.tomcat.Jar;
import utils.JPAUtil;

import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PrescriptionDAO extends GenericDAO<Prescription, String> {
    private EntityManager em;

    public PrescriptionDAO(Class<Prescription> clazz) {
        super(clazz);
        this.em = JPAUtil.getEntityManager();
    }

    public PrescriptionDAO(EntityManager em, Class<Prescription> clazz) {
        super(em, clazz);
    }

    public LocalDateTime convertStringToLacalDateTime(String date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;
        return LocalDateTime.parse(date, dateTimeFormatter);
    }
}
