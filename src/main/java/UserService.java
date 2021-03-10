import entity.User;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.transaction.Transactional;

public class UserService{
    private final EntityManager em = Persistence.createEntityManagerFactory("list-db").createEntityManager();

    @Transactional(Transactional.TxType.REQUIRED)
    public void savePerson(User user){
        System.out.println("user.getUsername() = " + user.getUsername());
        System.out.println("em.getFlushMode().name() = " + em.getFlushMode().name());

        em.persist(user);
    }
}
