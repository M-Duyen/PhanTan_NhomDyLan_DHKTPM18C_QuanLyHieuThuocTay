package dao;

import dao.implementation.GenericDAOImpl;
import entity.Manager;
import jakarta.persistence.EntityManager;

public class Manager_DAO extends GenericDAOImpl<Manager, String> {
    public Manager_DAO(Class<Manager> clazz) {
        super(clazz);
    }

    public Manager_DAO(EntityManager em, Class<Manager> clazz) {
        super(em, clazz);
    }
}