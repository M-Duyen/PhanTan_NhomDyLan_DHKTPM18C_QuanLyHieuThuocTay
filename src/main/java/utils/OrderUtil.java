package utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

public class OrderUtil {
    public static void main(String[] args) {
        EntityManager em = Persistence.createEntityManagerFactory("mariadb")
                .createEntityManager();


    }
}
