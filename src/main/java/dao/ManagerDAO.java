package dao;

import entity.Manager;
import jakarta.persistence.EntityManager;

public class ManagerDAO extends GenericDAO<Manager, String> {
    public ManagerDAO(Class<Manager> clazz) {
        super(clazz);
    }

    public ManagerDAO(EntityManager em, Class<Manager> clazz) {
        super(em, clazz);
    }
}
