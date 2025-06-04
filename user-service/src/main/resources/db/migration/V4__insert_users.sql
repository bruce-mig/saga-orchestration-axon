-- Insert corresponding users, mapping to the inserted card_details IDs (1, 2, 3)
INSERT INTO users (user_id, first_name, last_name, card_details_id) VALUES
('user_1', 'Alice', 'Smith', 1),
('user_2', 'Bob', 'Johnson', 2),
('user_3', 'Charlie', 'Brown', 3);