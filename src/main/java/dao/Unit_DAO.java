package dao;

import entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import net.datafaker.Faker;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Unit_DAO {
    private final EntityManager em;

    public Unit_DAO() {
        em = Persistence.createEntityManagerFactory("SHH-mariaDB").createEntityManager();
    }

    public List getAll() {
        return em.createQuery("select u from Unit u").getResultList();
    }

    public boolean insertUnit(Unit unit) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            em.persist(unit);
            tr.commit();
            return true;
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public static Unit createSapmleUnit() {
        Faker faker = new Faker();
        Unit unit = new Unit();
        unit.setUnitID("U" + faker.number().digits(3));
        unit.setUnitName(faker.company().name());
        unit.setDescription(faker.company().name());
        return unit;
    }

    public boolean updateUnit(Unit unit) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            em.merge(unit);
            tr.commit();
            return true;
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteUnit(Unit unit) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            em.remove(unit);
            tr.commit();
            return true;
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        Unit_DAO dao = new Unit_DAO();
        Unit unit = dao.createSapmleUnit();
        System.out.println(dao.insertUnit(unit));

        dao.getAll().forEach(System.out::println);
        //System.out.println(dao.updateUnit(unit));
        //System.out.println(dao.deleteUnit(unit));
    }
}