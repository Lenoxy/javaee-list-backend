package repository;

import dto.UserDto;
import dto.a;
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
    }

    @Test
    @Transactional
    void get(){
        when(databaseServiceMock.getEntityManager()).thenReturn(entityManager);
        UserDto expected = a.UserDtoBuilder().build();
        sut.add(expected);

        UserDto actual = sut.get(expected);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void add(){
        when(databaseServiceMock.getEntityManager()).thenReturn(entityManager);

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
}
