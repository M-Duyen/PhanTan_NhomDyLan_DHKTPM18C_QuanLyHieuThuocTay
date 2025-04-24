package dao;

import model.FunctionalFood;
import jakarta.persistence.EntityManager;
import service.FunctionalFoodService;

public class FunctionalFoodDAO extends GenericDAO<FunctionalFood, String> implements FunctionalFoodService {

    public FunctionalFoodDAO(Class<FunctionalFood> clazz) {
        super(clazz);
    }

    public FunctionalFoodDAO(EntityManager em, Class<FunctionalFood> clazz) {
        super(em, clazz);
    }
}
