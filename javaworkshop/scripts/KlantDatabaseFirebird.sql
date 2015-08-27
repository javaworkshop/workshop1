CREATE DATABASE 'C:/Documents and Settings/All Users/Application Data/Firebird/klantdatabase.fdb'
USER 'SYSDBA' PASSWORD 'masterkey'
;


CREATE TABLE KLANT (
  klant_id INT not null primary key,
  voornaam VARCHAR(50) not null,
  tussenvoegsel VARCHAR(20),
  achternaam VARCHAR(51) not null,
  email VARCHAR(320) not null,
  straatnaam VARCHAR(26) not null,
  huisnummer INT not null,
  toevoeging VARCHAR(6),
  postcode VARCHAR(6) not null,
  woonplaats VARCHAR(26) not null
);


CREATE TABLE BESTELLING (
  bestelling_id INT not null primary key,
  klant_id INT references klant,
  artikel_id1 INT not null,
  artikel_naam1 VARCHAR(45) not null,
  artikel_aantal1 INT not null,
  artikel_prijs1 DECIMAL(18, 4) not null,
  artikel_id2 INT not null,
  artikel_naam2 VARCHAR(45) not null,
  artikel_aantal2 INT not null,
  artikel_prijs2 DECIMAL(18, 4) not null,
  artikel_id3 INT not null,
  artikel_naam3 VARCHAR(45) not null,
  artikel_aantal3 INT not null,
  artikel_prijs3 DECIMAL(18, 4) not null
);


CREATE GENERATOR gen_klant_id;
SET GENERATOR gen_klant_id TO 0;

set term !! ;
CREATE TRIGGER trigger_klant_id FOR klant
ACTIVE BEFORE INSERT
POSITION 0
AS
begin
if ((NEW.KLANT_ID IS null) or (NEW.KLANT_ID = 0)) then new.KLANT_ID = gen_id(gen_klant_id,1);
end!!
set term ; !!


CREATE GENERATOR gen_bestelling_id;
SET GENERATOR gen_bestelling_id TO 0;

set term !! ;
CREATE TRIGGER trigger_bestelling_id FOR bestelling
ACTIVE BEFORE INSERT
POSITION 0
AS
begin
if ((NEW.bestelling_id IS null) or (NEW.bestelling_id = 0)) then new.bestelling_id = gen_id(gen_bestelling_id,1);
end!!
set term ; !!