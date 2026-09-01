-- MySQL schema for the ArmanReza currency exchange desktop app.
-- Create database: CREATE DATABASE exchange;

CREATE TABLE IF NOT EXISTS info (
    username    VARCHAR(64)  NOT NULL PRIMARY KEY,
    email       VARCHAR(128) NOT NULL,
    password    VARCHAR(128) NOT NULL,
    firstName   VARCHAR(64),
    lastName    VARCHAR(64),
    phoneNumber VARCHAR(32),
    image       VARCHAR(255),
    WalletID    INT
);

CREATE TABLE IF NOT EXISTS wallet (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    username        VARCHAR(64) NOT NULL,
    euro_currency   DOUBLE DEFAULT 0,
    usd_currency    DOUBLE DEFAULT 0,
    yen_currency    DOUBLE DEFAULT 0,
    toman_currency  DOUBLE DEFAULT 0,
    GBP_currency    DOUBLE DEFAULT 0,
    property        DOUBLE DEFAULT 0
);

CREATE TABLE IF NOT EXISTS currency_rates (
    date  DATE        NOT NULL,
    time  TIME        NOT NULL,
    usd   DOUBLE      NOT NULL,
    euro  DOUBLE      NOT NULL,
    toman DOUBLE      NOT NULL,
    yen   DOUBLE      NOT NULL,
    GBP   DOUBLE      NOT NULL,
    PRIMARY KEY (date, time)
);

CREATE DATABASE IF NOT EXISTS onlineusers;

USE onlineusers;

CREATE TABLE IF NOT EXISTS online_users (
    username VARCHAR(64) NOT NULL PRIMARY KEY
);

USE exchange;

CREATE TABLE IF NOT EXISTS orders (
    id              INT          NOT NULL,
    username        VARCHAR(64)  NOT NULL,
    DstUser         VARCHAR(64),
    date            VARCHAR(32),
    time            VARCHAR(32),
    situation       VARCHAR(32),
    OriginCurrency  VARCHAR(16),
    PurposeCurrency VARCHAR(16),
    amount          DOUBLE,
    sellOrBuy       VARCHAR(32),
    PRIMARY KEY (id, username)
);
