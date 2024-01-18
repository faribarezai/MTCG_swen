
--DROP TABLES-----------------------------------------------------------------------------------------------------
DROP TABLE IF EXISTS player CASCADE;
DROP TABLE IF EXISTS package CASCADE;
DROP TABLE IF EXISTS card CASCADE;
DROP TABLE IF EXISTS cardType CASCADE;
DROP TABLE IF EXISTS elementType CASCADE;
DROP TABLE IF EXISTS deck CASCADE;
DROP TABLE IF EXISTS battleLog CASCADE;
DROP TABLE IF EXISTS specialty CASCADE;
DROP TABLE IF EXISTS roundLog CASCADE;

-- Create Enums
CREATE TYPE cardtype AS ENUM ('MONSTER', 'SPELL');
CREATE TYPE elementtype AS ENUM ('FIRE', 'WATER', 'NORMAL');
CREATE TYPE specialty AS ENUM ('GOBLIN', 'DRAGON', 'WIZZARD', 'ORK', 'KNIGHT', 'WATERSPELL', 'FIREELVE', 'KRAKE');


-- Player Table
CREATE TABLE IF NOT EXISTS player (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    coins INT DEFAULT 20,
    ELOvalue INT DEFAULT 100
    );

ALTER TABLE player
    owner TO postgres;

-- Card Table
CREATE TABLE IF NOT EXISTS card (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    damage INT NOT NULL,
    element elementtype NOT NULL,
    cardType cardtype NOT NULL
    );

ALTER TABLE card
    owner TO postgres;

-- Package Table
CREATE TABLE IF NOT EXISTS package (
    id SERIAL PRIMARY KEY
);
ALTER TABLE package
    owner TO postgres;

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
