package rmi;

import dao.CustomerDAO;
import model.Customer;
import service.CustomerService;
import service.impl.CustomerServiceImpl;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.rmi.registry.LocateRegistry;

public class RMISever {
    public static void main(String[] args) throws Exception{
        Context context = new InitialContext();
        LocateRegistry.createRegistry(7281);
        //Tạo Java Object
        CustomerDAO customerDAO = new CustomerDAO(Customer.class);


        //Tạo Java Remote Object
        CustomerService customerService = new CustomerServiceImpl(customerDAO);

        //bind
        context.bind("rmi://localhost:7281/customerService", customerService);
        System.out.println("Server Started!");
    }
}
