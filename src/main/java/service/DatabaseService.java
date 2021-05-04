package service;

import javax.ejb.EJB;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@EJB
@Startup
public class DatabaseService{
    @PersistenceContext
    private EntityManager entityManager;

    public EntityManager getEntityManager(){
        return entityManager;
    }
}
