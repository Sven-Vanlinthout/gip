package be.ucll.java.ent.repository;

import be.ucll.java.ent.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    List<UserEntity> findAllByNaamContainsIgnoreCaseOrderByNaam(String naam);

    UserEntity getFirstByNaam(String naam);

}
