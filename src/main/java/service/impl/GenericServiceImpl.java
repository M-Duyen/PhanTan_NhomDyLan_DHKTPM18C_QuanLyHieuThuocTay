package service.impl;

import dao.GenericDAO;
import dao.PromotionDAO;
import org.springframework.web.bind.annotation.RequestParam;
import service.GenericService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public abstract class GenericServiceImpl<T, ID> extends UnicastRemoteObject implements GenericService<T, ID>{

    protected GenericDAO<T, ID> genericDAO;


    public GenericServiceImpl(GenericDAO<T,ID> genericDAO) throws RemoteException {
        this.genericDAO = genericDAO;
    }

    @Override
    public synchronized List<T> getAll() throws RemoteException {
        return genericDAO.getAll();
    }

    @Override
    public synchronized boolean create(T t) throws RemoteException {
        return genericDAO.create(t);
    }

    @Override
    public synchronized T findById(ID id) throws RemoteException {
        return genericDAO.findById(id);
    }

    @Override
    public synchronized boolean update(T t) throws RemoteException {
        return genericDAO.update(t);
    }

    @Override
    public synchronized boolean delete(ID id) throws RemoteException {
        return genericDAO.delete(id);
    }

    @Override
    public List<?> searchByMultipleCriteria(@RequestParam String entityName, @RequestParam String keyword) throws RemoteException {
        return genericDAO.searchByMultipleCriteria(entityName, keyword);
    }
}
