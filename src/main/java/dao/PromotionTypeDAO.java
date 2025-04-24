package dao;


import model.PromotionType;
import jakarta.persistence.EntityManager;
import service.GenericService;

public class PromotionTypeDAO extends GenericDAO<PromotionType, String> implements GenericService<PromotionType, String> {


    public PromotionTypeDAO(Class<PromotionType> clazz) {
        super(clazz);
    }

    public PromotionTypeDAO(EntityManager em, Class<PromotionType> clazz) {
        super(em, clazz);
    }


}