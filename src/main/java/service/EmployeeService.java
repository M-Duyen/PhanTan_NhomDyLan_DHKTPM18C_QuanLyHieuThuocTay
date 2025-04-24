package service;

import entity.Employee;

import java.rmi.RemoteException;

public interface EmployeeService extends GenericService<Employee, String> {
    String createEmployeeID(String phone) throws RemoteException;
}
