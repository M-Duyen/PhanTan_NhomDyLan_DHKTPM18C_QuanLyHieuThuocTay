package dao;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import model.AdministrationRoute;
import utils.JPAUtil;

public class AdministrationRouteDAO extends GenericDAO<AdministrationRoute, String> implements service.AdministrationRouteService {

    private EntityManager em;
    public AdministrationRouteDAO(Class<AdministrationRoute> clazz) {
        super(clazz);
        this.em = JPAUtil.getEntityManager();
    }

    public AdministrationRouteDAO(EntityManager em, Class<AdministrationRoute> clazz) {
        super(em, clazz);
    }
}
