import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import net.datafaker.Faker;

public class Runner {
    private static EntityManager em;
    private static Faker faker = new Faker();

    public static void main(String[] args) {
        em = Persistence.createEntityManagerFactory("mariadb").createEntityManager();


    }


}
