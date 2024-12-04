CREATE TABLE DailyMetrics (
	metric_id INT PRIMARY KEY AUTO_INCREMENT,
	spot_id INT NOT NULL,
	visit_count INT NOT NULL,
	rating double NOT NULL,
	date DATE NOT NULL,
	FOREIGN KEY (spot_id) REFERENCES AllStudySpots(spot_id)
	FOREIGN KEY (rating) REFERENCES Reviews(Rating)
);