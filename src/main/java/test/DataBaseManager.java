package test;

import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;

import javax.annotation.Resource;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.*;
import java.util.List;


public class DataBaseManager {
    private static final String PERSISTENCE_UNIT_NAME = "ora";

    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;

    @Resource
    private UserTransaction userTransaction;


    public List<Point> loadEntries() {
        return entityManager.createQuery("SELECT p FROM test.Point p").getResultList();
    }

    public synchronized void addEntryToDB(Point point) throws Exception {
        userTransaction.begin();
        entityManager.persist(point);
        userTransaction.commit();
    }

    public synchronized void clearDB(Point point) throws SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {
        userTransaction.begin();
        entityManager.remove(entityManager.merge(point));
        userTransaction.commit();
    }
}