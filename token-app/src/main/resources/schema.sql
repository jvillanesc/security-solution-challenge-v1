CREATE TABLE IF NOT EXISTS credential
(credential_id INT NOT NULL AUTO_INCREMENT,
client_id VARCHAR(50) NOT NULL,
password VARCHAR(50) NOT NULL,
PRIMARY KEY (credential_id));
