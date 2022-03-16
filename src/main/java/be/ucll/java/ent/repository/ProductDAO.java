package be.ucll.java.ent.repository;

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
public class ProductDAO implements Dao<ProductEntity> {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(ProductEntity product) {
        em.persist(product);
    }

    @Override
    // Gebruik Optional om aanroepende code af te dwingen en rekening te houden met NULL
    public Optional<ProductEntity> get(long productId) {
        return Optional.ofNullable(em.find(ProductEntity.class, productId));
    }

    @Override
    // Zonder Optional kan de return value null zijn en kan je alleen maar hopen
    // dat de aanroepende code daarmee rekening houdt
    public ProductEntity read(long productId) {
        return em.find(ProductEntity.class, productId);
    }

    public Optional<ProductEntity> getOneByName(String productNaam) {
        try {
            ProductEntity pro = null;
            try {
                Query q = em.createQuery("select e from ProductEntity e where lower(e.productNaam) = :p1");
                q.setParameter("p1", productNaam.toLowerCase());
                pro = (ProductEntity) q.getSingleResult();
            } catch (NoResultException e) {
                // ignore
            }
            return Optional.ofNullable(pro);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(ProductEntity pro) {
        em.merge(pro);
    }

    @Override
    public void delete(long productId) {
        ProductEntity ref = em.getReference(ProductEntity.class, productId);
        if (ref != null) {
            em.remove(ref);
        } else {
            // Already removed
        }
    }

    public List<ProductEntity> getProducts(String productNaam) {
        try {
            List<ProductEntity> lst = new ArrayList();
            try {
                String queryString = "select s from ProductEntity s where 1 = 1 ";
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
    public List<ProductEntity> getAll() {
        return em.createNamedQuery("Product.getAll").getResultList();
    }

    @Override
    public long countAll() {
        Object o = em.createNamedQuery("Product.countAll").getSingleResult();
        return (Long) o;
    }
}
