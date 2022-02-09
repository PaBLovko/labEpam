package com.epam.esm;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.math.BigDecimal;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"id", "isAvailable"}, callSuper = false)
@ToString(exclude = {"id"})
@Entity
@EntityListeners(AuditListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "gift_certificates")
public class GiftCertificate extends RepresentationModel<GiftCertificate> {
    @Id
    @Column(name = "gift_certificate_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(value = AccessLevel.NONE)
    private long id;
    @Column(name = "is_available")
    boolean isAvailable;
    @Column(name = "certificate_name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "duration")
    private int duration;
    @Column(name = "create_date")
    private LocalDateTime createDate;
    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "gift_certificates_tags",
            joinColumns = {@JoinColumn(name = "gift_certificate_id_fk")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id_fk")}
    )
    private Set<Tag> tags;
}
