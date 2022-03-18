package be.ucll.java.ent.repository;

import be.ucll.java.ent.model.AanvraagEntity;
import be.ucll.java.ent.model.ProductEntity;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class AanvraagDAO implements Dao<AanvraagEntity> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(AanvraagEntity aanvraag) {
        em.persist(aanvraag);
    }

    @Override
    // Gebruik Optional om aanroepende code af te dwingen en rekening te houden met NULL
    public Optional<AanvraagEntity> get(long aanvraagId) {
        return Optional.ofNullable(em.find(AanvraagEntity.class, aanvraagId));
    }

    @Override
    // Zonder Optional kan de return value null zijn en kan je alleen maar hopen
    // dat de aanroepende code daarmee rekening houdt
    public AanvraagEntity read(long aanvraagId) {
        return em.find(AanvraagEntity.class, aanvraagId);
    }

    public Optional<AanvraagEntity> getOneByName(String aanvraagNaam) {
        try {
            AanvraagEntity aanvraag = null;
            try {
                Query q = em.createQuery("select e from AanvraagEntity e where lower(e.productNaam) = :p1");
                q.setParameter("p1", aanvraagNaam.toLowerCase());
                aanvraag = (AanvraagEntity) q.getSingleResult();
            } catch (NoResultException e) {
                // ignore
            }
            return Optional.ofNullable(aanvraag);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(AanvraagEntity pro) {
        em.merge(pro);
    }

    @Override
    public void delete(long aanvraagId) {
        AanvraagEntity ref = em.getReference(AanvraagEntity.class, aanvraagId);
        if (ref != null) {
            em.remove(ref);
        } else {
            // Already removed
        }
    }

    public List<AanvraagEntity> getAanvragen(String productNaam) {
        try {
            List<AanvraagEntity> lst = new ArrayList();
            try {
                String queryString = "select s from AanvraagEntity s where 1 = 1 ";
                if (productNaam != null && productNaam.trim().length() > 0) {
                    queryString += "and lower(s.productNaam) like '%" + productNaam.toLowerCase().trim() + "%' ";
                }

                // System.out.println("Query: " + queryString);
                Query query = em.createQuery(queryString);
                lst = query.getResultList();
            } catch (NoResultException e) {
                // ignore
            }
            return lst;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<AanvraagEntity> getAll() {
        return em.createNamedQuery("Aanvraag.getAll").getResultList();
    }

    @Override
    public long countAll() {
        Object o = em.createNamedQuery("Aanvraag.countAll").getSingleResult();
        return (Long) o;
    }
}
