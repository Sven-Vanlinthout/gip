package be.ucll.java.ent.repository;

import be.ucll.java.ent.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

    List<ProductEntity> findAllByNaamContainsIgnoreCaseOrderByNaam(String pruductNaam);

    ProductEntity getFirstByNaam(String naam);

}
