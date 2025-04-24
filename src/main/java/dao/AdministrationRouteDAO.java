package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import model.AdministrationRoute;

import java.util.ArrayList;


public class AdministrationRouteDAO extends GenericDAO<AdministrationRoute, String> implements service.AdministrationRouteService {

    public AdministrationRouteDAO(Class<AdministrationRoute> clazz) {
        super(clazz);
    }

    public AdministrationRouteDAO(EntityManager em, Class<AdministrationRoute> clazz) {
        super(em, clazz);
    }
}
