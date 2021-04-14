package service;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Singleton
@Startup
public class DatabaseService{
    @PersistenceContext
    public EntityManager entityManager;
}
