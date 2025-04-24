package service.impl;

import dao.GenericDAO;
import org.springframework.web.bind.annotation.RequestParam;
import service.GenericService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

public abstract class GenericServiceImpl<T, ID> extends UnicastRemoteObject implements GenericService<T, ID>{

    protected GenericDAO<T, ID> genericDAO;

    public GenericServiceImpl(GenericDAO<T, ID> genericDAO) throws RemoteException {
        this.genericDAO = genericDAO;
    }

    @Override
    public List<T> getAll() {
        return genericDAO.getAll();
    }

    @Override
    public boolean create(T t) {
        return genericDAO.create(t);
    }

    @Override
    public T findById(ID id) {
        return genericDAO.findById(id);
    }

    @Override
    public boolean update(T t) {
        return genericDAO.update(t);
    }

    @Override
    public boolean delete(ID id) {
        return genericDAO.delete(id);
    }

    @Override
    public List<?> searchByMultipleCriteria(@RequestParam String entityName, @RequestParam String keyword) {
        return genericDAO.searchByMultipleCriteria(entityName, keyword);
    }
}
