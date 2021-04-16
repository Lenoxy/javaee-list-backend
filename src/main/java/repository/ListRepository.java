package repository;

import dto.ListDto;
import dto.UserDto;
import entity.ListEntity;
import entity.UserEntity;
import service.DatabaseService;

import javax.inject.Inject;
import javax.persistence.Query;
import java.util.List;

public class ListRepository{
    @Inject
    DatabaseService database;

    public List<ListDto> getListByUserId(UserDto user){
        Query query = database.entityManager.createQuery("SELECT l FROM ListEntity AS l " +
                "WHERE l.owner = :ownerId");
        query.setParameter("ownerId", user.toUserEntity());
        return (List<ListDto>) query.getResultList();
    }

    public void addList(UserDto owner, String listTitle){
        UserEntity userEntity = owner.toUserEntity();

        userEntity.addListEntity(new ListEntity(listTitle));
        database.entityManager.persist(userEntity);
    }

    public void removeList(ListDto listDto){
        Query query = database.entityManager.createQuery("DELETE FROM ListEntity l  WHERE l.id = :id");
        query.setParameter("id", listDto.toListEntity().getId());
    }

    public ListEntity getListById(int listId){
        Query query = database.entityManager.createQuery("SELECT l FROM ListEntity AS l " +
                "WHERE l.id = :id");
        query.setParameter("id", listId);
        return (ListEntity) query.getSingleResult();
    }
}
