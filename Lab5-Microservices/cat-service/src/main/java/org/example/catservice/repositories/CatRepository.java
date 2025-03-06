
package org.example.catservice.repositories;

import java.util.List;
import org.example.common.model.Cat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatRepository extends JpaRepository<Cat, Long> {
    List<Cat> findAllByOwnerId(Long ownerId);
}
