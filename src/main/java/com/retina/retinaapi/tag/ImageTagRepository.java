package com.retina.retinaapi.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageTagRepository extends JpaRepository<ImageTag, Long> {

    List<ImageTag> findByNameIn (List<String> name);

    Optional<ImageTag> findByName (String name);

    @Query(value="SELECT * FROM imageTag ORDER BY RAND() LIMIT :amount", nativeQuery = true)
    List<ImageTag> findRandomImageTags(@Param("amount") int amount);
}
