package dao;

import entity.Manager;
import jakarta.persistence.EntityManager;

public class Manager_DAO extends AbstractCRUD<Manager, String> {
    public Manager_DAO(EntityManager em) {
        super(em, Manager.class);
    }
}