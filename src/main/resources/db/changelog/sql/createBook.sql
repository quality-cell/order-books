create table book
(
    id bigserial not null primary key,
    title varchar(255) not null,
    isbn varchar(17) not null,
    author varchar(500) not null
);

insert into book(title, isbn, author) values ('Ангелы и пионеры', '978-5-9691-1681-8', 'Драгунская Ксения Викторовна');
insert into book(title, isbn, author) values ('Ковчег', '978-5-9691-1636-8', 'Фетисов Егор Сергеевич');
insert into book(title, isbn, author) values ('Крокодил, который не плакал', '978-5-9691-1655-9', 'Усачев Андрей Алексеевич');
insert into book(title, isbn, author) values ('Митя Тимкин. Прощай, началка!', '978-5-9691-1680-1', 'Тимашпольская Екатерина Борисовна');