
CREATE TABLE `User` (
	`user_id`	VARCHAR(255)	NOT NULL,
	`name`	VARCHAR(10)	NOT NULL,
	`password`	VARCHAR(255)	NOT NULL,
	`nickname`	VARCHAR(255)	NOT NULL,
	`quesion_set`	INT	NULL,
	`ranking_id`	INT	NULL
);

CREATE TABLE `Scrap` (
	`scrap_id`	BIGINT	NOT NULL,
	`user_id`	VARCHAR(255)	NOT NULL,
	`question_id`	BIGINT	NOT NULL
);

CREATE TABLE `Wrong` (
	`wrong_id`	BIGINT	NOT NULL,
	`user_id`	VARCHAR(255)	NOT NULL,
	`question_id`	BIGINT	NOT NULL,
	`wrong`	TINYINT(1)	NOT NULL
);

CREATE TABLE `Question` (
	`question_id`	BIGINT	NOT NULL,
	`wrong`	TINYINT(1)	NOT NULL,
	`user_id`	VARCHAR(255)	NOT NULL,
	`question`	VARCHAR(255)	NOT NULL,
	`classification`	VARCHAR(255)	NOT NULL,
	`answer`	VARCHAR(255)	NOT NULL
);

ALTER TABLE `User` ADD CONSTRAINT `PK_USER` PRIMARY KEY (
	`user_id`
);

ALTER TABLE `Scrap` ADD CONSTRAINT `PK_SCRAP` PRIMARY KEY (
	`scrap_id`
);

ALTER TABLE `Wrong` ADD CONSTRAINT `PK_WRONG` PRIMARY KEY (
	`wrong_id`
);
#안되네..
ALTER TABLE  `Wrong` ADD CONSTRAINT `UK_WRONG` UNIQUE KEY  (
    `wrong`

    );



ALTER TABLE `Question` ADD CONSTRAINT `PK_QUESTION` PRIMARY KEY (
	`question_id`,
	`wrong`
);


ALTER TABLE `Scrap` ADD CONSTRAINT `FK_User_TO_Scrap_1` FOREIGN KEY (
	`user_id`
)
REFERENCES `User` (
	`user_id`
);

ALTER TABLE `Scrap` ADD CONSTRAINT `FK_Question_TO_Scrap_1` FOREIGN KEY (
	`question_id`
)
REFERENCES `Question` (
	`question_id`
);

ALTER TABLE `Wrong` ADD CONSTRAINT `FK_User_TO_Wrong_1` FOREIGN KEY (
	`user_id`
)
REFERENCES `User` (
	`user_id`
);

ALTER TABLE `Wrong` ADD CONSTRAINT `FK_Question_TO_Wrong` FOREIGN KEY (
	`question_id`, `wrong`
)
REFERENCES `Question` (
	`question_id` , `wrong`
);


ALTER TABLE `Question` ADD CONSTRAINT `FK_User_TO_Question_1` FOREIGN KEY (
	`user_id`
)
REFERENCES `User` (
	`user_id`
);

# test용 table 생성

create table testQuestion
(
    question_id    bigint                      not null,
    wrong          tinyint(1)                  not null,
    user_id        varchar(255) not null,
    question       varchar(255) not null,
    classification varchar(255) not null,
    answer         varchar(255) not null,
    primary key (question_id, wrong)
)default character set utf8 collate utf8_general_ci;




create table testWrong
(
    wrong_id    int          not null,
    question_id bigint          not null,
    user_id     varchar(255) not null,
    constraint testWrong_pk
        primary key (wrong_id)
)default character set utf8 collate utf8_general_ci;

