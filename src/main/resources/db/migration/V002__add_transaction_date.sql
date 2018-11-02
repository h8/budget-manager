alter table transaction add column "date" date;
update transaction set "date" = created_at;
alter table transaction alter column "date" set not null;