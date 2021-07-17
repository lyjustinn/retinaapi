package com.retina.retinaapi.image;

import com.retina.retinaapi.tag.ImageTag;
import com.retina.retinaapi.user.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "userImage")
public class Image {

    @Id
    @SequenceGenerator(
            name = "image_sequence",
            sequenceName = "image_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "image_sequence"
    )
    private Long id;

    private String name;

    private String description;

    private String resourceName;

    @ManyToOne
    private User owner;

    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.PERSIST
            }
    )
    @JoinTable(
            name = "image_imagetag",
            joinColumns = @JoinColumn(name = "image_id"),
            inverseJoinColumns = @JoinColumn(name = "imageTag_id")
    )
    private Set<ImageTag> imageTags =  new HashSet<>();

    public Image() {
    }

    public Image(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Image(String name, String description, String resourceName) {
        this.name = name;
        this.description = description;
        this.resourceName = resourceName;
    }

    public Image(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Image(Long id, String name, String description, String resourceName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.resourceName = resourceName;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getResourceName() {
        return resourceName;
    }

    public User getOwner() {
        return owner;
    }

    public Set<ImageTag> getTags() {
        return imageTags;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
