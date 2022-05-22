CREATE DATABASE IF NOT EXISTS frpg;
CREATE USER IF NOT EXISTS 'frpg'@'%' IDENTIFIED WITH mysql_native_password BY 'Database_Password';
CREATE USER IF NOT EXISTS 'frpg'@'localhost' IDENTIFIED WITH mysql_native_password BY 'Database_Password';
FLUSH PRIVILEGES;

GRANT ALL ON frpg.* TO 'frpg'@'%';
FLUSH PRIVILEGES;

UPDATE mysql.user SET Host=' ' WHERE user = 'frpg';

CREATE TABLE frpg.Objectives
(
    objectiveID            INT NOT NULL,
    objectiveDescription   VARCHAR(200),
    questID                INT NOT NULL,
    experiencePointsGained INT,
    completionType         INT,
    completionCriteria     BLOB,
    PRIMARY KEY (questID, objectiveID)
);

CREATE TABLE frpg.Quests
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

CREATE TABLE frpg.QuestStates
(
    playerID            INT     NOT NULL,
    questID             INT     NOT NULL,
    questState          INT     NOT NULL,
    needingNotification BOOLEAN NOT NULL
);

CREATE TABLE frpg.ObjectiveStates
(
    objectiveID         INT NOT NULL,
    questID             INT NOT NULL,
    playerID            INT NOT NULL,
    objectiveState      INT,
    needingNotification BOOLEAN
);

CREATE TABLE frpg.Players
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

CREATE TABLE frpg.NPCs
(
    playerID      INT NOT NULL,
    behaviorClass VARCHAR(80),
    filePath      VARCHAR(80),
    PRIMARY KEY (playerID),
    FOREIGN KEY (playerID) REFERENCES Players (playerID) ON DELETE CASCADE
);

CREATE TABLE frpg.PlayerLogins
(
    playerID   int         NOT NULL,
    playerName VARCHAR(30) NOT NULL,
    password   BLOB        NOT NULL,
    salt       BLOB        NOT NULL,
    PRIMARY KEY (playerID),
    FOREIGN KEY (playerID) REFERENCES Players (playerID) ON DELETE CASCADE
);

CREATE TABLE frpg.PlayerConnection
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

CREATE TABLE frpg.Levels
(
    levelDescription  VARCHAR(30) NOT NULL,
    levelUpPoints     INT         NOT NULL,
    levelUpMonth      INT         NOT NULL,
    levelUpDayOfMonth INT         NOT NULL
);

CREATE TABLE frpg.NPCQuestions
(
    questionID        INT NOT NULL AUTO_INCREMENT,
    questionStatement VARCHAR(256),
    answer            VARCHAR(80),
    startDate         DATE,
    endDate           DATE,
    PRIMARY KEY (questionID)
);

CREATE TABLE frpg.InteractableItems
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

CREATE TABLE frpg.VanityItems
(
    vanityID    INT NOT NULL AUTO_INCREMENT,
    name        VARCHAR(255),
    description VARCHAR(255),
    textureName VARCHAR(255) UNIQUE,
    type        INT NOT NULL,
    PRIMARY KEY (vanityID)
);

CREATE TABLE frpg.Server
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

CREATE TABLE frpg.PlayerLogins
(
    playerID   int         NOT NULL,
    playerName VARCHAR(30) NOT NULL,
    password   BLOB        NOT NULL,
    salt       BLOB        NOT NULL,
    PRIMARY KEY (playerID),
    FOREIGN KEY (playerID) REFERENCES Players (playerID) ON DELETE CASCADE
);

CREATE TABLE frpg.DoubloonPrizes
(
    name        VARCHAR(30)  NOT NULL,
    cost        INT          NOT NULL,
    description VARCHAR(200) NOT NULL
);

CREATE TABLE frpg.RandomFacts
(
    factID    INT         NOT NULL AUTO_INCREMENT,
    factText  VARCHAR(80) NOT NULL,
    npcID     INT         NOT NULL,
    startDate DATE,
    endDate   DATE,
    PRIMARY KEY (factID)
);

CREATE TABLE frpg.VisitedMaps
(
    playerID INT NOT NULL,
    mapID    INT NOT NULL,
    FOREIGN KEY (playerID) REFERENCES Players (playerID) ON DELETE CASCADE,
    FOREIGN KEY (mapID) REFERENCES Server (serverID) ON DELETE CASCADE
);

CREATE TABLE frpg.Friends
(
    playerID int         NOT NULL,
    FRIENDID int         NOT NULL,
    status   varchar(10) NOT NULL
);

CREATE TABLE frpg.VanityInventory
(
    playerID  INT NOT NULL,
    vanityID  INT NOT NULL,
    isWearing INT NOT NULL,
    FOREIGN KEY (playerID) REFERENCES Players (playerID) ON DELETE CASCADE,
    FOREIGN KEY (vanityID) REFERENCES VanityItems (vanityID) ON DELETE CASCADE,
    CONSTRAINT PK_playerID_vanityID PRIMARY KEY (playerID, vanityID)
);

CREATE TABLE frpg.DefaultItems
(
    defaultID      INT NOT NULL UNIQUE,
    defaultWearing INT,
    FOREIGN KEY (defaultID) REFERENCES VanityItems (vanityID) ON DELETE CASCADE
);

CREATE TABLE frpg.VanityAwards
(
    questID INT NOT NULL,
    awardID INT NOT NULL,
    FOREIGN KEY (questID) REFERENCES Quests (questID) ON DELETE CASCADE,
    FOREIGN KEY (awardID) REFERENCES VanityItems (vanityID) ON DELETE CASCADE,
    CONSTRAINT PK_questID_awardID PRIMARY KEY (questID, awardID)
);

CREATE TABLE frpg.VanityShop
(
    vanityID INT NOT NULL UNIQUE,
    price    INT NOT NULL,
    FOREIGN KEY (vanityID) REFERENCES VanityItems (vanityID) ON DELETE CASCADE
);
