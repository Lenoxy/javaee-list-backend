package repository;

import dto.UserDto;
import dto.a;
import entity.UserEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.DatabaseService;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class H2UserRepositoryTest{
    EntityManager entityManager;

    @Mock
    DatabaseService databaseServiceMock;

    @InjectMocks
    UserRepository sut;

    @BeforeEach
    void setUp(){
        entityManager = Persistence.createEntityManagerFactory("list-db-test-h2").createEntityManager();
        // Return entity manager pointing to the H2 DB
        when(databaseServiceMock.getEntityManager()).thenReturn(entityManager);
    }

    @AfterEach
    void tearDown(){
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM UserEntity e").executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Test
    @Transactional
    void get(){
        UserDto expected = a.UserDtoBuilder().build();
        entityManager.getTransaction().begin();
        sut.add(expected);
        entityManager.getTransaction().commit();


        UserDto actual = sut.getByUsernameAndPassword(expected.getUsername(), expected.getPasswordSHA256());

        assertThat(actual).usingRecursiveComparison().ignoringFields("id").isEqualTo(expected);
    }

    @Test
    void add(){
        UserDto expected = a.UserDtoBuilder().build();
        entityManager.getTransaction().begin();
        sut.add(expected);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("SELECT u FROM UserEntity u");
        List resultList = query.getResultList();
        entityManager.getTransaction().commit();


        assertThat(resultList).isNotEmpty();
    }

    @Test
    void removeById(){
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

    List<UserEntity> getAllPersistedUsers(){
        return entityManager.createQuery("SELECT u FROM UserEntity u").getResultList();
    }
}
