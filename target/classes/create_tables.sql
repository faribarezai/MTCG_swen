
--DROP TABLES-----------------------------------------------------------------------------------------------------
<<<<<<< HEAD
DROP TABLE IF EXISTS player CASCADE;
DROP TABLE IF EXISTS package CASCADE;
DROP TABLE IF EXISTS card CASCADE;
DROP TABLE IF EXISTS cardType CASCADE;
DROP TABLE IF EXISTS elementType CASCADE;
DROP TABLE IF EXISTS deck CASCADE;
DROP TABLE IF EXISTS battleLog CASCADE;
DROP TABLE IF EXISTS specialty CASCADE;
DROP TABLE IF EXISTS roundLog CASCADE;
=======
DROP TABLE IF EXISTS mUser CASCADE;
DROP TABLE IF EXISTS package CASCADE;
DROP TABLE IF EXISTS card CASCADE;
DROP TABLE IF EXISTS battleLogic CASCADE;
DROP TABLE IF EXISTS cardType CASCADE;
DROP TABLE IF EXISTS elementType CASCADE;;
DROP TABLE IF EXISTS specialty CASCADE;

>>>>>>> c5eb8b5631d66a05baccdca2c975cc2c8944356c

-- Create Enums
CREATE TYPE cardtype AS ENUM ('MONSTER', 'SPELL');
CREATE TYPE elementtype AS ENUM ('FIRE', 'WATER', 'NORMAL');
CREATE TYPE specialty AS ENUM ('GOBLIN', 'DRAGON', 'WIZZARD', 'ORK', 'KNIGHT', 'WATERSPELL', 'FIREELVE', 'KRAKE');


<<<<<<< HEAD
-- Player Table
CREATE TABLE IF NOT EXISTS player (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
=======
-- User Table
CREATE TABLE IF NOT EXISTS mUser (
    userId SERIAL PRIMARY KEY,
    Username VARCHAR(255) NOT NULL,
    Password VARCHAR(255) NOT NULL,
>>>>>>> c5eb8b5631d66a05baccdca2c975cc2c8944356c
    coins INT DEFAULT 20,
    ELOvalue INT DEFAULT 100
    );

<<<<<<< HEAD
ALTER TABLE player
=======
ALTER TABLE mUser
>>>>>>> c5eb8b5631d66a05baccdca2c975cc2c8944356c
    owner TO postgres;

-- Card Table
CREATE TABLE IF NOT EXISTS card (
<<<<<<< HEAD
    id SERIAL PRIMARY KEY,
=======
    cardId SERIAL PRIMARY KEY,
>>>>>>> c5eb8b5631d66a05baccdca2c975cc2c8944356c
    name VARCHAR(255) NOT NULL,
    damage INT NOT NULL,
    element elementtype NOT NULL,
    cardType cardtype NOT NULL
    );

ALTER TABLE card
    owner TO postgres;

-- Package Table
CREATE TABLE IF NOT EXISTS package (
<<<<<<< HEAD
    id SERIAL PRIMARY KEY
=======
    pckgId SERIAL PRIMARY KEY
>>>>>>> c5eb8b5631d66a05baccdca2c975cc2c8944356c
);
ALTER TABLE package
    owner TO postgres;

<<<<<<< HEAD
-- Deck Table
CREATE TABLE IF NOT EXISTS deck (
    id SERIAL PRIMARY KEY
);
ALTER TABLE deck
    owner TO postgres;

-- BattleLog Table
CREATE TABLE IF NOT EXISTS battleLog (
    id SERIAL PRIMARY KEY,
    winner_id INT,
    loser_id INT,
    roundlogs_id INT,
    FOREIGN KEY (winner_id) REFERENCES player (id),
    FOREIGN KEY (loser_id) REFERENCES player (id),
    FOREIGN KEY (roundlogs_id) REFERENCES roundLog (id)
    );

ALTER TABLE battleLog
    owner TO postgres;

-- RoundLog Table
CREATE TABLE IF NOT EXISTS roundLog (
    id SERIAL PRIMARY KEY
);

ALTER TABLE roundLog
    owner TO postgres;




--INSERTS---------------------------------------------------------------------------------------------------------
INSERT INTO player (username, password) VALUES ('admin', 'admin_password');
INSERT INTO player (username, password) VALUES ('user', 'user_password');

INSERT INTO player (username, password) VALUES ('admin', 'admin_password');
INSERT INTO player (username, password) VALUES ('user', 'user_password');

INSERT INTO player (username, password) VALUES ('testuser1', 'password1');
INSERT INTO player (username, password) VALUES ('testuser2', 'password2');

UPDATE player SET coins = (coins -5) WHERE username = 'testuser1';
UPDATE player SET ELOvalue=80 WHERE username = 'testuser1';
UPDATE player SET ELOvalue=120 WHERE username = 'testuser2';

SELECT * FROM player;

--TRUNCATE card CASCADE;
--DELETE FROM card;
--TRUNCATE player CASCADE;
--DELETE FROM player;
=======
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
>>>>>>> c5eb8b5631d66a05baccdca2c975cc2c8944356c
