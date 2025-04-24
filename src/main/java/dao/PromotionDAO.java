package dao;

import entity.Promotion;
import jakarta.persistence.EntityManager;

public class PromotionDAO extends GenericDAO<Promotion, String> {
    public PromotionDAO(EntityManager em, Class<Promotion> entityClass) {
        super(em, entityClass);
    }
}