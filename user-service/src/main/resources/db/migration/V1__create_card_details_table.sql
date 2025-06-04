-- Create CardDetails table first
CREATE TABLE card_details (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    card_number VARCHAR(50) NOT NULL,
    valid_until_month INT NOT NULL,
    valid_until_year INT NOT NULL,
    cvv INT NOT NULL
);