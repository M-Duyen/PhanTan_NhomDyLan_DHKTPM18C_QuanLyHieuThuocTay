package dao;

import model.FunctionalFood;
import jakarta.persistence.EntityManager;
import service.FunctionalFoodService;
import utils.JPAUtil;

public class FunctionalFoodDAO extends GenericDAO<FunctionalFood, String> implements FunctionalFoodService {

    public FunctionalFoodDAO(Class<FunctionalFood> clazz) {
        super(clazz);
        this.em = JPAUtil.getEntityManager();

    }

    public FunctionalFoodDAO(EntityManager em, Class<FunctionalFood> clazz) {
        super(em, clazz);
    }
}
