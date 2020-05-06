create table if not exists FILE_INFO
(
    FILE_ID   int not null primary key auto_increment comment 'file primary key auto increment',
    FILE_NAME varchar(256) comment 'file name',
    FILE_PATH varchar(256) comment 'file path',
    VERSION_ID varchar(256) comment 'version id'
);