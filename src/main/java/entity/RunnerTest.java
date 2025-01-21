package entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

public class RunnerTest {
    public static void main(String[] args) {
        EntityManager em = Persistence.createEntityManagerFactory("SHH-mariaDB").createEntityManager();

    }
}
