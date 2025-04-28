package rmi;

import dao.*;
import model.*;
import service.*;
import service.impl.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.rmi.registry.LocateRegistry;

public class RMISever {
    public static void main(String[] args) throws Exception{
        Context context = new InitialContext();
        LocateRegistry.createRegistry(7281);
        //Tạo Java Object
        CustomerDAO customerDAO = new CustomerDAO(Customer.class);
        AccountDAO accountDAO = new AccountDAO(Account.class);
        AdministrationRouteDAO administrationrouteDAO = new AdministrationRouteDAO(AdministrationRoute.class);
        CategoryDAO categoryDAO = new CategoryDAO(Category.class);
        EmployeeDAO employeeDAO = new EmployeeDAO(Employee.class);
        FunctionalFoodDAO foodDAO = new FunctionalFoodDAO(FunctionalFood.class);
        ManagerDAO managerDAO = new ManagerDAO(Manager.class);
        MedicalSuppliesDAO medicalSuppliesDAO = new MedicalSuppliesDAO(MedicalSupply.class);
        MedicineDAO medicineDAO = new MedicineDAO(Medicine.class);
        OrderDAO orderDAO = new OrderDAO(Order.class);
        OrderDetailDAO orderDetailDAO = new OrderDetailDAO(OrderDetail.class);
        PrescriptionDAO prescriptionDAO = new PrescriptionDAO(Prescription.class);
        ProductDAO productDAO = new ProductDAO(Product.class);
        PromotionDAO promotionDAO = new PromotionDAO(Promotion.class);
        PromotionTypeDAO promotionTypeDAO = new PromotionTypeDAO(PromotionType.class);
        VendorDAO vendorDAO = new VendorDAO(Vendor.class);

        //Tạo Java Remote Object
        CustomerService customerService = new CustomerServiceImpl(customerDAO);
        AccountService accountService = new AccountServiceImpl(accountDAO);
        AdministrationRouteService administrationRouteService = new AdministrationRouteServiceImpl(administrationrouteDAO);
        CategoryService categoryService = new CategoryServiceImpl(categoryDAO);
        EmployeeService employeeService = new EmployeeServiceImpl(employeeDAO);
        FunctionalFoodService functionalFoodService = new FunctionalFoodServiceImpl(foodDAO);
        ManagerService managerService = new ManagerServiceImpl(managerDAO);
        MedicalSuppliesService medicalSuppliesService = new MedicalSupplyServiceImpl(medicalSuppliesDAO);
        MedicineService medicineService = new MedicineServiceImpl(medicineDAO);
        OrderService orderService = new OrderServiceImpl(orderDAO);
        OrderDetailService orderDetailService = new OrderDetailServiceImpl(orderDetailDAO);
        PrescriptionService prescriptionService = new PrescriptionServiceImpl(prescriptionDAO);
        ProductService productService = new ProductServiceImpl(productDAO);
        PromotionService promotionService = new PromotionServiceImpl(promotionDAO);
        PromotionTypeService promotionTypeService = new PromotionTypeServiceImpl(promotionTypeDAO);
        VendorService vendorService = new VendorServiceImpl(vendorDAO);
        ServerService serverService = new ServerServiceImpl();

        //bind
        context.bind("rmi://DESKTOP-6PMIT8Q:7281/customerService", customerService);
        context.bind("rmi://DESKTOP-6PMIT8Q:7281/accountService", accountService);
        context.bind("rmi://DESKTOP-6PMIT8Q:7281/administrationRouteService", administrationRouteService);
        context.bind("rmi://DESKTOP-6PMIT8Q:7281/categoryService", categoryService);
        context.bind("rmi://DESKTOP-6PMIT8Q:7281/employeeService", employeeService);
        context.bind("rmi://DESKTOP-6PMIT8Q:7281/functionalFoodService", functionalFoodService);
        context.bind("rmi://DESKTOP-6PMIT8Q:7281/managerService", managerService);
        context.bind("rmi://DESKTOP-6PMIT8Q:7281/medicalSuppliesService", medicalSuppliesService);
        context.bind("rmi://DESKTOP-6PMIT8Q:7281/medicineService", medicineService);
        context.bind("rmi://DESKTOP-6PMIT8Q:7281/orderService", orderService);
        context.bind("rmi://DESKTOP-6PMIT8Q:7281/orderDetailService", orderDetailService);
        context.bind("rmi://DESKTOP-6PMIT8Q:7281/prescriptionService", prescriptionService);
        context.bind("rmi://DESKTOP-6PMIT8Q:7281/productService", productService);
        context.bind("rmi://DESKTOP-6PMIT8Q:7281/promotionService", promotionService);
        context.bind("rmi://DESKTOP-6PMIT8Q:7281/promotionTypeService", promotionTypeService);
        context.bind("rmi://DESKTOP-6PMIT8Q:7281/vendorService", vendorService);
        context.bind("rmi://DESKTOP-6PMIT8Q:7281/serverService", serverService);

        accountDAO.outAllAccount();
        System.out.println("Server Started!");
    }
}
