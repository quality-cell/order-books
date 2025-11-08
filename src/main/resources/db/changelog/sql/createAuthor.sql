create table author
(
    id bigserial not null primary key,
    surname varchar(255) not null,
    first_name varchar(255) not null,
    patronymic varchar(255),
    birthday date not null
);

insert into author(surname, first_name, patronymic, birthday) values ('Драгунская', 'Ксения', 'Викторовна', '04.05.1999');
insert into author(surname, first_name, patronymic, birthday) values ('Фетисов', 'Егор', 'Сергеевич', '01.01.2001');
insert into author(surname, first_name, patronymic, birthday) values ('Усачев', 'Андрей', 'Алексеевич', '06.11.2000');
insert into author(surname, first_name, patronymic, birthday) values ('Тимашпольская', 'Екатерина', 'Борисовна', '04.12.2000');