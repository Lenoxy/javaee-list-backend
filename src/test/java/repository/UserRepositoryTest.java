package repository;

import entity.UserEntity;
import entity.a;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import service.DatabaseService;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@Testcontainers
class UserRepositoryTest{

    final static String H2_PERSISTENCE_UNIT = "list-db-test-h2";
    final static String TESTCONTAINERS_PERSISTENCE_UNIT = "list-db-test-testcontainer";

    @Container
    final PostgreSQLContainer postgres = new PostgreSQLContainer(DockerImageName.parse("postgres:13.2"))
            .withDatabaseName("list")
            .withUsername("list")
            .withPassword("eq7uC37qkQASSLcc");

    @Mock
    DatabaseService databaseServiceMock;

    @InjectMocks
    UserRepository sut;

    private static Stream<EntityManager> provideEntityManagers(){
        return Stream.of(
                Persistence.createEntityManagerFactory(H2_PERSISTENCE_UNIT).createEntityManager(),
                Persistence.createEntityManagerFactory(TESTCONTAINERS_PERSISTENCE_UNIT).createEntityManager()
        );
    }

    @BeforeEach
    void setUp(){
        // Somehow the mocks are not automatically initialized
        MockitoAnnotations.initMocks(this);
    }

    @AfterEach
    void tearDown(){
        // Abusing the mock
        databaseServiceMock.getEntityManager().getTransaction().begin();
        databaseServiceMock.getEntityManager().createQuery("DELETE FROM UserEntity e").executeUpdate();
        databaseServiceMock.getEntityManager().getTransaction().commit();
        databaseServiceMock.getEntityManager().close();
    }

    @ParameterizedTest
    @MethodSource(value = "provideEntityManagers")
    void get(EntityManager entityManager){
        // This function cannot be called outside of the @Test scope
        when(databaseServiceMock.getEntityManager()).thenReturn(entityManager);

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
    @MethodSource(value = "provideEntityManagers")
    void removeById(EntityManager entityManager){
        // This function cannot be called outside of the @Test scope
        when(databaseServiceMock.getEntityManager()).thenReturn(entityManager);

        UserEntity expected = a.UserEntityBuilder().build();
        entityManager.getTransaction().begin();
        entityManager.persist(expected);
        entityManager.getTransaction().commit();
        assertThat(getAllPersistedUsers(entityManager)).isNotEmpty();
        UserEntity userEntity = sut.getByUsernameAndPassword(expected.getUsername(), expected.getPasswordSHA256());

        entityManager.getTransaction().begin();
        sut.removeById(userEntity.getId());
        entityManager.getTransaction().commit();

        assertThat(getAllPersistedUsers(entityManager)).isEmpty();
    }

    @ParameterizedTest
    @MethodSource(value = "provideEntityManagers")
    void add(EntityManager entityManager){
        // This function cannot be called outside of the @Test scope
        when(databaseServiceMock.getEntityManager()).thenReturn(entityManager);

        UserEntity expected = a.UserEntityBuilder().build();

        entityManager.getTransaction().begin();
        sut.add(expected);
        entityManager.getTransaction().commit();

        assertThat(getAllPersistedUsers(entityManager)).isNotEmpty();
    }

    @ParameterizedTest
    @MethodSource(value = "provideEntityManagers")
    void modifyById(EntityManager entityManager){
        // This function cannot be called outside of the @Test scope
        when(databaseServiceMock.getEntityManager()).thenReturn(entityManager);

        UserEntity unchangedUserEntity = a.UserEntityBuilder().build();
        entityManager.getTransaction().begin();
        sut.add(unchangedUserEntity);
        entityManager.getTransaction().commit();
        assertThat(getAllPersistedUsers(entityManager)).isNotEmpty();
        UserEntity userEntityWithIdSet = sut.getByUsernameAndPassword(unchangedUserEntity.getUsername(), unchangedUserEntity.getPasswordSHA256());
        userEntityWithIdSet.setUsername("new-username");
        userEntityWithIdSet.setPasswordSHA256("new-password");

        entityManager.getTransaction().begin();
        sut.modifyById(userEntityWithIdSet.getId(), userEntityWithIdSet);
        entityManager.getTransaction().commit();

        assertThat(getAllPersistedUsers(entityManager).size()).isEqualTo(1);
        assertThat(getAllPersistedUsers(entityManager).get(0)).isEqualTo(userEntityWithIdSet);

    }

    private List<UserEntity> getAllPersistedUsers(EntityManager entityManager){
        return entityManager.createQuery("SELECT u FROM UserEntity u").getResultList();
    }
}
