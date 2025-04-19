package dao;

import entity.AdministrationRoute;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import java.util.List;


public class AdministrationRoute_DAO {
    private EntityManager em;

    public AdministrationRoute_DAO() {
        em = Persistence.createEntityManagerFactory("mariadb").createEntityManager();
    }





}
