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

