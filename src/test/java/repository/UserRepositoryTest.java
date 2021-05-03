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
class UserRepositoryTest{

    @Container
    final PostgreSQLContainer postgres = new PostgreSQLContainer(DockerImageName.parse("postgres:13.2"))
            .withDatabaseName("list")
            .withUsername("list")
            .withPassword("eq7uC37qkQASSLcc");
    UserRepository sut;
    //@PersistenceContext
    EntityManager entityManager;

    @BeforeEach
    void setUp(){
        System.out.println("hello container");
        entityManager = Persistence.createEntityManagerFactory("list-db-test").createEntityManager();
        // ...
        // https://www.eclipse.org/eclipselink/api/2.7/org/eclipse/persistence/platform/server/NoServerPlatform.html
        //
        // org.eclipse.persistence.platform.server.NoServerPlatform
        // PUBLIC: This platform is used when EclipseLink is not within any server (Oc4j, WebLogic, ...) This is also the default platform for all newly created DatabaseSessions. This platform has: - No external transaction controller class - No runtime services (JMX/MBean) - No launching of container Threads
        //
        // -> Currently trying to use hibernate instead
        // Error parsing JNDI name [jdbc/list_db_test]
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
