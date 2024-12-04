CREATE TABLE trending_scores (
	metric_id INT PRIMARY KEY AUTO_INCREMENT,
	spot_id INT NOT NULL,
	total_visits INT NOT NULL,
	trending_metrics double NOT NULL,
	average_rating double NOT NULL,
	FOREIGN KEY (spot_id) REFERENCES AllStudySpots(spot_id)
);