package dao;


import entity.Promotion;
import jakarta.persistence.EntityManager;

public class PromotionTypeDAO extends GenericDAO<Promotion, String> {


    public PromotionTypeDAO(Class<Promotion> clazz) {
        super(clazz);
    }

    public PromotionTypeDAO(EntityManager em, Class<Promotion> clazz) {
        super(em, clazz);
    }
}