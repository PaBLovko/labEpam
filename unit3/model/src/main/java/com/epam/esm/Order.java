package com.epam.esm;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@EqualsAndHashCode(exclude = {"id"}, callSuper = false)
@ToString(exclude = {"id"})
@Entity
@EntityListeners(AuditListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order extends RepresentationModel<Order> {
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(value = AccessLevel.NONE)
    private long id;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "timestamp")
    private LocalDateTime timestamp;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "gift_certificate_id_fk"
    )
    private GiftCertificate giftCertificate;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "user_id_fk"
    )
    private User user;
}
