package service.impl;

import dao.CustomerDAO;
import dao.GenericDAO;
import model.Customer;
import service.CustomerService;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

public class CustomerServiceImpl extends GenericServiceImpl<Customer, String> implements CustomerService {
    private CustomerDAO customerDAO;

    public CustomerServiceImpl(CustomerDAO customerDAO) throws RemoteException {
        super(customerDAO);
        this.customerDAO = customerDAO;
    }


    @Override
    public boolean checkPhoneNumber(String phone) throws RemoteException {
        return customerDAO.checkPhoneNumber(phone);
    }

    @Override
    public String createCustomerID() throws RemoteException {
        return customerDAO.createCustomerID();
    }

    @Override
    public Customer getCustomerByPhone(String phone) throws RemoteException {
        return customerDAO.getCustomerByPhone(phone);
    }

    @Override
    public double getCustomerPoint(String phone) throws RemoteException {
        return customerDAO.getCustomerPoint(phone);
    }

    @Override
    public boolean updateCustPoint_Decrease(String phone, double point) throws RemoteException {
        return customerDAO.updateCustPoint_Decrease(phone, point);
    }

    @Override
    public boolean updateCustPoint_Increase(String phone, double point) throws RemoteException {
        return customerDAO.updateCustPoint_Increase(phone, point);
    }

    @Override
    public Map<String, Customer> getAllCustomersAsMap() throws RemoteException {
        return customerDAO.getAllCustomersAsMap();
    }
}
