create table book_order
(
    id bigserial not null primary key,
    book_id bigint not null references book(id) on delete cascade,
    client_id bigint not null references client(id) on delete cascade,
    date_begin date not null,
    date_end date default null
);

insert into book_order(book_id, client_id, date_begin) values (1, 1, '2025-10-13');
insert into book_order(book_id, client_id, date_begin) values (2, 2, '2025-06-10');