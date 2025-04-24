package dao;


import model.PromotionType;
import jakarta.persistence.EntityManager;

public class PromotionTypeDAO extends GenericDAO<PromotionType, String> {


    public PromotionTypeDAO(Class<PromotionType> clazz) {
        super(clazz);
    }

    public PromotionTypeDAO(EntityManager em, Class<PromotionType> clazz) {
        super(em, clazz);
    }

    public static void main(String[] args) {
        PromotionTypeDAO dao = new PromotionTypeDAO(PromotionType.class);
        dao.getAll().forEach(System.out::println);

    }
}