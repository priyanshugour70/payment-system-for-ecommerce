drop database payments;
drop database trustly;
drop database validations;


drop user 'validations'@'%';
drop user 'payments'@'%';
drop user 'trustly'@'%';



-- Creates databases
create database validations;
create database payments;
create database trustly;


-- Creates user & grants permission
CREATE USER 'validations'@'%' IDENTIFIED BY 'cptraining';
GRANT ALL ON *.* TO 'validations'@'%' ;

CREATE USER 'payments'@'%' IDENTIFIED BY 'cptraining';
GRANT ALL ON *.* TO 'payments'@'%' ;

CREATE USER 'trustly'@'%' IDENTIFIED BY 'cptraining';
GRANT ALL ON *.* TO 'trustly'@'%' ;


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


-- Create Tables payments Schema Start***
CREATE TABLE payments.`Payment_Method` (
 `id` int NOT NULL,
 `name` varchar(500) NOT NULL,
 `status` tinyint DEFAULT 1,
 `creationDate` timestamp(2) NOT NULL DEFAULT CURRENT_TIMESTAMP(2),
 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE TABLE payments.`Payment_Type` (
 `id` int NOT NULL,
 `type` varchar(500) NOT NULL,
 `status` tinyint DEFAULT 1,
 `creationDate` timestamp(2) NOT NULL DEFAULT CURRENT_TIMESTAMP(2),
 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE TABLE payments.`Provider` (
 `id` int NOT NULL AUTO_INCREMENT,
 `providerName` varchar(2000) NOT NULL,
 `status` tinyint DEFAULT 1,
 `creationDate` timestamp(2) NOT NULL DEFAULT CURRENT_TIMESTAMP(2),
 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE TABLE payments.`Transaction_Status` (
 `id` int NOT NULL,
 `name` varchar(2000) NOT NULL,
 `status` tinyint DEFAULT 1,
 `creationDate` timestamp(2) NOT NULL DEFAULT CURRENT_TIMESTAMP(2),
 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE payments.`Transaction_Details` (
 `id` int NOT NULL,
 `code` varchar(100) NOT NULL,
 `message` varchar(1000) NOT NULL,
 `status` tinyint DEFAULT 1,
 `creationDate` timestamp(2) NOT NULL DEFAULT CURRENT_TIMESTAMP(2),
 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE payments.`Transaction` (
 `id` int NOT NULL AUTO_INCREMENT,
 `userId` int NOT NULL,
 `paymentMethodId` int NOT NULL,
 `providerId` int NOT NULL,
 `paymentTypeId` int NOT NULL,
 `amount` decimal(19,2) DEFAULT '0.00',
 `currency` varchar(3) NOT NULL,
 `txnStatusId` int NOT NULL,
 `merchantTransactionReference` varchar(50) NOT NULL,
 `txnReference` varchar(50) NOT NULL,
 `txnDetailsId` int NOT NULL,
 `providerCode` varchar(500) DEFAULT NULL,
 `providerMessage` varchar(1000) DEFAULT NULL,
`debitorAccount` varchar(100) DEFAULT NULL,
`creditorAccount` varchar(100) DEFAULT NULL,
`providerReference` varchar(100) DEFAULT NULL,
 `creationDate` timestamp(2) NOT NULL DEFAULT CURRENT_TIMESTAMP(2),
 `updatedDate` timestamp(2) NOT NULL DEFAULT CURRENT_TIMESTAMP(2),
 PRIMARY KEY (`id`),
 UNIQUE KEY `transaction_txnReference` (`txnReference`),
 KEY `transaction_paymentMethodId` (`paymentMethodId`),
 KEY `transaction_providerId` (`providerId`),
 KEY `transaction_txnStatusId` (`txnStatusId`),
 kEY `transaction_paymentTypeId` (`paymentTypeId`),
 kEY `transaction_txnDetailsId` (`txnDetailsId`),
 CONSTRAINT `transaction_paymentMethodId` FOREIGN KEY (`paymentMethodId`) REFERENCES `Payment_Method` (`id`),
 CONSTRAINT `transaction_providerId` FOREIGN KEY (`providerId`) REFERENCES `Provider` (`id`),
 CONSTRAINT `transaction_txnStatusId` FOREIGN KEY (`txnStatusId`) REFERENCES `Transaction_Status` (`id`),
 CONSTRAINT `transaction_paymentTypeId` FOREIGN KEY (`paymentTypeId`) REFERENCES `Payment_Type` (`id`),
 CONSTRAINT `transaction_txnDetailsId` FOREIGN KEY (`txnDetailsId`) REFERENCES `Transaction_Details` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;


CREATE TABLE payments.`Transaction_Log` (
 `id` int NOT NULL AUTO_INCREMENT,
 `transactionId` int NOT NULL,
 `txnFromStatus` varchar(50) DEFAULT '-1',
 `txnToStatus` varchar(50) DEFAULT '-1',
 `creationDate` timestamp(2) NOT NULL DEFAULT CURRENT_TIMESTAMP(2),
 PRIMARY KEY (`id`),
 KEY `transaction_log_transactionId` (`transactionId`),
 CONSTRAINT `transaction_log_transactionId` FOREIGN KEY (`transactionId`) REFERENCES `Transaction` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
-- Create Tables payments Schema End***



-- Create Tables trustly Schema Start***
CREATE TABLE trustly.`provider_request_response` (
 `id` int NOT NULL AUTO_INCREMENT,
 `transactionReference` varchar(50) NOT NULL,
 `request` TEXT,
 `response` TEXT,
 `creationDate` timestamp(2) NOT NULL DEFAULT CURRENT_TIMESTAMP(2),
 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
-- Create Tables trustly Schema End***
