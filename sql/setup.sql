DROP DATABASE IF EXISTS StudySpots;
CREATE DATABASE StudySpots;
USE StudySpots;

CREATE TABLE AllStudySpots(
	spot_id int NOT NULL AUTO_INCREMENT,
	name varchar(255),
	location varchar(255),
	image varchar(255),
	latitude decimal(10, 8),
	longitude decimal(11, 8),
	hours varchar(255),
    tags varchar(255),
	PRIMARY KEY (spot_id)
);

CREATE TABLE Users (
	user_id int NOT NULL AUTO_INCREMENT,
    email varchar(255),
    name varchar(255),
    password varchar(255),
    PRIMARY KEY (user_id)
);

CREATE TABLE DailyMetrics (
	metric_id INT PRIMARY KEY AUTO_INCREMENT,
	spot_id INT NOT NULL,
	visit_count INT NOT NULL,
	rating double NOT NULL,
	date DATE NOT NULL,
	FOREIGN KEY (spot_id) REFERENCES AllStudySpots(spot_id)
);

CREATE TABLE SavedSpots (
    save_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    spot_id INT NOT NULL,
    date DATE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (spot_id) REFERENCES AllStudySpots(spot_id)
);

CREATE TABLE trending_scores (
	metric_id INT PRIMARY KEY AUTO_INCREMENT,
	spot_id INT NOT NULL,
	total_visits INT NOT NULL,
	trending_metrics double NOT NULL,
	average_rating double NOT NULL,
	FOREIGN KEY (spot_id) REFERENCES AllStudySpots(spot_id)
);


CREATE TABLE Comments (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    post_id INT NOT NULL,
    title VARCHAR(255),
    description VARCHAR(5000),
    timestamp DATETIME NOT NULL,
    rating INT NOT NULL,
    CONSTRAINT rating_range CHECK (rating >= 1 AND rating <= 10),
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (post_id) REFERENCES AllStudySpots(spot_id),
    INDEX idx_user_post_comment (user_id, post_id, id)
);
