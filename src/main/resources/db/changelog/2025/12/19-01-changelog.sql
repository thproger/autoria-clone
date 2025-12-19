-- liquibase formatted sql

-- changeset Acer:1766133944939-1
CREATE TABLE model
(
    id   BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255) NULL,
    CONSTRAINT pk_model PRIMARY KEY (id)
);

-- changeset Acer:1766133944939-2
CREATE TABLE offer
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    user_id     BIGINT NULL,
    title       VARCHAR(255) NULL,
    body        VARCHAR(255) NULL,
    price DOUBLE NOT NULL,
    model_id    BIGINT NULL,
    currency    VARCHAR(255) NULL,
    region      VARCHAR(255) NULL,
    is_active   BIT(1) NOT NULL,
    inspections INT    NOT NULL,
    CONSTRAINT pk_offer PRIMARY KEY (id)
);

-- changeset Acer:1766133944939-3
CREATE TABLE user
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    first_name    VARCHAR(255) NULL,
    last_name     VARCHAR(255) NULL,
    password      VARCHAR(255) NULL,
    email         VARCHAR(255) NULL,
    `role`        VARCHAR(255) NULL,
    refresh_token VARCHAR(255) NULL,
    is_premium    BIT(1) NOT NULL,
    is_blocked    BIT(1) NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

-- changeset Acer:1766133944939-4
CREATE TABLE view
(
    id       BIGINT NOT NULL,
    offer_id BIGINT NULL,
    date     date NULL,
    CONSTRAINT pk_view PRIMARY KEY (id)
);

-- changeset Acer:1766133944939-5
ALTER TABLE model
    ADD CONSTRAINT uc_model_name UNIQUE (name);

-- changeset Acer:1766133944939-6
ALTER TABLE user
    ADD CONSTRAINT uc_user_email UNIQUE (email);

-- changeset Acer:1766133944939-7
ALTER TABLE offer
    ADD CONSTRAINT FK_OFFER_ON_MODEL FOREIGN KEY (model_id) REFERENCES model (id);

-- changeset Acer:1766133944939-8
ALTER TABLE offer
    ADD CONSTRAINT FK_OFFER_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);

-- changeset Acer:1766133944939-9
ALTER TABLE view
    ADD CONSTRAINT FK_VIEW_ON_OFFER FOREIGN KEY (offer_id) REFERENCES offer (id);

