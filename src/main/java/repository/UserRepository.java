package repository;

import entity.UserEntity;
import service.DatabaseService;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.util.Optional;

@EJB
public class UserRepository{
    @Inject
    DatabaseService database;

    public UserEntity getByUsernameAndPassword(String username, String passwordSHA256){
        TypedQuery<UserEntity> q = database.getEM().createQuery(
                "SELECT u FROM UserEntity AS u " +
                        "WHERE u.username = :username AND " +
                        "u.passwordSHA256 = :passwordSHA256",
                UserEntity.class
        );
        q.setParameter("username", username);
        q.setParameter("passwordSHA256", passwordSHA256);

        return q.getResultStream().findFirst().orElseThrow(NotFoundException::new);
    }

    @Transactional(Transactional.TxType.MANDATORY)
    public void removeById(int id){
        Query q = database.getEM().createQuery(
                "DELETE FROM UserEntity as u " +
                        "WHERE u.id = :id "
        );
        q.setParameter("id", id);
        int rowsAffected = q.executeUpdate();

        if(rowsAffected != 1){
            database.getEM().getTransaction().rollback();
            throw new IllegalStateException();
        }

    }

    @Transactional(Transactional.TxType.MANDATORY)
    public UserEntity getById(int id){
        TypedQuery<UserEntity> q = database.getEM().createQuery(
                "SELECT u FROM UserEntity u " +
                        "WHERE u.id = :id ",
                UserEntity.class
        );
        q.setParameter("id", id);
        return q.getResultStream().findFirst().orElseThrow(NotFoundException::new);
    }

    @Transactional(Transactional.TxType.MANDATORY)
    public Integer add(UserEntity userEntity){
        database.getEM().persist(userEntity);
        database.getEM().flush();
        return userEntity.getId();
    }

    @Transactional(Transactional.TxType.MANDATORY)
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

    public Optional<UserEntity> getByUsername(String username){
        TypedQuery<UserEntity> query = database.getEM().createQuery("SELECT u FROM UserEntity u  WHERE u.username = :username", UserEntity.class);
        query.setParameter("username", username);
        return query.getResultStream().findFirst();
    }
}
