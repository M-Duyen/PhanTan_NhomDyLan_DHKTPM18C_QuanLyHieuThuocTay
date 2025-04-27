package dao;


import jakarta.persistence.EntityManager;
import model.PromotionType;
import service.PromotionTypeService;
import utils.JPAUtil;

public class PromotionTypeDAO extends GenericDAO<PromotionType, String> implements PromotionTypeService {

    public PromotionTypeDAO(Class<PromotionType> clazz) {
        super(clazz);
        this.em = JPAUtil.getEntityManager();

    }

    public PromotionTypeDAO(EntityManager em, Class<PromotionType> clazz) {
        super(em, clazz);
    }


}