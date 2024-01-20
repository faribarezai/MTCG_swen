
--DROP TABLES-----------------------------------------------------------------------------------------------------
DROP TABLE IF EXISTS mUser CASCADE;
DROP TABLE IF EXISTS package CASCADE;
DROP TABLE IF EXISTS card CASCADE;
DROP TABLE IF EXISTS battleLogic CASCADE;
DROP TABLE IF EXISTS cardType CASCADE;
DROP TABLE IF EXISTS elementType CASCADE;;
DROP TABLE IF EXISTS specialty CASCADE;


-- Create Enums
CREATE TYPE cardtype AS ENUM ('MONSTER', 'SPELL');
CREATE TYPE elementtype AS ENUM ('FIRE', 'WATER', 'NORMAL');
CREATE TYPE specialty AS ENUM ('GOBLIN', 'DRAGON', 'WIZZARD', 'ORK', 'KNIGHT', 'WATERSPELL', 'FIREELVE', 'KRAKE');


-- User Table
CREATE TABLE IF NOT EXISTS mUser (
    userId SERIAL PRIMARY KEY,
    Username VARCHAR(255) NOT NULL,
    Password VARCHAR(255) NOT NULL,
    coins INT DEFAULT 20,
    ELOvalue INT DEFAULT 100
    );

ALTER TABLE mUser
    owner TO postgres;

-- Card Table
CREATE TABLE IF NOT EXISTS card (
    cardId SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    damage INT NOT NULL,
    element elementtype NOT NULL,
    cardType cardtype NOT NULL
    );

ALTER TABLE card
    owner TO postgres;

-- Package Table
CREATE TABLE IF NOT EXISTS package (
    pckgId SERIAL PRIMARY KEY
);
ALTER TABLE package
    owner TO postgres;

-- BattleLog Table
CREATE TABLE IF NOT EXISTS battleLogic (
    battleId SERIAL PRIMARY KEY,
    winner_id INT,
    loser_id INT,
    FOREIGN KEY (winner_id) REFERENCES mUser (userId),
    FOREIGN KEY (loser_id) REFERENCES mUser (userId)
    );

ALTER TABLE battleLogic
    owner TO postgres;