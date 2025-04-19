package dao;

import service.GenericService;
import jakarta.persistence.EntityManager;

import java.util.List;

public abstract class AbstractCRUD <T, ID> implements GenericService<T, ID> {
    protected EntityManager em;
    private final Class<T> entityClass;

    public AbstractCRUD(EntityManager em, Class<T> entityClass) {
        this.em = em;
        this.entityClass = entityClass;
    }

    @Override
    public List<T> getAll(){
        return em.createQuery("FROM " + entityClass.getName(), entityClass).getResultList();
    }

    @Override
    public boolean create(T t){
        try {
            em.getTransaction().begin();
            em.persist(t);
            em.getTransaction().commit();
            return true;
        } catch (Exception e){
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public T findById(ID id){
        return em.find(entityClass, id);
    }

    @Override
    public boolean update(T t){
        try {
            em.getTransaction().begin();
            em.merge(t);
            em.getTransaction().commit();
            return true;
        } catch (Exception e){
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(ID id){
        try {
            T t = findById(id);
            em.getTransaction().begin();
            em.remove(t);
            em.getTransaction().commit();
            return true;
        } catch (Exception e){
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
}
