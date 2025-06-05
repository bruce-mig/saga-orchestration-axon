-- Insert corresponding users, mapping to the inserted card_details IDs (1, 2, 3)
INSERT INTO users (user_id, first_name, last_name, email, card_details_id) VALUES
('user_1', 'Alice', 'Smith', 'asmith@example.com', 1),
('user_2', 'Bob', 'Johnson', 'bjohnson@example.com', 2),
('user_3', 'Charlie', 'Brown', 'cbrown@example.com', 3);