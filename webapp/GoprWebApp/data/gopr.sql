DROP TABLE IF EXISTS T_AREA, 
T_MESSAGE_TO_USER, 
T_MESSAGE, 
T_RESOURCE, 
T_POSITION, 
T_NOTE, 
T_ACTION,  
T_USER_IN_ACTION, 
T_GROUP, 
T_GROUP_AREA,
T_USER,
T_ROLE;

CREATE TABLE T_ROLE (
ID SERIAL PRIMARY KEY,
NAME varchar(25) NOT NULL,
COLOR varchar(25),
PICTOGRAM varchar(25));

CREATE TABLE T_USER (
ID SERIAL PRIMARY KEY,
NAME varchar(25) NOT NULL,
SURNAME varchar(25) NOT NULL,
LOGIN varchar(25) NOT NULL,
PASSWORD varchar(25) NOT NULL,
PHONE varchar(12) NOT NULL,
NICK varchar(25) NOT NULL,
ROLE_ID int8 REFERENCES T_ROLE(ID));

CREATE TABLE T_ACTION (
ID SERIAL PRIMARY KEY,
NAME varchar(100) NOT NULL,
START_DATE timestamp,
END_DATE timestamp,
DESCRIPTION varchar(250),
COMMENTS varchar(250),
STATIC_DATABASE_ID int8,
ISRID_DATABASE_ID int8);

CREATE TABLE T_AREA (
ID SERIAL PRIMARY KEY,
NAME varchar(25) NOT NULL,
DATE_TIME timestamp,
DATA varchar NOT NULL,
NUMBER int NOT NULL,
IS_ACTIVE boolean NOT NULL,
ACTION_ID int8 REFERENCES T_ACTION(ID));

CREATE table T_GROUP (
ID SERIAL PRIMARY KEY,
NAME varchar(25) NOT NULL,
COLOR varchar(25),
PICTOGRAM varchar(25),
ACTION_ID int8 REFERENCES T_ACTION(ID));

CREATE TABLE T_GROUP_AREA (
GROUP_ID int8 NOT NULL,
AREA_ID int8 NOT NULL,
PRIMARY KEY (GROUP_ID, AREA_ID),
CONSTRAINT FK_GROUP_ID FOREIGN KEY (GROUP_ID) REFERENCES T_GROUP(ID),
CONSTRAINT FK_AREA_ID FOREIGN KEY (AREA_ID) REFERENCES T_AREA(ID));

CREATE TABLE T_USER_IN_ACTION (
ID SERIAL PRIMARY KEY,
PHONE varchar(12) NOT NULL,
COLOR varchar(25),
PICTOGRAM varchar(25),
GROUP_ID int8 REFERENCES T_GROUP(ID),
ROLE_ID int8 REFERENCES T_ROLE(ID),
USER_ID int8 REFERENCES T_USER(ID));

CREATE TABLE T_RESOURCE (
ID SERIAL PRIMARY KEY,
DATA bytea NOT NULL,
TYPE varchar(10) NOT NULL);

CREATE TABLE T_POSITION (
ID SERIAL PRIMARY KEY,
USER_IN_ACTION_ID int8 REFERENCES T_USER_IN_ACTION(ID),
LATITUDE float NOT NULL,
LONGITUDE float NOT NULL, 
DATE_TIME timestamp NOT NULL);

CREATE TABLE T_NOTE (
ID SERIAL PRIMARY KEY,
POSITION_ID int8 REFERENCES T_POSITION(ID),
TEXT varchar(100),
RESOURCE_ID int8 REFERENCES T_RESOURCE);

CREATE TABLE T_MESSAGE (
ID SERIAL PRIMARY KEY,
DATE_TIME timestamp NOT NULL,
TEXT varchar(250),
RECEIVED boolean,
FROM_USER_ID int8 REFERENCES T_USER_IN_ACTION(ID),
RESOURCE_ID int8 REFERENCES T_RESOURCE(ID));

CREATE TABLE T_MESSAGE_TO_USER (
TO_USER_ID int8 REFERENCES T_USER_IN_ACTION(ID),
MESSAGE_ID int8 REFERENCES T_MESSAGE(ID),
CONSTRAINT MESSAGE_TO_USER_ID PRIMARY KEY (MESSAGE_ID, TO_USER_ID))