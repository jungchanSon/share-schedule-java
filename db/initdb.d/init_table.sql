CREATE TABLE `schedule`
(
    `schedule_id`    bigint      NOT NULL PRIMARY KEY,
    `calendar_id`    bigint      NOT NULL,
    `user_id`        bigint      NOT NULL,
    `title`          varchar(50) NOT NULL,
    `all_day`        Bool NULL default false,
    `start_datetime` datetime NOT NULL,
    `end_datetime`   datetime NULL,
    `content`        varchar(50) NULL,
    `location`       varchar(50) NULL,
    `created_at`     datetime    NOT NULL,
    `modified_at`    datetime NULL,
    `deleted_at`     datetime NULL
);

CREATE TABLE `favorite_schedule`
(
    `favorite_schedule_id` bigint NOT NULL PRIMARY KEY,
    `user_id`              bigint NOT NULL,
    `schedule_id`          bigint NOT NULL,
    `calendar_id`          bigint NOT NULL,
    `schedule_end_date`    date NULL,
    `created_at`           datetime NOT NULL
);

CREATE TABLE `calendar`
(
    `calendar_id` bigint NOT NULL PRIMARY KEY,
    `is_public`   Bool NULL default false,
    `title`       varchar(50) NOT NULL,
    `content`     varchar(50) NOT NULL,
    `image`       varchar(50) NULL,
    `created_by`  varchar(50) NOT NULL,
    `created_at`  datetime NOT NULL,
    `modified_at` datetime NULL
);

CREATE TABLE `subscribed_calendar`
(
    `subscribed_calendar_id` bigint NOT NULL PRIMARY KEY,
    `calendar_id`            bigint NOT NULL,
    `user_id`                bigint NOT NULL,
    `created_at`             datetime NOT NULL
);

CREATE TABLE `calendar_user`
(
    `calendar_user_id` bigint NOT NULL PRIMARY KEY,
    `user_id`          bigint NOT NULL,
    `calendar_id`      bigint NOT NULL,
    `calendar_auth_id` enum('t1', 't2', 't3', 't4') DEFAULT 't4' NOT NULL,
    `is_black`         bool NULL default false,
    `created_at`       datetime NOT NULL,
    `modified_at`   datetime NULL
);

CREATE TABLE `user`
(
    `user_id`       BIGINT NOT NULL PRIMARY KEY,
    `nickname`      varchar(50) NOT NULL,
    `method`        varchar(50) NOT NULL,
    `ci`            varchar(50) NOT NULL,
    `image`         varchar(50) NULL,
    `registered_at` datetime NOT NULL,
    `modified_at`   datetime NULL
);

CREATE TABLE `schedule_tag`
(
    `schedule_tag_id` bigint NOT NULL PRIMARY KEY,
    `schedule_id`     bigint NOT NULL,
    `tag`             varchar(50) NULL
);

CREATE TABLE `calendar_tag`
(
    `calendar_tag_id` bigint NOT NULL PRIMARY KEY,
    `calendar_id`     bigint NOT NULL,
    `tag`             varchar(50) NULL
);

CREATE TABLE `calendar_user_holiday`
(
    `calendar_user_holiday_id` varchar(255) NOT NULL PRIMARY KEY,
    `calendar_id`              bigint       NOT NULL,
    `user_id`                  bigint       NOT NULL,
    `holiday`                  date NULL,
    `created_at`               datetime NOT NULL,
    `modified_at`               datetime NULL
);

CREATE TABLE `calendar_auth`
(
    `calendar_auth_id` bigint NOT NULL PRIMARY KEY,
    `name`             varchar(50) NULL,
    `sort_no`          int NULL
);

CREATE TABLE `schedule_user`
(
    `schedule_user_id` bigint NOT NULL PRIMARY KEY,
    `schedule_id`      bigint NOT NULL,
    `user_id`          bigint NOT NULL,
    `user_img`         varchar(50) NULL,
    `created_at`       datetime NOT NULL
);

CREATE TABLE `calendar_holiday`
(
    `calendar_holiday_id` bigint NOT NULL PRIMARY KEY,
    `calendar_id`         bigint NOT NULL,
    `holiday`             date NOT NULL,
    `created_at`          datetime NOT NULL,
    `created_by`          varchar(50) NOT NULL
);

-- 외래키 설정
ALTER TABLE schedule
    ADD FOREIGN KEY (calendar_id) REFERENCES calendar (calendar_id);

ALTER TABLE schedule
    ADD FOREIGN KEY (user_id) REFERENCES user (user_id);

ALTER TABLE favorite_schedule
    ADD FOREIGN KEY (user_id) REFERENCES user (user_id);

ALTER TABLE favorite_schedule
    ADD FOREIGN KEY (schedule_id) REFERENCES schedule (schedule_id);

ALTER TABLE favorite_schedule
    ADD FOREIGN KEY (calendar_id) REFERENCES calendar (calendar_id);

ALTER TABLE subscribed_calendar
    ADD FOREIGN KEY (calendar_id) REFERENCES calendar (calendar_id);

ALTER TABLE calendar_user
    ADD FOREIGN KEY (user_id) REFERENCES user (user_id);

ALTER TABLE calendar_user
    ADD FOREIGN KEY (calendar_id) REFERENCES calendar (calendar_id);

ALTER TABLE schedule_tag
    ADD FOREIGN KEY (schedule_id) REFERENCES schedule (schedule_id);

ALTER TABLE calendar_tag
    ADD FOREIGN KEY (calendar_id) REFERENCES calendar (calendar_id);

ALTER TABLE calendar_user_holiday
    ADD FOREIGN KEY (calendar_id) REFERENCES calendar (calendar_id);

ALTER TABLE calendar_user_holiday
    ADD FOREIGN KEY (user_id) REFERENCES user (user_id);

ALTER TABLE schedule_user
    ADD FOREIGN KEY (schedule_id) REFERENCES schedule (schedule_id);

ALTER TABLE schedule_user
    ADD FOREIGN KEY (user_id) REFERENCES user (user_id);

ALTER TABLE calendar_holiday
    ADD FOREIGN KEY (calendar_id) REFERENCES calendar (calendar_id);

-- ALTER TABLE `schedule`
--     ADD CONSTRAINT `PK_SCHEDULE` PRIMARY KEY (
--                                               `schedule_id`
--         );
--
-- ALTER TABLE `유저`
--     ADD CONSTRAINT `PK_유저` PRIMARY KEY (
--                                         `Key`
--         );
--
-- ALTER TABLE `favorite_schedule`
--     ADD CONSTRAINT `PK_FAVORITE_SCHEDULE` PRIMARY KEY (
--                                                        `favorite_schedule_id`
--         );
--
-- ALTER TABLE `calandar`
--     ADD CONSTRAINT `PK_CALANDAR` PRIMARY KEY (
--                                               `calendar_id`
--         );
--
-- ALTER TABLE `subscribed_calendar`
--     ADD CONSTRAINT `PK_SUBSCRIBED_CALENDAR` PRIMARY KEY (
--                                                          `subscribed_calendar_id`
--         );
--
-- ALTER TABLE `calendar_user`
--     ADD CONSTRAINT `PK_CALENDAR_USER` PRIMARY KEY (
--                                                    `calendar_user`
--         );
--
-- ALTER TABLE `user`
--     ADD CONSTRAINT `PK_USER` PRIMARY KEY (
--                                           `user_id`
--         );
--
-- ALTER TABLE `schedule_tag`
--     ADD CONSTRAINT `PK_SCHEDULE_TAG` PRIMARY KEY (
--                                                   `schedule_tag_id`
--         );
--
-- ALTER TABLE `calendar_tag`
--     ADD CONSTRAINT `PK_CALENDAR_TAG` PRIMARY KEY (
--                                                   `calendar_tag_id`
--         );
--
-- ALTER TABLE `calendar_user_holiday`
--     ADD CONSTRAINT `PK_CALENDAR_USER_HOLIDAY` PRIMARY KEY (
--                                                            `calendar_user_holiday_id`
--         );
--
-- ALTER TABLE `calendar_auth`
--     ADD CONSTRAINT `PK_CALENDAR_AUTH` PRIMARY KEY (
--                                                    `calendar_auth_id`
--         );
--
-- ALTER TABLE `schedule_user`
--     ADD CONSTRAINT `PK_SCHEDULE_USER` PRIMARY KEY (
--                                                    `schedule_user_id`
--         );
--
-- ALTER TABLE `calendar_holiday`
--     ADD CONSTRAINT `PK_CALENDAR_HOLIDAY` PRIMARY KEY (
--                                                       `calendar_holiday_id`
--         );