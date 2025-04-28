package dao;

import jakarta.persistence.EntityTransaction;
import model.Customer;
import jakarta.persistence.EntityManager;
import net.datafaker.Faker;
import service.CustomerService;
import utils.JPAUtil;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerDAO extends GenericDAO<Customer, String> implements CustomerService {
    public CustomerDAO(Class<Customer> clazz) {
        super(clazz);
    }

    public CustomerDAO(EntityManager em, Class<Customer> clazz) {
        super(em, clazz);
    }

    public static Customer createSampleCustomer(Faker faker) {
        Customer customer = new Customer();
        customer.setPhoneNumber(faker.phoneNumber().cellPhone().substring(0, 10));
        customer.setCustomerID("CM" + faker.number().digits(3));
        return customer;
    }

    /**
     * Kiểm tra tồn tại của số điện thoại
     *
     * @param phone
     * @return
     */
    @Override
    public boolean checkPhoneNumber(String phone) {
        String jpql = "SELECT COUNT(c) FROM Customer c WHERE c.phoneNumber = :phone";
        Long count = em.createQuery(jpql, Long.class)
                .setParameter("phone", phone)
                .getSingleResult();
        return count > 0;
    }

    /**
     * Tạo mã khách hàng tự động
     *
     * @return
     */
    @Override
    public String createCustomerID() {
        String prefix = "C";
        String date = new java.text.SimpleDateFormat("ddMMyy").format(new java.util.Date());
        String likePattern = prefix + date + "%";

        String jpql = "SELECT MAX(CAST(SUBSTRING(c.customerID, 8, 3) AS integer)) " +
                "FROM Customer c WHERE c.customerID LIKE :pattern";

        Integer max = em.createQuery(jpql, Integer.class)
                .setParameter("pattern", likePattern)
                .getSingleResult();

        int currentMax = (max != null) ? max : 0;
        int nextCustomerID = currentMax + 1;

        return prefix + date + String.format("%03d", nextCustomerID);
    }

    /**
     * Lọc khách hàng theo số điện thoại
     *
     * @param phone
     * @return
     */
    @Override
    public Customer getCustomerByPhone(String phone) {
        String query = "SELECT c FROM Customer c WHERE c.phoneNumber = :phone";
        List<Customer> customer = em.createQuery(query, Customer.class)
                .setParameter("phone", phone)
                .getResultList();
        return customer.isEmpty() ? null : customer.get(0);
    }


    /**
     * Lấy điểm tích lũy của khách hàng
     *
     * @param phone
     * @return
     */
    @Override
    public double getCustomerPoint(String phone) {
        String jpql = "SELECT c.point FROM Customer c WHERE c.phoneNumber = :phone";
        Double point = em.createQuery(jpql, Double.class)
                .setParameter("phone", phone)
                .getSingleResult();
        return (point != null) ? point : 0;
    }


    /**
     * Giảm điểm tích lũy khi đổi điểm (có transaction)
     *
     * @param phone
     * @param point
     * @return
     */
    @Override
    public boolean updateCustPoint_Decrease(String phone, double point) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String jpql = "SELECT c FROM Customer c WHERE c.phoneNumber = :phone";
            Customer cus = em.createQuery(jpql, Customer.class)
                    .setParameter("phone", phone)
                    .getSingleResult();
            cus.setPoint(cus.getPoint() - point);
            em.merge(cus);
            tr.commit();
            return true;
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            e.printStackTrace();
        }
        return false;
    }


    /**
     * Tăng điểm tích lũy khi mua hàng (có transaction)
     *
     * @param phone
     * @param point
     * @return
     */
    @Override
    public boolean updateCustPoint_Increase(String phone, double point) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String jpql = "SELECT c FROM Customer c WHERE c.phoneNumber = :phone";
            Customer cus = em.createQuery(jpql, Customer.class)
                    .setParameter("phone", phone)
                    .getSingleResult();
            cus.setPoint(cus.getPoint() + point);
            em.merge(cus);
            tr.commit();
            return true;
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Lưu danh sách customer vào map
     *
     * @return
     */
    @Override
    public Map<String, Customer> getAllCustomersAsMap() {
        String jpql = "SELECT c FROM Customer c";
        Map<String, Customer> customerMap = new HashMap<>();

        em.createQuery(jpql, Customer.class)
                .getResultList()
                .forEach(c -> customerMap.put(c.getPhoneNumber(), c));

        return customerMap;
    }


    public static void main(String[] args) {
        CustomerDAO dao = new CustomerDAO(Customer.class);
        Customer customer = new Customer();
        customer.setCustomerID(dao.createCustomerID());
        customer.setCustomerName("Test Customer");
        customer.setAddr("Test Address");
        customer.setPhoneNumber("1234567890");
        customer.setBrithDate(LocalDate.now());
        customer.setGender(true);

//        dao.create(customer);


//        dao.getAll().forEach(cus -> System.out.println(cus.getCustomerID() + ": " + cus.getCustomerName()));
//        dao.updateCustPoint_Increase("1234567890",10);
//        System.out.println(dao.findById("1234567890"));
//        System.out.println(dao.getCustomerPoint("1234567890"));
        dao.getAll().forEach(x-> System.out.println(x));
//            dao.getAllCustomersAsMap().forEach((k, v) -> {
//                System.out.println(k +": "+v);
//            });
//            dao.delete("1234567890");
    }
}
