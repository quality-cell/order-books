create table client
(
    id bigserial not null primary key,
    surname varchar(255) not null,
    first_name varchar(255) not null,
    patronymic varchar(255),
    birthday date not null
);

insert into client(surname, first_name, patronymic, birthday) values ('Иванов', 'Иван', 'Иванович', '04.05.1999');
insert into client(surname, first_name, patronymic, birthday) values ('Петров', 'Петр', 'Петрович', '01.01.2001');
insert into client(surname, first_name, patronymic, birthday) values ('Пупкин', 'Василий', 'Васильевич', '06.11.2000');
insert into client(surname, first_name, patronymic, birthday) values ('Иванов', 'Иван', 'Иванович', '04.12.2000');