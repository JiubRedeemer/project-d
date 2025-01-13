alter table core.room_user
    add create_datetime timestamp default now() not null;

comment on column core.room_user.create_datetime is 'Дата создания';

