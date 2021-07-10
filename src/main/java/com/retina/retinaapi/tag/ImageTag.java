package com.retina.retinaapi.tag;

import com.retina.retinaapi.image.Image;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "imageTag")
public class ImageTag {

    @Id
    @SequenceGenerator(
            name = "tag_sequence",
            sequenceName = "tag_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tag_sequence"
    )
    private long id;

    private String name;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST
            }
    )
    @JoinTable(
            name = "imagetag_image",
            joinColumns = @JoinColumn(name = "imagetag_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id")
    )
    private Set<Image> images = new HashSet<>();

    public ImageTag() {
    }

    public ImageTag(String name) {
        this.name = name;
    }

    public ImageTag(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public Set<Image> getImages() {
        return images;
    }

    public void setName(String name) {
        this.name = name;
    }
}
