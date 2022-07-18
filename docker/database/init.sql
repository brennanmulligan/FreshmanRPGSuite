CREATE DATABASE IF NOT EXISTS frpg;
CREATE USER IF NOT EXISTS 'frpg'@'%' IDENTIFIED WITH mysql_native_password BY 'Database_Password';
CREATE USER IF NOT EXISTS 'frpg'@'localhost' IDENTIFIED WITH mysql_native_password BY 'Database_Password';
FLUSH PRIVILEGES;

GRANT ALL ON frpg.* TO 'frpg'@'%';
FLUSH PRIVILEGES;

# UPDATE mysql.user SET Host=' ' WHERE user = 'frpg';

CREATE TABLE IF NOT EXISTS frpg.Objectives
(
    objectiveID            INT NOT NULL,
    objectiveDescription   VARCHAR(200),
    questID                INT NOT NULL,
    experiencePointsGained INT,
    completionType         INT,
    completionCriteria     BLOB,
    PRIMARY KEY (questID, objectiveID)
);

CREATE TABLE IF NOT EXISTS frpg.Quests
(
    questID                   INT NOT NULL AUTO_INCREMENT,
    questTitle                VARCHAR(40) UNIQUE,
    questDescription          VARCHAR(200),
    triggerMapName            VARCHAR(80),
    triggerRow                INT,
    triggerColumn             INT,
    experiencePointsGained    INT,
    objectivesForFulfillment  INT,
    completionActionType      INT,
    completionActionParameter BLOB,
    startDate                 DATE,
    endDate                   DATE,
    PRIMARY KEY (questID)
);

CREATE TABLE IF NOT EXISTS frpg.QuestStates
(
    playerID            INT     NOT NULL,
    questID             INT     NOT NULL,
    questState          INT     NOT NULL,
    needingNotification BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS frpg.ObjectiveStates
(
    objectiveID         INT NOT NULL,
    questID             INT NOT NULL,
    playerID            INT NOT NULL,
    objectiveState      INT,
    needingNotification BOOLEAN
);

CREATE TABLE IF NOT EXISTS frpg.Players
(
    playerID         INT     NOT NULL AUTO_INCREMENT,
    appearanceType   VARCHAR(255),
    quizScore        INTEGER,
    experiencePoints INTEGER,
    crew             INTEGER NOT NULL,
    major            INTEGER NOT NULL,
    section          INTEGER NOT NULL,
    buffPool         INT,
    online           BOOL,
    PRIMARY KEY (playerID)
);

CREATE TABLE IF NOT EXISTS frpg.NPCs
(
    playerID      INT NOT NULL,
    behaviorClass VARCHAR(80),
    filePath      VARCHAR(80),
    PRIMARY KEY (playerID),
    FOREIGN KEY (playerID) REFERENCES Players (playerID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS frpg.PlayerLogins
(
    playerID   int         NOT NULL,
    playerName VARCHAR(30) NOT NULL,
    password   BLOB        NOT NULL,
    salt       BLOB        NOT NULL,
    PRIMARY KEY (playerID),
    FOREIGN KEY (playerID) REFERENCES Players (playerID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS frpg.PlayerConnection
(
    playerID   INT       NOT NULL,
    Pin        DOUBLE    NOT NULL,
    changed_on timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    mapName    VARCHAR(30)        DEFAULT NULL,
    playerRow  int(11)            DEFAULT NULL,
    playerCol  int(11)            DEFAULT NULL,
    PRIMARY KEY (playerID),
    FOREIGN KEY (playerID) REFERENCES Players (playerID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS frpg.Levels
(
    levelDescription  VARCHAR(30) NOT NULL,
    levelUpPoints     INT         NOT NULL,
    levelUpMonth      INT         NOT NULL,
    levelUpDayOfMonth INT         NOT NULL
);

CREATE TABLE IF NOT EXISTS frpg.NPCQuestions
(
    questionID        INT NOT NULL AUTO_INCREMENT,
    questionStatement VARCHAR(256),
    answer            VARCHAR(80),
    startDate         DATE,
    endDate           DATE,
    PRIMARY KEY (questionID)
);

CREATE TABLE IF NOT EXISTS frpg.InteractableItems
(
    itemID      INT         NOT NULL AUTO_INCREMENT,
    name        VARCHAR(30) NOT NULL,
    xPosition   INT         NOT NULL,
    yPosition   INT         NOT NULL,
    actionType  INT         NOT NULL,
    actionParam BLOB        NOT NULL,
    mapName     VARCHAR(30) NOT NULL,
    PRIMARY KEY (itemID)
);

CREATE TABLE IF NOT EXISTS frpg.VanityItems
(
    vanityID    INT NOT NULL AUTO_INCREMENT,
    name        VARCHAR(255),
    description VARCHAR(255),
    textureName VARCHAR(255) UNIQUE,
    type        INT NOT NULL,
    PRIMARY KEY (vanityID)
);

CREATE TABLE IF NOT EXISTS frpg.Server
(
    serverID          INT NOT NULL AUTO_INCREMENT,
    hostName          varchar(80),
    portNumber        INT,
    mapName           varchar(80) UNIQUE,
    mapTitle          varchar(80) UNIQUE,
    teleportPositionX INT DEFAULT 0,
    teleportPositionY INT DEFAULT 0,
    PRIMARY KEY (serverID)
);

INSERT INTO frpg.Server (serverID, hostName, portNumber, mapName, mapTitle, teleportPositionX, teleportPositionY) VALUES (1, 'host1.com', 1871, 'map1.tmx', 'Map1', 10, 10);
INSERT INTO frpg.Server (serverID, hostName, portNumber, mapName, mapTitle, teleportPositionX, teleportPositionY) VALUES (2, 'host2.com', 1872, 'map2.tmx', 'Map2', 10, 10);
INSERT INTO frpg.Server (serverID, hostName, portNumber, mapName, mapTitle, teleportPositionX, teleportPositionY) VALUES (3, 'localhost', 1873, 'recCenter.tmx', 'Rec Center', 4, 20);
INSERT INTO frpg.Server (serverID, hostName, portNumber, mapName, mapTitle, teleportPositionX, teleportPositionY) VALUES (4, 'localhost', 1874, 'library.tmx', 'Library', 48, 22);
INSERT INTO frpg.Server (serverID, hostName, portNumber, mapName, mapTitle, teleportPositionX, teleportPositionY) VALUES (5, 'localhost', 1875, 'sortingRoom.tmx', 'SortingRoom', 7, 12);
INSERT INTO frpg.Server (serverID, hostName, portNumber, mapName, mapTitle, teleportPositionX, teleportPositionY) VALUES (6, 'localhost', 1876, 'wellingtonRoom.tmx', 'WellingtonRoom', 6, 89);
INSERT INTO frpg.Server (serverID, hostName, portNumber, mapName, mapTitle, teleportPositionX, teleportPositionY) VALUES (7, 'localhost', 1877, 'mct1.tmx', 'MCT1', 33, 55);
INSERT INTO frpg.Server (serverID, hostName, portNumber, mapName, mapTitle, teleportPositionX, teleportPositionY) VALUES (8, 'localhost', 1879, 'Ducktopia.tmx', 'Ducktopia', 28, 56);
INSERT INTO frpg.Server (serverID, hostName, portNumber, mapName, mapTitle, teleportPositionX, teleportPositionY) VALUES (9, 'localhost', 1880, 'cub.tmx', 'Cub', 59, 94);
INSERT INTO frpg.Server (serverID, hostName, portNumber, mapName, mapTitle, teleportPositionX, teleportPositionY) VALUES (10, 'localhost', 1881, 'mowrey.tmx', 'Mowrey', 29, 36);
INSERT INTO frpg.Server (serverID, hostName, portNumber, mapName, mapTitle, teleportPositionX, teleportPositionY) VALUES (11, 'localhost', 1882, 'outsideOfMowrey.tmx', 'OutsideMowrey', 2, 94);
INSERT INTO frpg.Server (serverID, hostName, portNumber, mapName, mapTitle, teleportPositionX, teleportPositionY) VALUES (12, 'localhost', 1883, 'quad.tmx', 'Quad', 52, 52);
INSERT INTO frpg.Server (serverID, hostName, portNumber, mapName, mapTitle, teleportPositionX, teleportPositionY) VALUES (13, 'localhost', 1884, 'dhc1.tmx', 'DHC1', 44, 49);

CREATE TABLE IF NOT EXISTS frpg.DoubloonPrizes
(
    name        VARCHAR(30)  NOT NULL,
    cost        INT          NOT NULL,
    description VARCHAR(200) NOT NULL
);

CREATE TABLE IF NOT EXISTS frpg.RandomFacts
(
    factID    INT         NOT NULL AUTO_INCREMENT,
    factText  VARCHAR(80) NOT NULL,
    npcID     INT         NOT NULL,
    startDate DATE,
    endDate   DATE,
    PRIMARY KEY (factID)
);

CREATE TABLE IF NOT EXISTS frpg.VisitedMaps
(
    playerID INT NOT NULL,
    mapID    INT NOT NULL,
    FOREIGN KEY (playerID) REFERENCES Players (playerID) ON DELETE CASCADE,
    FOREIGN KEY (mapID) REFERENCES Server (serverID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS frpg.Friends
(
    playerID int         NOT NULL,
    FRIENDID int         NOT NULL,
    status   varchar(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS frpg.VanityInventory
(
    playerID  INT NOT NULL,
    vanityID  INT NOT NULL,
    isWearing INT NOT NULL,
    FOREIGN KEY (playerID) REFERENCES Players (playerID) ON DELETE CASCADE,
    FOREIGN KEY (vanityID) REFERENCES VanityItems (vanityID) ON DELETE CASCADE,
    CONSTRAINT PK_playerID_vanityID PRIMARY KEY (playerID, vanityID)
);

CREATE TABLE IF NOT EXISTS frpg.DefaultItems
(
    defaultID      INT NOT NULL UNIQUE,
    defaultWearing INT,
    FOREIGN KEY (defaultID) REFERENCES VanityItems (vanityID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS frpg.VanityAwards
(
    questID INT NOT NULL,
    awardID INT NOT NULL,
    FOREIGN KEY (questID) REFERENCES Quests (questID) ON DELETE CASCADE,
    FOREIGN KEY (awardID) REFERENCES VanityItems (vanityID) ON DELETE CASCADE,
    CONSTRAINT PK_questID_awardID PRIMARY KEY (questID, awardID)
);

CREATE TABLE IF NOT EXISTS frpg.VanityShop
(
    vanityID INT NOT NULL UNIQUE,
    price    INT NOT NULL,
    FOREIGN KEY (vanityID) REFERENCES VanityItems (vanityID) ON DELETE CASCADE
);
