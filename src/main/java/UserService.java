import entity.User;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
public class UserService{
    @PersistenceContext
    private EntityManager em = Persistence.createEntityManagerFactory("list-db").createEntityManager();

    public void savePerson(User user){
        em.persist(user);
    }
}
