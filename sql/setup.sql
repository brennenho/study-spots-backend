DROP DATABASE IF EXISTS StudySpots;
CREATE DATABASE StudySpots;
USE StudySpots;

CREATE TABLE Users (
	user_id int NOT NULL AUTO_INCREMENT,
    email varchar(255),
    name varchar(255),
    password varchar(255),
    PRIMARY KEY (user_id)
);