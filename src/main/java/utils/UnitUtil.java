package utils;

//import entity.Unit;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import net.datafaker.Faker;

import java.util.List;

public class UnitUtil {
    //TODO:
//    private final EntityManager em;
//
//    public UnitUtil() {
//        em = Persistence.createEntityManagerFactory("SHH-mariaDB").createEntityManager();
//    }
//
//    public List getAll() {
//        return em.createQuery("select u from Unit u").getResultList();
//    }
//
//    public boolean insertUnit(Unit unit) {
//        EntityTransaction tr = em.getTransaction();
//        try {
//            tr.begin();
//            em.persist(unit);
//            tr.commit();
//            return true;
//        } catch (Exception e) {
//            tr.rollback();
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    public static Unit createSapmleUnit() {
//        Faker faker = new Faker();
//        Unit unit = new Unit();
//        unit.setUnitID("U" + faker.number().digits(3));
//        unit.setUnitName(faker.company().name());
//        unit.setDescription(faker.company().name());
//        return unit;
//    }
//
//    public static void main(String[] args) {
//        UnitUtil util = new UnitUtil();
//        Unit unit = util.createSapmleUnit();
//        System.out.println(util.insertUnit(unit));
//
//        util.getAll().forEach(System.out::println);
//    }
}
