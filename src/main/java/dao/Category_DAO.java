package dao;

import entity.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import net.datafaker.Faker;

import java.util.List;

public class Category_DAO {
    private EntityManager em;

    public Category_DAO() {
        em = Persistence.createEntityManagerFactory("mariadb").createEntityManager();
    }




    public static void main(String[] args) {
        Category_DAO category_dao = new Category_DAO();



    }

}
