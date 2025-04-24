package dao;

import model.FunctionalFood;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.List;

public class FunctionalFoodDAO extends GenericDAO<FunctionalFoodDAO, String>{

    public FunctionalFoodDAO(Class<FunctionalFoodDAO> clazz) {
        super(clazz);
    }

    public FunctionalFoodDAO(EntityManager em, Class<FunctionalFoodDAO> clazz) {
        super(em, clazz);
    }
}
