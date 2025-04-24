package service.impl;

import dao.CustomerDAO;
import dao.GenericDAO;
import entity.Customer;
import service.CustomerService;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.util.Map;

public class CustomerServiceImpl extends GenericServiceImpl<Customer, String> implements CustomerService {
    private CustomerDAO customerDAO;

    public CustomerServiceImpl(GenericDAO<Customer, String> genericDAO) throws RemoteException {
        super(genericDAO);
    }


    @Override
    public boolean checkPhoneNumber(String phone) throws RemoteException {
        return false;
    }

    @Override
    public String createCustomerID() throws RemoteException {
        return "";
    }

    @Override
    public double getCustomerPoint(String phone) throws RemoteException {
        return 0;
    }

    @Override
    public boolean updateCustPoint_Decrease(String phone, double point) throws RemoteException {
        return false;
    }

    @Override
    public boolean updateCustPoint_Increase(String phone, double point) throws RemoteException {
        return false;
    }

    @Override
    public Map<String, Customer> getAllCustomersAsMap() throws RemoteException {
        return customerDAO.getAllCustomersAsMap();
    }
}
