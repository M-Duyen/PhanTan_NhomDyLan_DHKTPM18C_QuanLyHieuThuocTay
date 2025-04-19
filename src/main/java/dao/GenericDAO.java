package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import utils.JPAUtil;

import java.util.List;

public abstract class GenericDAO<T, ID> {
    protected EntityManager em;
    private Class<T> clazz;

    public GenericDAO(Class<T> clazz) {
        this.clazz = clazz;
        this.em = JPAUtil.getEntityManager();
    }

    public GenericDAO(EntityManager em, Class<T> clazz) {
        this.em = em;
        this.clazz = clazz;
    }

    public List<T> getAll() {
        return List.of();
    }

    public boolean create(T t) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(t);
            tx.commit();
            return true;
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public T findById(ID id) {
        return em.find(clazz, id);
    }

    public boolean update(T t) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(t);
            tx.commit();
            return true;
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(ID id) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            T t = em.find(clazz, id);
            if (t != null) {
                em.remove(t);
                tx.commit();
                return true;
            }
        } catch (Exception e) {
            if(tx.isActive()) {
                tx.rollback();
                e.printStackTrace();
            }
        }
        return false;
    }
}
