create table category (
  id    bigserial not null primary key,
  title varchar(100) unique
);

create table currency (
  id   bigserial not null primary key,
  code varchar(3) unique
);

create table transaction (
  id          bigserial not null primary key,
  amount      double precision,
  description text,
  category_id bigint references category (id),
  currency_id bigint references currency (id),
  created_at  timestamp
);

insert into currency (id, code)
values (1, 'PLN'),
       (2, 'USD'),
       (3, 'EUR');

alter sequence currency_id_seq restart with 4;
