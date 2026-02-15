CREATE TABLE category
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    created_at      datetime              NULL,
    last_updated_at datetime              NULL,
    is_deleted      BIT(1)                NOT NULL,
    name            VARCHAR(255)          NULL,
    `description`   VARCHAR(255)          NULL,
    CONSTRAINT pk_category PRIMARY KEY (id)
);

CREATE TABLE product
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    created_at      datetime              NULL,
    last_updated_at datetime              NULL,
    is_deleted      BIT(1)                NOT NULL,
    title           VARCHAR(255)          NULL,
    price           DOUBLE                NULL,
    `description`   VARCHAR(255)          NULL,
    category_id     BIGINT                NULL,
    image_url       VARCHAR(255)          NULL,
    rating_id       BIGINT                NULL,
    CONSTRAINT pk_product PRIMARY KEY (id)
);

CREATE TABLE rating
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    created_at      datetime              NULL,
    last_updated_at datetime              NULL,
    is_deleted      BIT(1)                NOT NULL,
    rate            DOUBLE                NULL,
    count           INT                   NULL,
    CONSTRAINT pk_rating PRIMARY KEY (id)
);

ALTER TABLE product
    ADD CONSTRAINT FK_PRODUCT_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES category (id);

ALTER TABLE product
    ADD CONSTRAINT FK_PRODUCT_ON_RATING FOREIGN KEY (rating_id) REFERENCES rating (id);