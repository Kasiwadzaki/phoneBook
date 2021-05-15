create table if not exists PERSON
(
    ID BIGINT not null
        primary key,
    NAME VARCHAR(255),
    PHONE_NUMBER VARCHAR(255)
);

create table if not exists CONTACT
(
    ID BIGINT not null
        primary key,
    NAME VARCHAR(255),
    PHONE_NUMBER VARCHAR(255),
    PERSON_ID BIGINT not null,
    constraint FKJBCDAAYHSA4DHCUC5Q0KKW8ET
        foreign key (PERSON_ID) references PERSON (ID)
);

create table if not exists PERSON_CONTACTS
(
    PERSON_ID BIGINT not null,
    CONTACTS_ID BIGINT not null
        constraint UK_2OEBW8NRJF1NF9GMXOX9G8VCS
            unique,
    constraint FK12CO01NUK6FYDSUKV7DDF0FHL
        foreign key (PERSON_ID) references PERSON (ID),
    constraint FKRYF85WPCVFGXHC9HLC5UCPPI2
        foreign key (CONTACTS_ID) references CONTACT (ID)
);

