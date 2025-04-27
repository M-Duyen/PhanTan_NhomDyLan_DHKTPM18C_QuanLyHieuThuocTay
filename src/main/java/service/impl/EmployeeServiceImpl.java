package service.impl;

import dao.EmployeeDAO;
import dao.GenericDAO;
import model.Employee;
import service.EmployeeService;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

public class EmployeeServiceImpl extends GenericServiceImpl<Employee, String> implements EmployeeService {
    private EmployeeDAO employeeDAO;

    public EmployeeServiceImpl(EmployeeDAO employeeDAO) throws RemoteException {
        super(employeeDAO);
        this.employeeDAO = employeeDAO;
    }

    @Override
    public String createEmployeeID(String phone) throws RemoteException {
        return employeeDAO.createEmployeeID(phone);
    }

    @Override
    public Employee getListEmployeeByAccountID(String username) throws RemoteException {
        return employeeDAO.getListEmployeeByAccountID(username);
    }

    @Override
    public Map<String, Employee> getAllEmployeesAsMap() throws RemoteException {
        return employeeDAO.getAllEmployeesAsMap();
    }
}
