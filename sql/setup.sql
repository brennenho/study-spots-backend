DROP DATABASE IF EXISTS StudySpots;
CREATE DATABASE StudySpots;
USE StudySpots;

CREATE TABLE AllStudySpots(
	spot_id int NOT NULL AUTO_INCREMENT,
	name varchar(255),
	address varchar(255),
	image varchar(255),
	latitude decimal(10, 8),
	longitude decimal(11, 8),
	rating decimal(3,1),
	PRIMARY KEY (spot_id)
);
CREATE TABLE Tags(
	tag_id int NOT NULL AUTO_INCREMENT,
	name varchar(50),
    PRIMARY KEY (tag_id)
);

CREATE TABLE SpotTag(
	spot_id int NOT NULL,
    tag_id int NOT NULL,
	FOREIGN KEY (spot_id) REFERENCES AllStudySpots(spot_id),
	FOREIGN KEY (tag_id) REFERENCES Tags(tag_id)
);



INSERT INTO Tags(name)
VALUES ('Historic Building'), ('Wifi'), ('AC'), ('Research Resources'), ('Coffee'), ('Food Available'), ('Casual Environment'),
('Silent Areas'), ('24/7'), ('Group Rooms');


CREATE TABLE Users (
	user_id int NOT NULL AUTO_INCREMENT,
    email varchar(255),
    name varchar(255),
    password varchar(255),
    PRIMARY KEY (user_id)
);