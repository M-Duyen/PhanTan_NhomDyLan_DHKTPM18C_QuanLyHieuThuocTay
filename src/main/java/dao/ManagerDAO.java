package dao;

import model.Manager;
import jakarta.persistence.EntityManager;
import service.GenericService;

public class ManagerDAO extends GenericDAO<Manager, String> implements GenericService<Manager, String> {
    public ManagerDAO(Class<Manager> clazz) {
        super(clazz);
    }

    public ManagerDAO(EntityManager em, Class<Manager> clazz) {
        super(em, clazz);
    }


}
