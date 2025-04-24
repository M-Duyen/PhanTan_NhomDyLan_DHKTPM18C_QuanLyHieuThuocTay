package service.impl;

import dao.EmployeeDAO;
import dao.GenericDAO;
import entity.Employee;
import service.EmployeeService;

import java.rmi.RemoteException;

public class EmployeeServiceImpl extends GenericServiceImpl<Employee, String> implements EmployeeService {
    private EmployeeDAO employeeDAO;
    public EmployeeServiceImpl(GenericDAO<Employee, String> genericDAO) throws RemoteException {
        super(genericDAO);
    }

    @Override
    public String createEmployeeID(String phone) {
        return "";
    }
}
