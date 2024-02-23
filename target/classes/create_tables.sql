
--DROP TABLES-----------------------------------------------------------------------------------------------------
DROP TABLE IF EXISTS mUser CASCADE;
DROP TABLE IF EXISTS package CASCADE;
DROP TABLE IF EXISTS card CASCADE;
DROP TABLE IF EXISTS battle CASCADE;
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
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    coins INT DEFAULT 20,
    elo INT DEFAULT 100,
    bio VARCHAR(255),
    image VARCHAR(255),
    changeName VARCHAR(255)
    );

ALTER TABLE mUser
    owner TO postgres;

-- Card Table
CREATE TABLE IF NOT EXISTS card (
    cardId SERIAL PRIMARY KEY,
    userId INT,
    name VARCHAR(255) NOT NULL,
    damage INT NOT NULL,
    element elementtype NOT NULL,
    cardType cardtype NOT NULL,
    FOREIGN KEY (userId) REFERENCES mUser(userId)
    );

ALTER TABLE card
ADD CONSTRAINT fk_card_user FOREIGN KEY (userId) REFERENCES mUser(userId);


-- Package Table
CREATE TABLE IF NOT EXISTS package (
    pckgId SERIAL PRIMARY KEY,
    cardIds INTEGER[]
);
ALTER TABLE package
    owner TO postgres;

-- Battle Table
CREATE TABLE IF NOT EXISTS battle (
    battleId SERIAL PRIMARY KEY,
    winner_id INT,
    loser_id INT,
    FOREIGN KEY (winner_id) REFERENCES mUser (userId),
    FOREIGN KEY (loser_id) REFERENCES mUser (userId)
    );

ALTER TABLE battle
    owner TO postgres;

---INSERT INTO mUser(username, password, coins, elo) VALUES ('Tim', 'passwords', 20, 100);
