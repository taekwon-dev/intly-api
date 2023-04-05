CREATE TABLE user
(
    `id`               BIGINT          NOT NULL    AUTO_INCREMENT,
    `username`         VARCHAR(100)    NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE post
(
    `id`               BIGINT          NOT NULL    AUTO_INCREMENT,
    `user_id`          BIGINT          NULL,
    `parent_category`  VARCHAR(20)     NOT NULL,
    `child_category`   VARCHAR(20)     NULL,
    `title`            VARCHAR(255)    NOT NULL,
    `content`          LONGTEXT        NOT NULL,
    `created_at`       TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`       TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE RESTRICT ON UPDATE RESTRICT
);

CREATE TABLE tag
(
    `id`    BIGINT         NOT NULL    AUTO_INCREMENT,
    `name`  VARCHAR(50)    NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE post_tags
(
    `id`       BIGINT    NOT NULL    AUTO_INCREMENT,
    `post_id`  BIGINT    NOT NULL,
    `tag_id`   BIGINT    NOT NULL,
    CONSTRAINT PK_ PRIMARY KEY (id),
    FOREIGN KEY (post_id) REFERENCES post (id) ON DELETE RESTRICT ON UPDATE RESTRICT,
    FOREIGN KEY (tag_id) REFERENCES tag (id) ON DELETE RESTRICT ON UPDATE RESTRICT
);

CREATE TABLE post_likes
(
    `id`       BIGINT    NOT NULL    AUTO_INCREMENT COMMENT '포스트 좋아요 고유 ID',
    `user_id`  BIGINT    NOT NULL    COMMENT '[FK] 유저 고유 ID',
    `post_id`  BIGINT    NOT NULL    COMMENT '[FK] 포스트 고유 ID',
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE RESTRICT ON UPDATE RESTRICT,
    FOREIGN KEY (post_id) REFERENCES post (id) ON DELETE RESTRICT ON UPDATE RESTRICT
);

CREATE TABLE comment
(
    `id`                  BIGINT          NOT NULL    AUTO_INCREMENT,
    `user_id`             BIGINT          NOT NULL,
    `post_id`             BIGINT          NOT NULL,
    `root_parent_id`      BIGINT          NULL,
    `parent_id`           BIGINT          NULL,
    `content`             VARCHAR(100)    NOT NULL,
    `created_at`          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE RESTRICT ON UPDATE RESTRICT,
    FOREIGN KEY (post_id) REFERENCES post (id) ON DELETE RESTRICT ON UPDATE RESTRICT,
    FOREIGN KEY (parent_id) REFERENCES comment (id) ON DELETE RESTRICT ON UPDATE RESTRICT
);