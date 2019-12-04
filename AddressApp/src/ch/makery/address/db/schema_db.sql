DROP SCHEMA public CASCADE;
CREATE SCHEMA public;

CREATE TABLE Pessoa
(
	cod_CPF CHAR(11) NOT NULL,
	nom_prim VARCHAR(50) NOT NULL,
	nom_ult VARCHAR(50) NOT NULL,
        rua VARCHAR(50),
        cidade VARCHAR(50),
        cod_postal VARCHAR(50),
        data_nasc VARCHAR(10),
	PRIMARY KEY (cod_CPF)
);
