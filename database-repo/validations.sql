drop database validations;


drop user 'validations'@'%';


create database validations;


CREATE USER 'validations'@'localhost' IDENTIFIED BY 'rootval';
GRANT ALL PRIVILEGES ON validations.* TO 'validations'@'localhost';


-- Create Tables validations Schema Start***
CREATE TABLE validations.`User` (
 `id` int NOT NULL AUTO_INCREMENT,
 `email` varchar(500) NOT NULL,
 `phoneNumber` varchar(500) DEFAULT NULL,
 `firstName` varchar(2000) NOT NULL,
 `lastName` varchar(2000) NOT NULL,
 `creationDate` timestamp(2) NOT NULL DEFAULT CURRENT_TIMESTAMP(2),
 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE TABLE validations.`merchant_payment_request` (
 `id` int NOT NULL AUTO_INCREMENT,
 `merchantTransactionReference` varchar(50) NOT NULL,
 `transactionRequest` text DEFAULT NULL,
 `creationDate` timestamp(2) NOT NULL DEFAULT CURRENT_TIMESTAMP(2),
 PRIMARY KEY (`id`),
UNIQUE KEY (`merchantTransactionReference`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
-- Create Tables validations Schema End***