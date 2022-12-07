CREATE TABLE IF NOT EXISTS user_info
(
    id       bigserial,
    username varchar(255),
    password varchar(255),
    role     varchar(11),
    PRIMARY KEY (id),
    CONSTRAINT unique_app_user_name UNIQUE (username)
);

CREATE TABLE IF NOT EXISTS files_info
(
    id                 bigserial,
    file_name_original varchar(255),
    file_name_unique   varchar(255),
    path               varchar(255),
    description        varchar(255),
    PRIMARY KEY (id)
);