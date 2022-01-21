create schema gift_certificates;

create table gift_certificates
(
    gift_certificate_id bigint auto_increment
        primary key,
    certificate_name    varchar(256)    not null,
    description         text            not null,
    price               decimal(10, 2)  not null,
    duration            int             not null,
    create_date         datetime        not null,
    last_update_date    datetime        null
);

create table tags
(
    tag_id   bigint auto_increment
        primary key,
    tag_name varchar(256) not null
);

create table gift_certificates_tags
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

create table users
(
    user_id     bigint not null auto_increment,
    first_name  varchar(256) not null,
    last_name   varchar(256) not null,
    email       varchar(256) not null,
    primary key (user_id)
);

create table orders
(
    order_id                bigint not null auto_increment,
    price                   decimal(10, 2) not null,
    timestamp               datetime not null,
    gift_certificate_id_fk  bigint not null references gift_certificates (gift_certificate_id),
    user_id_fk              bigint not null references users (user_id),
    primary key (order_id)
)