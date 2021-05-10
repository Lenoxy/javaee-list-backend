package repository;

import dto.ListDto;
import dto.UserDto;
import dto.a;
import entity.ListEntity;
import entity.UserEntity;
import org.h2.engine.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import service.DatabaseService;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@Testcontainers
@ExtendWith(MockitoExtension.class)
class TestcontainerUserRepositoryTest{

    @Container
    final PostgreSQLContainer postgres = new PostgreSQLContainer(DockerImageName.parse("postgres:13.2"))
            .withDatabaseName("list")
            .withUsername("list")
            .withPassword("eq7uC37qkQASSLcc");

    @Mock
    DatabaseService databaseServiceMock;

    @InjectMocks
    UserRepository sut = new UserRepository();
    EntityManager entityManager;



    @BeforeEach
    void setUp(){
        entityManager = Persistence.createEntityManagerFactory("list-db-test-testcontainer").createEntityManager();
        when(databaseServiceMock.getEntityManager()).thenReturn(entityManager);
    }

    @Test
    @Transactional
    void get(){
        UserDto expected = a.UserDtoBuilder().withId(1).build();

        entityManager.getTransaction().begin();
        entityManager.persist(expected.toUserEntity());
        entityManager.getTransaction().commit();

        UserDto actual = sut.get(expected);

        assertThat(expected).usingRecursiveComparison().isEqualTo(actual);
    }

    //@Test
    void removeById(){
    }

    @Test
    void add(){
        // TODO Search Kuzu-Leistung for alternatives
        entityManager.getTransaction().begin();
        UserDto expected = a.UserDtoBuilder().build();
        sut.add(expected);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("SELECT u FROM UserEntity u");
        List resultList = query.getResultList();
        entityManager.getTransaction().commit();

        assertThat(resultList).isNotEmpty();
    }

    //@Test
    void modifyById(){
    }
}
