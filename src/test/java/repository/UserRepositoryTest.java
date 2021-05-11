package repository;

import dto.UserDto;
import dto.a;
import entity.UserEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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


    @BeforeEach
    void setUp(){
    }

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

        UserDto expected = a.UserDtoBuilder().build();

        entityManager.getTransaction().begin();
        entityManager.persist(expected.toUserEntity());
        entityManager.getTransaction().commit();

        UserDto actual = sut.getByUsernameAndPassword(expected.getUsername(), expected.getPasswordSHA256());

        assertThat(expected)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {TESTCONTAINERS_PERSISTENCE_UNIT, H2_PERSISTENCE_UNIT})
    void removeById(String persistenceUnit){
        entityManager = setupEntityManager(persistenceUnit);

        UserDto expected = a.UserDtoBuilder().build();
        entityManager.getTransaction().begin();
        entityManager.persist(expected.toUserEntity());
        entityManager.getTransaction().commit();
        assertThat(getAllPersistedUsers()).isNotEmpty();
        UserDto userDto = sut.getByUsernameAndPassword(expected.getUsername(), expected.getPasswordSHA256());

        entityManager.getTransaction().begin();
        sut.removeById(userDto.getId());
        entityManager.getTransaction().commit();

        assertThat(getAllPersistedUsers()).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {TESTCONTAINERS_PERSISTENCE_UNIT, H2_PERSISTENCE_UNIT})
    void add(String persistenceUnit){
        entityManager = setupEntityManager(persistenceUnit);

        UserDto expected = a.UserDtoBuilder().build();

        entityManager.getTransaction().begin();
        sut.add(expected);
        entityManager.getTransaction().commit();

        assertThat(getAllPersistedUsers()).isNotEmpty();
    }

    //@Test
    void modifyById(){
    }

    private EntityManager setupEntityManager(String persistenceUnit){
        EntityManager em = Persistence.createEntityManagerFactory(persistenceUnit).createEntityManager();
        when(databaseServiceMock.getEntityManager()).thenReturn(em);
        return em;
    }


    List<UserEntity> getAllPersistedUsers(){
        return entityManager.createQuery("SELECT u FROM UserEntity u").getResultList();
    }
}
