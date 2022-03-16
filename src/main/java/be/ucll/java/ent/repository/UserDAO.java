package be.ucll.java.ent.repository;

import be.ucll.java.ent.model.UserEntity;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserDAO implements Dao<UserEntity> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(UserEntity user) {
        em.persist(user);
    }

    @Override
    // Gebruik Optional om aanroepende code af te dwingen en rekening te houden met NULL
    public Optional<UserEntity> get(long userId) {
        return Optional.ofNullable(em.find(UserEntity.class, userId));
    }

    @Override
    // Zonder Optional kan de return value null zijn en kan je alleen maar hopen
    // dat de aanroepende code daarmee rekening houdt
    public UserEntity read(long userId) {
        return em.find(UserEntity.class, userId);
    }

    /**
     * Deze method zoekt 1 Student in de databank gegeven een naam
     * Deze opzoeking is case insensitive
     * @param name
     * @return
     */
    public Optional<UserEntity> getOneByName(String name) {
        try {
            UserEntity use = null;
            try {
                Query q = em.createQuery("select e from UserEntity e where lower(e.naam) = :p1");
                q.setParameter("p1", name.toLowerCase());
                use = (UserEntity) q.getSingleResult();
            } catch (NoResultException e) {
                // ignore
            }
            return Optional.ofNullable(use);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(UserEntity user) {
        em.merge(user);
    }

    @Override
    public void delete(long userId) {
        UserEntity ref = em.getReference(UserEntity.class, userId);
        if (ref != null) {
            em.remove(ref);
        } else {
            // Already removed
        }
    }

    public List<UserEntity> getUsers(String naam, String voornaam) {
        try {
            List<UserEntity> lst = new ArrayList();
            try {
                String queryString = "select s from UserEntity s where 1 = 1 ";
                if (naam != null && naam.trim().length() > 0) {
                    queryString += "and lower(s.naam) like '%" + naam.toLowerCase().trim() + "%' ";
                }
                if (voornaam != null && voornaam.trim().length() > 0) {
                    queryString += "and lower(s.voornaam) like '%" + voornaam.toLowerCase().trim() + "%' ";
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
    public List<UserEntity> getAll() {
        return em.createNamedQuery("User.getAll").getResultList();
    }

    @Override
    public long countAll() {
        Object o = em.createNamedQuery("User.countAll").getSingleResult();
        return (Long) o;
    }
}



