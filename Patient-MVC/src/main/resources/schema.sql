
-- Create the 'users' table
CREATE TABLE IF NOT EXISTS users (
    username VARCHAR(50) NOT NULL PRIMARY KEY,
    password VARCHAR(500) NOT NULL,
    enabled BOOLEAN NOT NULL
    );

-- Create the 'authorities' table
CREATE TABLE IF NOT EXISTS authorities (
    username VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users(username)
    );

-- Drop the foreign key constraint before dropping the index
ALTER TABLE authorities DROP FOREIGN KEY fk_authorities_users;

-- Drop the index
DROP INDEX ix_auth_username ON authorities;

-- Recreate the index with the desired changes
ALTER TABLE authorities ADD INDEX ix_auth_username (username, authority);

-- Recreate the foreign key constraint
ALTER TABLE authorities ADD CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users(username);
