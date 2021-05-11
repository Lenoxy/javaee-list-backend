package repository;

import entity.UserEntity;
import entity.a;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@Testcontainers
@ExtendWith(MockitoExtension.class)
class UserRepositoryTest{

    final String H2_PERSISTENCE_UNIT = "list-db-test-h2";
    final String TESTCONTAINERS_PERSISTENCE_UNIT = "list-db-test-testcontainer";

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

    @AfterEach
    void tearDown(){
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM UserEntity e").executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }


    @ParameterizedTest
    @ValueSource(strings = {TESTCONTAINERS_PERSISTENCE_UNIT, H2_PERSISTENCE_UNIT})
    void get(String persistenceUnit){
        entityManager = setupEntityManager(persistenceUnit);

        UserEntity expected = a.UserEntityBuilder().build();

        entityManager.getTransaction().begin();
        entityManager.persist(expected);
        entityManager.getTransaction().commit();

        UserEntity actual = sut.getByUsernameAndPassword(expected.getUsername(), expected.getPasswordSHA256());

        assertThat(expected)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {TESTCONTAINERS_PERSISTENCE_UNIT, H2_PERSISTENCE_UNIT})
    void removeById(String persistenceUnit){
        entityManager = setupEntityManager(persistenceUnit);

        UserEntity expected = a.UserEntityBuilder().build();
        entityManager.getTransaction().begin();
        entityManager.persist(expected);
        entityManager.getTransaction().commit();
        assertThat(getAllPersistedUsers()).isNotEmpty();
        UserEntity userEntity = sut.getByUsernameAndPassword(expected.getUsername(), expected.getPasswordSHA256());

        entityManager.getTransaction().begin();
        sut.removeById(userEntity.getId());
        entityManager.getTransaction().commit();

        assertThat(getAllPersistedUsers()).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {TESTCONTAINERS_PERSISTENCE_UNIT, H2_PERSISTENCE_UNIT})
    void add(String persistenceUnit){
        entityManager = setupEntityManager(persistenceUnit);

        UserEntity expected = a.UserEntityBuilder().build();

        entityManager.getTransaction().begin();
        sut.add(expected);
        entityManager.getTransaction().commit();

        assertThat(getAllPersistedUsers()).isNotEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {TESTCONTAINERS_PERSISTENCE_UNIT, H2_PERSISTENCE_UNIT})
    void modifyById(String persistenceUnit){
        entityManager = setupEntityManager(persistenceUnit);

        UserEntity unchangedUserEntity = a.UserEntityBuilder().build();
        entityManager.getTransaction().begin();
        sut.add(unchangedUserEntity);
        entityManager.getTransaction().commit();
        assertThat(getAllPersistedUsers()).isNotEmpty();
        UserEntity userEntityWithIdSet = sut.getByUsernameAndPassword(unchangedUserEntity.getUsername(), unchangedUserEntity.getPasswordSHA256());
        userEntityWithIdSet.setUsername("new-username");
        userEntityWithIdSet.setPasswordSHA256("new-password");

        entityManager.getTransaction().begin();
        sut.modifyById(userEntityWithIdSet.getId(), userEntityWithIdSet);
        entityManager.getTransaction().commit();

        assertThat(getAllPersistedUsers().size()).isEqualTo(1);
        assertThat(getAllPersistedUsers().get(0)).isEqualTo(userEntityWithIdSet);

    }

    private EntityManager setupEntityManager(String persistenceUnit){
        EntityManager em = Persistence.createEntityManagerFactory(persistenceUnit).createEntityManager();
        when(databaseServiceMock.getEntityManager()).thenReturn(em);
        return em;
    }


    private List<UserEntity> getAllPersistedUsers(){
        return entityManager.createQuery("SELECT u FROM UserEntity u").getResultList();
    }
}
