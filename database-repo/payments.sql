-- Drop the existing database and user if they exist
DROP DATABASE IF EXISTS payments;
DROP USER IF EXISTS 'payments'@'localhost';

-- Create the database and user
CREATE DATABASE payments;
CREATE USER 'payments'@'localhost' IDENTIFIED BY 'rootpay';
GRANT ALL PRIVILEGES ON payments.* TO 'payments'@'localhost';

-- Use the payments database
USE payments;

-- Create tables in the payments schema
CREATE TABLE Payment_Method (
                                id INT NOT NULL,
                                name VARCHAR(500) NOT NULL,
                                status TINYINT DEFAULT 1,
                                creationDate TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP(2),
                                PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE Payment_Type (
                              id INT NOT NULL,
                              type VARCHAR(500) NOT NULL,
                              status TINYINT DEFAULT 1,
                              creationDate TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP(2),
                              PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE Provider (
                          id INT NOT NULL AUTO_INCREMENT,
                          providerName VARCHAR(2000) NOT NULL,
                          status TINYINT DEFAULT 1,
                          creationDate TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP(2),
                          PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE Transaction_Status (
                                    id INT NOT NULL,
                                    name VARCHAR(2000) NOT NULL,
                                    status TINYINT DEFAULT 1,
                                    creationDate TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP(2),
                                    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE Transaction_Details (
                                     id INT NOT NULL,
                                     code VARCHAR(100) NOT NULL,
                                     message VARCHAR(1000) NOT NULL,
                                     status TINYINT DEFAULT 1,
                                     creationDate TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP(2),
                                     PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE Transaction (
                             id INT NOT NULL AUTO_INCREMENT,
                             userId INT NOT NULL,
                             paymentMethodId INT NOT NULL,
                             providerId INT NOT NULL,
                             paymentTypeId INT NOT NULL,
                             amount DECIMAL(19,2) DEFAULT '0.00',
                             currency VARCHAR(3) NOT NULL,
                             txnStatusId INT NOT NULL,
                             merchantTransactionReference VARCHAR(50) NOT NULL,
                             txnReference VARCHAR(50) NOT NULL,
                             txnDetailsId INT NOT NULL,
                             providerCode VARCHAR(500) DEFAULT NULL,
                             providerMessage VARCHAR(1000) DEFAULT NULL,
                             debitorAccount VARCHAR(100) DEFAULT NULL,
                             creditorAccount VARCHAR(100) DEFAULT NULL,
                             providerReference VARCHAR(100) DEFAULT NULL,
                             creationDate TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP(2),
                             updatedDate TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP(2),
                             PRIMARY KEY (id),
                             UNIQUE KEY transaction_txnReference (txnReference),
                             KEY transaction_paymentMethodId (paymentMethodId),
                             KEY transaction_providerId (providerId),
                             KEY transaction_txnStatusId (txnStatusId),
                             KEY transaction_paymentTypeId (paymentTypeId),
                             KEY transaction_txnDetailsId (txnDetailsId),
                             CONSTRAINT transaction_paymentMethodId FOREIGN KEY (paymentMethodId) REFERENCES Payment_Method (id),
                             CONSTRAINT transaction_providerId FOREIGN KEY (providerId) REFERENCES Provider (id),
                             CONSTRAINT transaction_txnStatusId FOREIGN KEY (txnStatusId) REFERENCES Transaction_Status (id),
                             CONSTRAINT transaction_paymentTypeId FOREIGN KEY (paymentTypeId) REFERENCES Payment_Type (id),
                             CONSTRAINT transaction_txnDetailsId FOREIGN KEY (txnDetailsId) REFERENCES Transaction_Details (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

CREATE TABLE Transaction_Log (
                                 id INT NOT NULL AUTO_INCREMENT,
                                 transactionId INT NOT NULL,
                                 txnFromStatus VARCHAR(50) DEFAULT '-1',
                                 txnToStatus VARCHAR(50) DEFAULT '-1',
                                 creationDate TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP(2),
                                 PRIMARY KEY (id),
                                 KEY transaction_log_transactionId (transactionId),
                                 CONSTRAINT transaction_log_transactionId FOREIGN KEY (transactionId) REFERENCES Transaction (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Insert data into Transaction_Details table
INSERT INTO Transaction_Details (id, code, message, status, creationDate)
VALUES (1, '000.001', 'payment created', 1, '2023-09-10 19:05:16.01'),
       (2, '000.002', 'payment pending', 1, '2023-09-10 19:05:16.01'),
       (3, '000.003', 'payment approved', 1, '2023-09-10 19:05:16.01'),
       (4, '000.004', 'payment failed', 1, '2023-09-10 19:05:16.01');

-- Insert data into Payment_Method table
INSERT INTO Payment_Method (id, name, status, creationDate)
VALUES (1, 'APM', 1, '2023-09-10 21:29:49.25');

-- Insert data into Payment_Type table
INSERT INTO Payment_Type (id, type, status, creationDate)
VALUES (1, 'SALE', 1, '2023-09-10 21:30:42.05');

-- Insert data into Provider table
INSERT INTO Provider (id, providerName, status, creationDate)
VALUES (1, 'Trustly', 1, '2023-09-10 21:31:28.08');

-- Insert data into Transaction_Status table
INSERT INTO Transaction_Status (id, name, status, creationDate)
VALUES (1, 'CREATED', 1, '2023-09-10 21:33:39.84'),
       (2, 'PENDING', 2, '2023-09-10 21:33:39.84'),
       (3, 'APPROVED', 3, '2023-09-10 21:33:39.84'),
       (4, 'FAILED', 4, '2023-09-10 21:33:39.84');
