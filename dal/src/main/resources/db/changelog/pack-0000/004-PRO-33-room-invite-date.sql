alter table core.rooms_users
    add create_datetime timestamp default now() not null;

comment on column core.rooms_users.create_datetime is 'Дата создания';

