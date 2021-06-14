package repository;

import dto.ListDto;
import dto.UserDto;
import entity.ListEntity;
import entity.UserEntity;
import service.DatabaseService;

import javax.inject.Inject;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

public class ListRepository{
    @Inject
    DatabaseService database;

    @Inject
    UserRepository userRepository;

    public List<ListEntity> getListByUserId(Integer ownerId){
        UserEntity ownerUserEntity = userRepository.getById(ownerId);
        TypedQuery<ListEntity> query = database.getEM().createQuery("SELECT l FROM ListEntity AS l " +
                "WHERE l.owner = :ownerId",
                ListEntity.class
        );
        query.setParameter("ownerId", ownerUserEntity);
        return query.getResultList();
    }

    @Transactional
    public void addList(Integer ownerId, String listTitle){
        UserEntity userEntity = userRepository.getById(ownerId);
        userEntity.addListEntity(new ListEntity(listTitle));
        database.getEM().persist(userEntity);
    }

    public void removeList(ListDto listDto){
        Query query = database.getEM().createQuery("DELETE FROM ListEntity l  WHERE l.id = :id");
        query.setParameter("id", listDto.toListEntity().getId());
    }

    public ListEntity getListById(int listId){
        Query query = database.getEM().createQuery("SELECT l FROM ListEntity AS l " +
                "WHERE l.id = :id");
        query.setParameter("id", listId);
        return (ListEntity) query.getSingleResult();
    }
}
