drop database payments;


drop user 'payments'@'%';


create database payments;


CREATE USER 'payments'@'localhost' IDENTIFIED BY 'rootpay';
GRANT ALL PRIVILEGES ON payments.* TO 'payments'@'localhost';


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



-- data started

INSERT INTO payments.Transaction_Details
(id, code, message, status, creationDate)
VALUES(1, '000.001', 'payment created', 1, '2023-09-10 19:05:16.01');
INSERT INTO payments.Transaction_Details
(id, code, message, status, creationDate)
VALUES(2, '000.002', 'payment pending', 1, '2023-09-10 19:05:16.01');
INSERT INTO payments.Transaction_Details
(id, code, message, status, creationDate)
VALUES(3, '000.003', 'payment approved', 1, '2023-09-10 19:05:16.01');
INSERT INTO payments.Transaction_Details
(id, code, message, status, creationDate)
VALUES(4, '000.004', 'payment failed', 1, '2023-09-10 19:05:16.01');



INSERT INTO payments.Payment_Method
(id, name, status, creationDate)
VALUES(1, 'APM', 1, '2023-09-10 21:29:49.25');
INSERT INTO payments.Payment_Type
(id, `type`, status, creationDate)
VALUES(1, 'SALE', 1, '2023-09-10 21:30:42.05');

INSERT INTO payments.Provider
(id, providerName, status, creationDate)
VALUES(1, 'Trustly', 1, '2023-09-10 21:31:28.08');

INSERT INTO payments.Transaction_Status
(id, name, status, creationDate)
VALUES(1, 'CREATED', 1, '2023-09-10 21:33:39.84');
INSERT INTO payments.Transaction_Status
(id, name, status, creationDate)
VALUES(2, 'PENDING', 2, '2023-09-10 21:33:39.84');
INSERT INTO payments.Transaction_Status
(id, name, status, creationDate)
VALUES(3, 'APPROVED', 3, '2023-09-10 21:33:39.84');
INSERT INTO payments.Transaction_Status
(id, name, status, creationDate)
VALUES(4, 'FAILED', 4, '2023-09-10 21:33:39.84');

-- data ended
