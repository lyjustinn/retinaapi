package com.retina.retinaapi.image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query(value = "select distinct user_image.* from image_imagetag join image_tag on image_tag.id=image_imagetag.image_tag_id join user_image on user_image.id=image_imagetag.image_id join retina_user on retina_user.id=user_image.owner_id where concat(coalesce(user_image.description, ''), user_image.name, retina_user.username, retina_user.first_name, retina_user.last_name, image_tag.name) like %:term%",
    nativeQuery = true)
    List<Image> findImageBySearch (@Param("term") String term);

    @Query(value = "select * from user_image where user_image.owner_id=:ownerId", nativeQuery = true)
    List<Image> findImageByOwner (@Param("ownerId") Long ownerId);
}
