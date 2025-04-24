package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;


public class AdministrationRouteDAO {
    private EntityManager em;

    public AdministrationRouteDAO() {
        em = Persistence.createEntityManagerFactory("mariadb").createEntityManager();
    }





}
