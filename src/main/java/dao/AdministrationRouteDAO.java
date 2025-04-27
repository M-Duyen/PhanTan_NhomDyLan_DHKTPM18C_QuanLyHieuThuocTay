package dao;

import jakarta.persistence.EntityManager;
import model.AdministrationRoute;
import utils.JPAUtil;

public class AdministrationRouteDAO extends GenericDAO<AdministrationRoute, String> implements service.AdministrationRouteService {

    public AdministrationRouteDAO(Class<AdministrationRoute> clazz) {
        super(clazz);

    }

    public AdministrationRouteDAO(EntityManager em, Class<AdministrationRoute> clazz) {
        super(em, clazz);
    }
}
