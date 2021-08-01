CREATE TABLE simulation (
simulation_id INT AUTO_INCREMENT  PRIMARY KEY,
number_of_rolls INT NOT NULL,
dice_side INT NOT NULL,
dice_count INT NOT NULL,
created_date TIMESTAMP
);

CREATE TABLE dice_distribution_record (
record_id INT AUTO_INCREMENT  PRIMARY KEY,
simulation_id INT NOT NULL,
rolled_times INT NOT NULL,
sum_rolled_number_dice INT NOT NULL,
created_date TIMESTAMP
);