package dao;

import entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import net.datafaker.Faker;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Product_DAO  {
    private EntityManager em;

    public Product_DAO() {
        em = Persistence.createEntityManagerFactory("mariadb").createEntityManager();
    }


    public static void main(String[] args) {
        Product_DAO productDAO = new Product_DAO();

    }

}
