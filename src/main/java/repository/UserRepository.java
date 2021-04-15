package repository;

import dto.ItemDto;
import dto.UserDto;
import entity.ItemEntity;
import entity.UserEntity;
import service.DatabaseService;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.Query;

@EJB
public class UserRepository{
    @Inject
    DatabaseService database;

    public UserDto get(UserDto userDto){
        Query q = database.entityManager.createQuery(
                "SELECT u FROM UserEntity AS u " +
                        "WHERE u.username = :username AND " +
                        "u.passwordSHA256 = :passwordSHA256"
        );
        q.setParameter("username", userDto.getUsername());
        q.setParameter("passwordSHA256", userDto.getPasswordSHA256());

        if(q.getFirstResult() > 0){
            return ((UserEntity) q.getSingleResult()).toUserDto();
        }else{
            return null;
        }
    }

    public void removeById(int id){

    }

    public void add(ItemDto object){

    }

    public Object modifyById(int id){
        return null;
    }
}
