package dao;

import model.FunctionalFood;
import jakarta.persistence.EntityManager;
import service.FunctionalFoodService;

public class FunctionalFoodDAO extends GenericDAO<FunctionalFoodDAO, String>{

    public FunctionalFoodDAO(Class<FunctionalFoodDAO> clazz) {
        super(clazz);
    }

    public FunctionalFoodDAO(EntityManager em, Class<FunctionalFoodDAO> clazz) {
        super(em, clazz);
    }
}
