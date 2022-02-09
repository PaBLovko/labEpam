package com.epam.esm;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"id"}, callSuper = false)
@ToString(exclude = {"id"})
@Entity
@EntityListeners(AuditListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User extends RepresentationModel<User> {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(value = AccessLevel.NONE)
    private long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
}
