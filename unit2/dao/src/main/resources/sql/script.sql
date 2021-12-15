create table gift_certificates.gift_certificates
(
    id               bigint auto_increment
        primary key,
    certificate_name varchar(256) not null,
    description      text         not null,
    price            decimal      not null,
    duration         int          not null,
    create_date      datetime     not null,
    last_update_date datetime     null
);

create table gift_certificates.tags
(
    id       bigint auto_increment
        primary key,
    tag_name varchar(256) not null
);

create table gift_certificates.gift_certificates_tags
(
    id_gift_certificate_tag bigint auto_increment
        primary key,
    gift_certificate_id     bigint not null,
    tag_id                  bigint not null,
    constraint gift_certificates_tags_gift_certificates_id_fk
        foreign key (gift_certificate_id) references gift_certificates.gift_certificates (id),
    constraint gift_certificates_tags_tags_id_fk
        foreign key (tag_id) references gift_certificates.tags (id)
);