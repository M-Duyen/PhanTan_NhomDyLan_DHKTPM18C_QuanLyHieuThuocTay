package service;

import model.Employee;

import java.rmi.RemoteException;

public interface EmployeeService extends GenericService<Employee, String> {
    String createEmployeeID(String phone) throws RemoteException;
}
