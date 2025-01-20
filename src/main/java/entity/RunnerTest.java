package entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

public class RunnerTest {
    EntityManager em = Persistence.createEntityManagerFactory("SHH-mariaDB").createEntityManager();

}
