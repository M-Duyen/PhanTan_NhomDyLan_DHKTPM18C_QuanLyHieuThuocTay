package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

public class CategoryDAO {
    private EntityManager em;

    public CategoryDAO() {
        em = Persistence.createEntityManagerFactory("mariadb").createEntityManager();
    }

    public static void main(String[] args) {
        CategoryDAO category_dao = new CategoryDAO();



    }

}
