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

    @Query(value="select image_tag.* from image_tag join image_imagetag on image_tag.id=image_imagetag.image_tag_id group by image_tag.id having count(image_tag.id) >= 4 order by RAND() limit :amount", nativeQuery = true)
    List<ImageTag> findRandomImageTags(@Param("amount") int amount);
}
