package repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.transaction.Transactional;

@Testcontainers
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest{

    //UserRepository sut;
    @Container
    final PostgreSQLContainer postgres = new PostgreSQLContainer(DockerImageName.parse("postgres:13.2"))
            .withDatabaseName("list")
            .withUsername("list")
            .withPassword("eq7uC37qkQASSLcc");

    private EntityManager entityManager;

    @BeforeEach
    void setUp(){
        String address = postgres.getHost();
        //int port = postgres.getFirstMappedPort();
        entityManager = Persistence.createEntityManagerFactory(
                "list-testcontainers-db"
        ).createEntityManager();

    }

    //@Test
    @Transactional
    void get(){
        entityManager.createQuery("SELECT UserEntity FROM UserEntity ");
    }

    //@Test
    void removeById(){
    }

    //@Test
    void add(){
    }

    //@Test
    void modifyById(){
    }
}
