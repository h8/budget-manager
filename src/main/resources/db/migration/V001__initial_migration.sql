create table category (
  id    bigserial    not null primary key,
  title varchar(100) not null unique check (title <> '')
);

create table currency (
  id   bigserial  not null primary key,
  code varchar(3) not null unique check (length(code) = 3)
);

create table account (
  id          bigserial    not null primary key,
  title       varchar(100) not null unique check (title <> ''),
  currency_id bigint references currency (id),
  description text
);

create table transaction (
  id          bigserial not null primary key,
  account_id  bigint    not null references account (id),
  amount      double precision,
  description text,
  category_id bigint references category (id),
  created_at  timestamp
);

insert into currency (id, code)
values (1, 'PLN'),
       (2, 'USD'),
       (3, 'EUR');

alter sequence currency_id_seq
  restart with 4;
