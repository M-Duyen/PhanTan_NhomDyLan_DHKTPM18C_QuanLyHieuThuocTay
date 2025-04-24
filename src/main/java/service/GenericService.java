package service;

import org.springframework.web.bind.annotation.RequestParam;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface GenericService<T, ID> extends Remote {
    List<T> getAll() throws RemoteException;
    boolean create(T t) throws RemoteException;
    T findById(ID id) throws RemoteException;
    boolean update(T t) throws RemoteException;
    boolean delete(ID id) throws RemoteException;
    List<?> searchByMultipleCriteria(@RequestParam String entityName, @RequestParam String keyword) throws RemoteException;
}
