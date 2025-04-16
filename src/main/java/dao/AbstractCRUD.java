package dao;

import jakarta.persistence.EntityManager;

import java.util.List;

public abstract class AbstractCRUD <T, ID> implements CRUD<T, ID> {
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
    public T create(T t){
        try {
            em.getTransaction().begin();
            em.persist(t);
            em.getTransaction().commit();
            return t;
        } catch (Exception e){
            em.getTransaction().rollback();
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public T read(ID id){
        return em.find(entityClass, id);
    }

    @Override
    public T update(T t){
        try {
            em.getTransaction().begin();
            em.merge(t);
            em.getTransaction().commit();
            return t;
        } catch (Exception e){
            em.getTransaction().rollback();
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean delete(ID id){
        try {
            T t = read(id);
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
