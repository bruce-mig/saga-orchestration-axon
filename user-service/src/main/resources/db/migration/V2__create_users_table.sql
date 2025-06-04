-- Create User table with a foreign key to CardDetails (one-to-one mapping)
CREATE TABLE users (
    user_id VARCHAR(50) PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    card_details_id INT UNIQUE,
    CONSTRAINT fk_card_details
        FOREIGN KEY (card_details_id)
        REFERENCES card_details(id)
        ON DELETE CASCADE
);