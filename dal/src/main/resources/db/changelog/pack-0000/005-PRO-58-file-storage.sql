alter table core.room
    add filePath text null;

comment on column core.room.filePath is 'Ссылка на аватарку в ФХ';

