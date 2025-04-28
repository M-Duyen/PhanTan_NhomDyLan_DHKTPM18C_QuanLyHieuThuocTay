package dao;

import jakarta.persistence.criteria.*;
import model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import model.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.GenericService;
import utils.JPAUtil;

import java.lang.reflect.Field;
import java.util.*;

public abstract class GenericDAO<T, ID> implements GenericService<T,ID> {
    protected EntityManager em;
    private Class<T> clazz;

    public GenericDAO(Class<T> clazz) {
        this.clazz = clazz;
        this.em = JPAUtil.getEntityManager();
    }

    public GenericDAO(EntityManager em, Class<T> clazz) {
        this.em = em;
        this.clazz = clazz;
    }


    public List<T> getAll() {
        String jpql = "SELECT t FROM " + clazz.getSimpleName() + " t";
        return em.createQuery(jpql, clazz).getResultList();
    }

    public boolean create(T t) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(t);
            tx.commit();
            return true;
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public T findById(ID id) {
        return em.find(clazz, id);
    }

    public boolean update(T t) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(t);
            tx.commit();
            return true;
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(ID id) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            T t = em.find(clazz, id);
            if (t != null) {
                em.remove(t);
                tx.commit();
                return true;
            }
        } catch (Exception e) {
            if(tx.isActive()) {
                tx.rollback();
                e.printStackTrace();
            }
        }
        return false;
    }
    public <T> List<T> search(Class<T> clazz, Map<String, Object> criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(clazz);
        Root<T> root = cq.from(clazz);

        List<Predicate> predicates = new ArrayList<>();

        criteria.forEach((key, value) -> {
            if (value != null) {
                Path<String> path;

                if (key.contains(".")) {
                    String[] parts = key.split("\\.");
                    Path<Object> joinPath = (Path<Object>) root;
                    for (int i = 0; i < parts.length - 1; i++) {
                        joinPath = ((From<?, ?>) joinPath).join(parts[i], JoinType.LEFT);
                    }
                    path = joinPath.get(parts[parts.length - 1]);
                } else {
                    path = root.get(key);
                }

                predicates.add(cb.like(cb.lower(path.as(String.class)), "%" + value.toString().toLowerCase() + "%"));
            }
        });

        cq.where(cb.or(predicates.toArray(new Predicate[0])));
        TypedQuery<T> query = em.createQuery(cq);
        return query.getResultList();
    }


    private Set<String> getAllFieldNames(Class<?> clazz) {
        Set<String> fieldNames = new HashSet<>();
        while (clazz != null && clazz != Object.class) {
            for (Field field : clazz.getDeclaredFields()) {
                fieldNames.add(field.getName().toLowerCase());
            }
            clazz = clazz.getSuperclass();
        }
        return fieldNames;
    }
    @GetMapping("/search-entity")
    public List<?> searchByMultipleCriteria(@RequestParam String entityName, @RequestParam String keyword) {

        Map<String, EntitySearchConfig> entitySearchConfigMap = new HashMap<>();
        entitySearchConfigMap.put("product", new EntitySearchConfig(Product.class, Arrays.asList("productID", "productName", "registrationNumber", "endDate")));
        entitySearchConfigMap.put("vendor", new EntitySearchConfig(Vendor.class, Arrays.asList("vendorID", "country", "vendorName")));
        entitySearchConfigMap.put("administration", new EntitySearchConfig(AdministrationRoute.class, Arrays.asList("administrationRouteID","administrationRouteName")));
        entitySearchConfigMap.put("category", new EntitySearchConfig(Category.class, Arrays.asList("categoryName")));
        entitySearchConfigMap.put("customer", new EntitySearchConfig(Customer.class, Arrays.asList("customerID", "customerName", "phoneNumber", "email", "addr")));
        entitySearchConfigMap.put("employee", new EntitySearchConfig(Employee.class, Arrays.asList("employeeID", "employeeName", "phoneNumber", "email", "address", "status", "degree")));
        entitySearchConfigMap.put("functionalfood", new EntitySearchConfig(FunctionalFood.class, Arrays.asList("mainNutrients", "supplementaryIngredients")));
        entitySearchConfigMap.put("medicalsupply", new EntitySearchConfig(MedicalSupply.class, Arrays.asList("medicalSupplyType")));
        entitySearchConfigMap.put("medicine", new EntitySearchConfig(Medicine.class, Arrays.asList("activeIngredient", "conversionUnit")));
        entitySearchConfigMap.put("promotiontype", new EntitySearchConfig(PromotionType.class, Arrays.asList("promotionTypeName")));
        entitySearchConfigMap.put("promotion", new EntitySearchConfig(Promotion.class, Arrays.asList("status","promotionType.promotionTypeName")));
        entitySearchConfigMap.put("order", new EntitySearchConfig(Order.class, Arrays.asList("orderDate", "shipToAddress")));
        entitySearchConfigMap.put("orderdetail", new EntitySearchConfig(OrderDetail.class, Arrays.asList("order.orderID")));

        EntitySearchConfig config = entitySearchConfigMap.get(entityName.toLowerCase());

        if (config == null) {
            throw new IllegalArgumentException("Không tìm thấy thực thể: " + entityName);
        }

        Map<String, Object> criteria = new HashMap<>();
        for (String field : config.getSearchFields()) {
            criteria.put(field, keyword);
        }

        return search(config.getEntityClass(), criteria);
    }
}
