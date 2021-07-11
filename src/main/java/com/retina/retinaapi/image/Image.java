package com.retina.retinaapi.image;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private String resourceLink;

    @ManyToOne
    private User owner;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST
            },
            mappedBy = "images"
    )
    @JsonIgnore
    private Set<ImageTag> imageTags =  new HashSet<>();

    public Image() {
    }

    public Image(String name, String description, String resourceLink) {
        this.name = name;
        this.description = description;
        this.resourceLink = resourceLink;
    }

    public Image(Long id, String name, String description, String resourceLink) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.resourceLink = resourceLink;
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

    public String getResourceLink() {
        return resourceLink;
    }

    public User getOwner() {
        return owner;
    }

    @JsonIgnore
    public Set<ImageTag> getTags() {
        return imageTags;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setResourceLink(String resourceLink) {
        this.resourceLink = resourceLink;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
