package repository;

import entity.ListEntity;
import entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Testcontainers
class TestcontainerUserRepositoryTest{

    @Container
    final PostgreSQLContainer postgres = new PostgreSQLContainer(DockerImageName.parse("postgres:13.2"))
            .withDatabaseName("list")
            .withUsername("list")
            .withPassword("eq7uC37qkQASSLcc");
    UserRepository sut;
    EntityManager entityManager;

    @BeforeEach
    void setUp() {
        entityManager = Persistence.createEntityManagerFactory("list-db-test-testcontainer").createEntityManager();
    }

    @Test
    @Transactional
    void get() {
        ListEntity listEntity = new ListEntity();
        listEntity.setTitle("test");
        List<ListEntity> listEntities = Arrays.asList(listEntity);
        UserEntity userEntity = new UserEntity(0, "User", "hashedPW", listEntities);

        entityManager.getTransaction().begin();
        entityManager.persist(userEntity);
        entityManager.getTransaction().commit();

        List resultList = entityManager.createQuery("SELECT u FROM UserEntity AS u").getResultList();

        System.out.println(resultList);
    }

    //@Test
    void removeById() {
    }

    //@Test
    void add() {
    }

    //@Test
    void modifyById() {
    }
}
