package repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

//@Testcontainers

//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest{

    //UserRepository sut;
//    @Container
//    final PostgreSQLContainer postgres = new PostgreSQLContainer(DockerImageName.parse("postgres:13.2"))
//            .withDatabaseName("list")
//            .withUsername("list")
//            .withPassword("eq7uC37qkQASSLcc");

    //@PersistenceContext(unitName = "TestPU")
    EntityManager entityManager;

    @BeforeEach
    void setUp(){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("list-db-test");
        entityManager = entityManagerFactory
                .createEntityManager();

        System.out.println("hello container");
       // String address = postgres.getHost();
        //int port = postgres.getFirstMappedPort();
    }

    @Test
    @Transactional
    void get(){
        System.out.println("hello there");
        entityManager.createQuery("SELECT UserEntity FROM UserEntity");
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
