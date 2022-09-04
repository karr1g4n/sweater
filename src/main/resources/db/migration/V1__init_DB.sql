create sequence hibernate_sequence start 2 increment 1;
create table message
(
    id       int8         not null,
    filename varchar(255),
    tag      varchar(255),
    text     varchar(255) not null,
    user_id  int8,
    primary key (id)
);

create table user_role
(
    user_id int8         not null,
    roles   varchar(255) not null
);

create table usr
(
    id              int8         ,
    activation_code varchar(255),
    active          boolean,
    email           varchar(255) not null,
    password        varchar(255) not null,
    username        varchar(255) not null,
    primary key (id)
);

alter table if exists message
    add constraint FK70bv6o4exfe3fbrho7nuotopf
    foreign key (user_id) references usr;

alter table if exists user_role
    add constraint FKfpm8swft53ulq2hl11yplpr5
    foreign key (user_id) references usr;