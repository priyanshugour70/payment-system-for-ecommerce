drop database trustly;


drop user 'trustly'@'%';


create database trustly;


CREATE USER 'trustly'@'localhost' IDENTIFIED BY 'Harda20p!';
GRANT ALL PRIVILEGES ON trustly.* TO 'trustly'@'localhost';


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