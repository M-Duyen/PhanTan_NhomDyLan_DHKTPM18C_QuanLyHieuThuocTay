package dao;

import model.Manager;
import jakarta.persistence.EntityManager;

public class ManagerDAO extends GenericDAO<Manager, String> implements service.ManagerService {
    public ManagerDAO(Class<Manager> clazz) {
        super(clazz);
    }

    public ManagerDAO(EntityManager em, Class<Manager> clazz) {
        super(em, clazz);
    }


}
