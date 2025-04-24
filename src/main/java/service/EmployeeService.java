package service;

import model.Employee;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

public interface EmployeeService extends GenericService<Employee, String> {
    String createEmployeeID(String phone) throws RemoteException;
    Employee getListEmployeeByAccountID(String username) throws RemoteException;
    Map<String, Employee> getAllEmployeesAsMap() throws RemoteException;
}
