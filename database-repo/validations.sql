-- Drop the existing database and user if they exist
DROP DATABASE IF EXISTS validations;
DROP USER IF EXISTS 'validations'@'localhost';

-- Create the database and user
CREATE DATABASE validations;
CREATE USER 'validations'@'localhost' IDENTIFIED BY 'rootval';
GRANT ALL PRIVILEGES ON validations.* TO 'validations'@'localhost';
FLUSH PRIVILEGES;

-- Create tables in the validations schema
USE validations;

CREATE TABLE User (
                      id INT NOT NULL AUTO_INCREMENT,
                      email VARCHAR(500) NOT NULL,
                      phoneNumber VARCHAR(500) DEFAULT NULL,
                      firstName VARCHAR(2000) NOT NULL,
                      lastName VARCHAR(2000) NOT NULL,
                      creationDate TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP(2),
                      PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE merchant_payment_request (
                                          id INT NOT NULL AUTO_INCREMENT,
                                          merchantTransactionReference VARCHAR(50) NOT NULL,
                                          transactionRequest TEXT DEFAULT NULL,
                                          creationDate TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP(2),
                                          PRIMARY KEY (id),
                                          UNIQUE KEY (merchantTransactionReference)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
