-- Drop the existing database and user if they exist
DROP DATABASE IF EXISTS trustly;
DROP USER IF EXISTS 'trustly'@'localhost';

-- Create the database and user
CREATE DATABASE trustly;
CREATE USER 'trustly'@'localhost' IDENTIFIED BY 'roottrustly';
GRANT ALL PRIVILEGES ON trustly.* TO 'trustly'@'localhost';

-- Create tables in the trustly schema
CREATE TABLE trustly.provider_request_response (
                                                   id INT NOT NULL AUTO_INCREMENT,
                                                   transactionReference VARCHAR(50) NOT NULL,
                                                   request TEXT,
                                                   response TEXT,
                                                   creationDate TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP(2),
                                                   PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
