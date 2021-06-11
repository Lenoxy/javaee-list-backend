package repository;

import entity.UserEntity;
import service.DatabaseService;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Optional;

@EJB
public class UserRepository{
    @Inject
    DatabaseService database;

    @Transactional
    public UserEntity getByUsernameAndPassword(String username, String passwordSHA256){
        Query q = database.getEM().createQuery(
                "SELECT u FROM UserEntity AS u " +
                        "WHERE u.username = :username AND " +
                        "u.passwordSHA256 = :passwordSHA256"
        );
        q.setParameter("username", username);
        q.setParameter("passwordSHA256", passwordSHA256);

        if(q.getResultList().size() == 1){
            return (UserEntity) q.getSingleResult();
        }else{
            return null;
        }
    }

    @Transactional
    public void removeById(int id){
        Query q = database.getEM().createQuery(
                "DELETE FROM UserEntity as u " +
                        "WHERE u.id = :id "
        );
        q.setParameter("id", id);
        int rowsAffected = q.executeUpdate();

        if(rowsAffected != 1){
            database.getEM().getTransaction().rollback();
        }

    }

    @Transactional
    public void add(UserEntity userEntity){
        database.getEM().persist(userEntity);
    }

    @Transactional
    public void modifyById(int id, UserEntity userEntity){
        Query query = database.getEM().createQuery(
                "UPDATE UserEntity u SET " +
                        "u.username = :username, " +
                        "u.passwordSHA256 = :passwordSHA256 " +
                        "WHERE u.id = :id");
        query.setParameter("username", userEntity.getUsername());
        query.setParameter("passwordSHA256", userEntity.getPasswordSHA256());
        query.setParameter("id", id);
        if(query.executeUpdate() != 1){
            throw new RuntimeException("The user could not not be updated");
        }
    }

    @Transactional
    public Optional<UserEntity> getByUsername(String username){
        TypedQuery<UserEntity> query = database.getEM().createQuery("SELECT u FROM UserEntity u  WHERE u.username = :username", UserEntity.class);
        query.setParameter("username", username);
        return query.getResultStream().findFirst();
    }
}
