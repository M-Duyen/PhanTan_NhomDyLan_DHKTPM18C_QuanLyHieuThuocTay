package utils;

import dao.Customer_DAO;
import dao.Employee_DAO;
import dao.Prescription_DAO;
import dao.Product_DAO;
import entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import net.datafaker.Faker;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OrderUtil {
    public static void main(String[] args) {
        EntityManager em = Persistence.createEntityManagerFactory("mariadb")
                .createEntityManager();


    }
}
