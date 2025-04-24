package dao;


import model.PromotionType;
import jakarta.persistence.EntityManager;
import service.PromotionTypeService;

public class PromotionTypeDAO extends GenericDAO<PromotionType, String> implements PromotionTypeService {

    public PromotionTypeDAO(Class<PromotionType> clazz) {
        super(clazz);
    }

    public PromotionTypeDAO(EntityManager em, Class<PromotionType> clazz) {
        super(em, clazz);
    }

}