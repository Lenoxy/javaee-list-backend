package repository;

import dto.UserDto;
import entity.UserEntity;
import service.DatabaseService;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.transaction.Transactional;

@EJB
public class UserRepository{
    @Inject
    DatabaseService database;

    @Transactional
    public UserDto get(UserDto userDto){
        Query q = database.getEntityManager().createQuery(
                "SELECT u FROM UserEntity AS u " +
                        "WHERE u.username = :username AND " +
                        "u.passwordSHA256 = :passwordSHA256"
        );
        q.setParameter("username", userDto.getUsername());
        q.setParameter("passwordSHA256", userDto.getPasswordSHA256());

        if(q.getResultList().size() == 1){
            return ((UserEntity) q.getSingleResult()).toUserDto();
        }else{
            return null;
        }
    }

    @Transactional
    public void removeById(int id){
        Query q = database.getEntityManager().createQuery(
                "DELETE FROM UserEntity as u " +
                        "WHERE u.id = :id "
        );
        q.setParameter("id", id);
        int rowsAffected = q.executeUpdate();

        if(rowsAffected != 1){
            database.getEntityManager().getTransaction().rollback();
        }

    }

    @Transactional
    public void add(UserDto userDto){
        database.getEntityManager().persist(userDto.toUserEntity());
    }

    @Transactional
    public Object modifyById(int id){
        return null;
    }
}
