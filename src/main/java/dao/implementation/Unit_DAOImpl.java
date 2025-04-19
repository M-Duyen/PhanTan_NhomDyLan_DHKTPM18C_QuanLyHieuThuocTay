package dao.implementation;

import entity.*;
import jakarta.persistence.EntityManager;
import net.datafaker.Faker;


public class Unit_DAOImpl extends GenericDAOImpl<Unit, String>{
    public Unit_DAOImpl(Class<Unit> clazz) {
        super(clazz);
    }

    public Unit_DAOImpl(EntityManager em, Class<Unit> clazz) {
        super(em, clazz);
    }

    public static Unit createSapmleUnit() {
        Faker faker = new Faker();
        Unit unit = new Unit();
        unit.setUnitID("U" + faker.number().digits(3));
        unit.setUnitName(faker.company().name());
        unit.setDescription(faker.company().name());
        return unit;
    }


    public static void main(String[] args) {
        Unit_DAOImpl dao = new Unit_DAOImpl(Unit.class);
        Unit unit = dao.createSapmleUnit();
        System.out.println(dao.create(unit));

        dao.getAll().forEach(System.out::println);
        //System.out.println(dao.updateUnit(unit));
        //System.out.println(dao.deleteUnit(unit));
    }
}