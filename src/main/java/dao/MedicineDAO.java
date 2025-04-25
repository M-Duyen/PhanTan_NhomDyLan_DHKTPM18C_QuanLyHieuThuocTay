package dao;

import jakarta.persistence.EntityManager;
import model.Medicine;

public class MedicineDAO extends GenericDAO<Medicine, String > {

    public MedicineDAO(Class<Medicine> clazz) {
        super(clazz);
    }

    public MedicineDAO(EntityManager em, Class<Medicine> clazz) {
        super(em, clazz);
    }
}
