package dao;

import model.Medicine;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import java.util.List;

public class MedicineDAO extends GenericDAO<Medicine, String> implements service.MedicineService {

    public MedicineDAO(Class<Medicine> clazz) {
        super(clazz);
    }

    public MedicineDAO(EntityManager em, Class<Medicine> clazz) {
        super(em, clazz);
    }
}
