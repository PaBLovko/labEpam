package com.epam.esm;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {"id", "isAvailable"}, callSuper = false)
@ToString(exclude = {"id"})
@Entity
@EntityListeners(AuditListener.class)
@Table(name = "tags")
public class Tag extends RepresentationModel<Tag> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    @Setter(value = AccessLevel.NONE)
    private long id;
    @Column(name = "tag_name")
    private String name;
    @Column(name = "is_available")
    boolean isAvailable;
    public Tag(String name) {
        this.name = name;
    }
}
