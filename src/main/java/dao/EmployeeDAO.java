package dao;

import model.Employee;
import jakarta.persistence.EntityManager;
import net.datafaker.Faker;
import service.EmployeeService;
import utils.JPAUtil;

import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Collectors;

public class EmployeeDAO extends GenericDAO<Employee, String> implements EmployeeService {
    public EmployeeDAO(Class<Employee> clazz) {
        super(clazz);
        this.em = JPAUtil.getEntityManager();
    }

    public EmployeeDAO(EntityManager em, Class<Employee> clazz) {
        super(em, clazz);
    }

    /**
     *
     * @param username
     * @return
     */
    @Override
    public Employee getListEmployeeByAccountID(String username) {
        String query = "SELECT e FROM Employee e WHERE e.account.accountID =:username";
        return em.createQuery(query, Employee.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    /**
     * Tạo mã nhân viên tự động
     *
     * @param phone
     * @return
     * @throws SQLException
     */
    @Override
    public String createEmployeeID(String phone) {
        String prefix = "EP";
        String endphone = phone.substring(phone.length() - 2);
        String likePattern = prefix + endphone + "%";

        // Truy vấn tìm giá trị lớn nhất của 2 số cuối sau prefix + endphone
        String jpql = "SELECT MAX(CAST(SUBSTRING(e.employeeID, 5, 2) AS integer)) FROM Employee e WHERE e.employeeID LIKE :pattern";

        Integer max = em.createQuery(jpql, Integer.class)
                .setParameter("pattern", likePattern)
                .getSingleResult();

        int currentMax = (max != null) ? max : 0;
        int nextEmployeeID = currentMax + 1;

        String newEmployeeID = prefix + endphone + String.format("%02d", nextEmployeeID);
        return newEmployeeID;
    }

    /**
     * Lưu danh sách Emp vào map
     *
     * @return
     */
    @Override
    public Map<String, Employee> getAllEmployeesAsMap() {
        String query = "SELECT e.employeeID, e FROM Employee e ";
        return em.createQuery(query, Object[].class)
                .getResultList()
                .stream()
                .collect(Collectors.toMap(
                        record -> (String) record[0],
                        record -> (Employee) record[1]
                ));
    }

}
