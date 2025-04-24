package dao;

import model.MedicalSupply;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import net.datafaker.Faker;

import java.time.LocalDateTime;
import java.util.List;

public class MedicalSuppliesDAO extends GenericDAO<MedicalSupply, String> implements service.MedicalSuppliesService {

    public MedicalSuppliesDAO(Class<MedicalSupply> clazz) {
        super(clazz);
    }

    public MedicalSuppliesDAO(EntityManager em, Class<MedicalSupply> clazz) {
        super(em, clazz);
    }
}
