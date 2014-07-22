package cz.muni.fi.mir.db.dao.impl;

import cz.muni.fi.mir.db.dao.ApplicationRunDAO;
import cz.muni.fi.mir.db.domain.ApplicationRun;
import cz.muni.fi.mir.db.domain.Configuration;
import cz.muni.fi.mir.db.domain.Revision;
import cz.muni.fi.mir.db.domain.User;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 * @author Dominik Szalai
 *
 * @version 1.0
 * @since 1.0
 *
 */
@Repository(value = "applicationRunDAO")
public class ApplicationRunDAOImpl implements ApplicationRunDAO
{

    @PersistenceContext
    private EntityManager entityManager;

    private static final Logger logger = Logger.getLogger(ApplicationRunDAOImpl.class);

    @Override
    public void createApplicationRun(ApplicationRun applicationRun)
    {
        entityManager.persist(applicationRun);
    }

    @Override
    public void updateApplicationRun(ApplicationRun applicationRun)
    {
        entityManager.merge(applicationRun);
    }

    @Override
    public void deleteApplicationRun(ApplicationRun applicationRun)
    {
        ApplicationRun ap = entityManager.find(ApplicationRun.class, applicationRun.getId());
        if (ap != null)
        {
            entityManager.remove(ap);
        } 
        else
        {
            logger.info("Trying to delete ApplicationRun with ID that has not been found. The ID is [" + applicationRun.getId().toString() + "]");
        }
    }

    @Override
    public ApplicationRun getApplicationRunByID(Long id)
    {
        ApplicationRun ar = entityManager.find(ApplicationRun.class, id);
        int count = 0;
        try
        {
            count = entityManager.createQuery("SELECT count(co) FROM canonicOutput co WHERE co.applicationRun = :apprun", Long.class)
                    .setParameter("apprun", ar).getSingleResult().intValue();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        ar.setCanonicOutputCount(count);
        
        return ar;
    }

    @Override
    public List<ApplicationRun> getAllApplicationRuns()
    {
        List<ApplicationRun> resultList = new ArrayList<>();

        List<Object[]> results =
            entityManager.createQuery("SELECT apr,COUNT(co) FROM applicationRun apr, canonicOutput co WHERE co.applicationRun = apr GROUP BY apr.id")
                    .getResultList();
        
        for(Object[] row : results)
        {
            ApplicationRun ar = (ApplicationRun) row[0];
            Long count = (Long) row[1];
            
            ar.setCanonicOutputCount(count.intValue());
            
            resultList.add(ar);
        }       

        return resultList;
    }

    @Override
    public List<ApplicationRun> getAllApplicationRunsByUser(User user)
    {
        List<ApplicationRun> resultList = Collections.emptyList();

        try
        {
            resultList = entityManager.createQuery("SELECT apr FROM applicationRun apr WHERE apr.user = :user", ApplicationRun.class)
                    .setParameter("user", user).getResultList();
        } 
        catch (NoResultException nre)
        {
            logger.debug(nre);
        }

        return resultList;
    }

    @Override
    public List<ApplicationRun> getAllApplicationRunsByRevision(Revision revision)
    {
        List<ApplicationRun> resultList = Collections.emptyList();
        
        try
        {
            resultList = entityManager.createQuery("SELECT apr FROM applicationRun apr WHERE apr.revision = :revision", ApplicationRun.class)
                    .setParameter("revision", revision).getResultList();
        } 
        catch (NoResultException nre)
        {
            logger.debug(nre);
        }

        return resultList;
    }

    @Override
    public List<ApplicationRun> getAllApplicationRunsByConfiguration(Configuration configuration)
    {
        List<ApplicationRun> resultList = Collections.emptyList();
        
        try
        {
            resultList = entityManager.createQuery("SELECT apr FROM applicationRun apr WHERE apr.configuration = :configuration", ApplicationRun.class)
                    .setParameter("configuration", configuration).getResultList();
        } 
        catch (NoResultException nre)
        {
            logger.debug(nre);
        }

        return resultList;
    }

    @Override
    public List<ApplicationRun> getAllApplicationRunsFromRange(int start, int end)
    {
        List<ApplicationRun> resultList = Collections.emptyList();

        try
        {
            resultList = entityManager.createQuery("SELECT ar FROM applicationRun ar ORDER BY ar.id DESC", ApplicationRun.class)
                    .setFirstResult(start).setMaxResults(end - start).getResultList();
        } 
        catch (NoResultException nre)
        {
            logger.debug(nre);
        }

        return resultList;
    }
}
