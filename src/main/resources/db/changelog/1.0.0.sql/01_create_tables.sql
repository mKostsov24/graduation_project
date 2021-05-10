create sequence if not exists user_seq START 25;

CREATE TABLE IF NOT EXISTS users
(
    id           integer      default nextval('user_seq'::regclass) not null
        constraint user_pk
            primary key,
    code         varchar(255) DEFAULT NULL,
    email        varchar(255)                                       NOT NULL,
    is_moderator boolean default false,
    name         varchar(255)                                       NOT NULL,
    password     varchar(255)                                       NOT NULL,
    photo        text         DEFAULT NULL,
    reg_time     timestamp                                          NOT NULL
);

create sequence if not exists post_seq START 56;
CREATE TABLE IF NOT EXISTS posts
(
    id                integer default nextval('post_seq'::regclass) not null
        constraint post_pk
            primary key,
    is_active         boolean                                        NOT NULL,
    moderation_status varchar default 'NEW'                         NOT NULL,
    moderator_id      integer DEFAULT NULL,
    user_id           integer                                       NOT NULL,
    time              DATE                                     NOT NULL,
    title             varchar(255)                                  NOT NULL,
    text              text                                          NOT NULL,
    view_count        integer                                       NOT NULL
);
alter table posts
    add constraint user_id_fk foreign key (user_id) references users (id);
alter table posts
    add constraint moderator_id_fk foreign key (moderator_id) references users (id);

create sequence if not exists post_comment_seq  START 291;
CREATE TABLE IF NOT EXISTS post_comments
(
    id        integer default nextval('post_comment_seq'::regclass) not null
        constraint post_comment_pk
            primary key,
    parent_id integer DEFAULT NULL,
    post_id   integer                                               NOT NULL,
    user_id   integer                                               NOT NULL,
    time      timestamp                                             NOT NULL,
    text      text                                                  NOT NULL
);
alter table post_comments
    add constraint user_id_fk foreign key (user_id) references users (id);
alter table post_comments
    add constraint post_id_fk foreign key (post_id) references posts (id);
alter table post_comments
    add constraint parent_id_fk foreign key (parent_id) references post_comments (id);

create sequence if not exists post_vote_seq START 328;
CREATE TABLE IF NOT EXISTS post_votes
(
    id      integer default nextval('post_vote_seq'::regclass) not null
        constraint post_vote_pk
            primary key,
    post_id integer                                            NOT NULL,
    user_id integer                                            NOT NULL,
    time    timestamp                                          NOT NULL,
    value   integer                                            NOT NULL
);
alter table post_votes
    add constraint user_id_fk foreign key (user_id) references users (id);
alter table post_votes
    add constraint post_id_fk foreign key (post_id) references posts (id);

create sequence if not exists tag_seq START 33;
CREATE TABLE IF NOT EXISTS tags
(
    id      integer default nextval('tag_seq'::regclass) not null
        constraint tag_pk
            primary key,
    name varchar(255)                                        NOT NULL
);

create sequence if not exists tag_to_post_seq START 65;
CREATE TABLE IF NOT EXISTS tag_to_post
(
    id      integer default nextval('tag_to_post_seq'::regclass) not null
        constraint tag_to_post_pk
            primary key,
    post_id integer                                      NOT NULL,
    tag_id integer                                      NOT NULL
);
alter table tag_to_post
    add constraint post_id_fk foreign key (post_id) references posts (id);
alter table tag_to_post
    add constraint tag_id_fk foreign key (tag_id) references tags (id);

create sequence if not exists captcha_code_seq;
CREATE TABLE IF NOT EXISTS captcha_codes
(
    id      integer default nextval('captcha_code_seq'::regclass) not null
        constraint captcha_code_pk
            primary key,
    time date                                    NOT NULL,
    code  varchar(255)                                       NOT NULL,
    secret_code varchar(2048)                                       NOT NULL
);

create sequence if not exists global_setting_seq;
CREATE TABLE IF NOT EXISTS global_settings
(
    id      integer default nextval('global_setting_seq'::regclass) not null
        constraint global_setting_pk
            primary key,

    code  varchar(255)                                       NOT NULL,
    name varchar(255)                                     NOT NULL,
    value varchar(255)                                       NOT NULL
);