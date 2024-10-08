DROP TABLE if exists users cascade;
DROP TABLE if exists user_clan cascade;
DROP TABLE if exists user_devices cascade;
DROP TABLE if exists user_inventory cascade;

CREATE TABLE user_clan
(
    id         INT          NOT NULL,
    user_id    VARCHAR(36)          NOT NULL,
    name       VARCHAR(128) NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE (user_id)
);

CREATE INDEX idx_uc_updated_at ON user_clan (updated_at);
CREATE INDEX idx_uc_created_at ON user_clan (created_at);

CREATE TABLE user_devices
(
    id         INT          NOT NULL GENERATED ALWAYS AS IDENTITY,
    user_id    VARCHAR(36)          NOT NULL,
    model      VARCHAR(128) NOT NULL,
    carrier    VARCHAR(128) NOT NULL,
    firmware   VARCHAR(128) NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE (id, user_id)
);

CREATE INDEX idx_ud_updated_at ON user_devices (updated_at);
CREATE INDEX idx_ud_created_at ON user_devices (created_at);

CREATE TABLE user_inventory
(
    id         INT       NOT NULL GENERATED ALWAYS AS IDENTITY,
    user_id    VARCHAR(36)       NOT NULL,
    cash       INT       NOT NULL,
    coins      INT       NOT NULL,
    items      CLOB,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE (user_id)
);

CREATE INDEX idx_ui_updated_at ON user_inventory (updated_at);
CREATE INDEX idx_ui_created_at ON user_inventory (created_at);

CREATE TABLE users
(
    id                 VARCHAR(36)  NOT NULL PRIMARY KEY,
    credential         VARCHAR(128) NOT NULL,
    level              FLOAT        NOT NULL,
    country            CHAR(2)      NOT NULL,
    language           CHAR(2)      NOT NULL,
    birth_date         TIMESTAMP    NOT NULL,
    gender             VARCHAR(128) NOT NULL,
    total_spent        INT          NOT NULL,
    total_refund       INT          NOT NULL,
    total_transactions INT          NOT NULL,
    last_purchase      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    xp                 INT          NOT NULL,
    last_session       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    custom_field       VARCHAR(128),
    created_at         TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at         TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_users_updated_at ON users (updated_at);
CREATE INDEX idx_users_created_at ON users (created_at);

INSERT INTO users (id, credential, level, country, language, birth_date, gender, total_spent, total_refund,
                   total_transactions, last_purchase, xp, last_session, custom_field)
VALUES ('97983be2-98b7-11e7-90cf-082e5f28d836', 'apple_credential', 3, 'CA', 'fr', '2000-01-10 13:37:17', 'male', 400,
        0, 5, '2021-01-22 13:37:17', 1000, '2021-01-23 13:37:17', 'mycustom');

INSERT INTO user_clan (id, user_id, name)
VALUES (123456, '97983be2-98b7-11e7-90cf-082e5f28d836', 'Hello world clan');

INSERT INTO user_devices (user_id, model, carrier, firmware)
VALUES ('97983be2-98b7-11e7-90cf-082e5f28d836', 'apple iphone 11', 'vodafone', '123');

INSERT INTO user_inventory(user_id, cash, coins, items)
VALUES ('97983be2-98b7-11e7-90cf-082e5f28d836', 123, 123, '[{"item_1" : 1}, {"item_34" : 3}, {"item_55" : 2}]');

INSERT INTO user_device_mapping (user_id, device_id) VALUES ('97983be2-98b7-11e7-90cf-082e5f28d836', 1);
