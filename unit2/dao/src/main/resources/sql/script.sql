create table gift_certificates.gift_certificates
(
    gift_certificate_id bigint auto_increment
        primary key,
    certificate_name    varchar(256) not null,
    description         text         not null,
    price               decimal      not null,
    duration            int          not null,
    create_date         datetime     not null,
    last_update_date    datetime     null
);

create table gift_certificates.tags
(
    tag_id   bigint auto_increment
        primary key,
    tag_name varchar(256) not null
);

create table gift_certificates.gift_certificates_tags
(
    gift_certificate_tag_id bigint auto_increment
        primary key,
    gift_certificate_id_fk  bigint not null,
    tag_id_fk               bigint not null,
    constraint gift_certificate_id
        foreign key (gift_certificate_id_fk) references gift_certificates (gift_certificate_id),
    constraint tag_id
        foreign key (tag_id_fk) references tags (tag_id)
);