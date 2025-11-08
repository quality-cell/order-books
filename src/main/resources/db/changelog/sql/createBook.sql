create table book
(
    id bigserial not null primary key,
    title varchar(255) not null,
    isbn varchar(17) not null,
    author_id bigint not null references author(id) on delete cascade
);

insert into book(title, isbn, author_id) values ('Ангелы и пионеры', '978-5-9691-1681-8', 1);
insert into book(title, isbn, author_id) values ('Ковчег', '978-5-9691-1636-8', 2);
insert into book(title, isbn, author_id) values ('Крокодил, который не плакал', '978-5-9691-1655-9', 3);
insert into book(title, isbn, author_id) values ('Митя Тимкин. Прощай, началка!', '978-5-9691-1680-1', 4);