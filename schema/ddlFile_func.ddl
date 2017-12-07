-- Generated by Oracle SQL Developer Data Modeler 17.3.0.261.1541
--   at:        2017-12-07 23:03:30 WAT
--   site:      Oracle Database 11g
--   type:      Oracle Database 11g



CREATE TABLE badania (
    id                   INTEGER NOT NULL,
    nazwa                VARCHAR2(40) NOT NULL,
    datagodzinabadania   DATE NOT NULL,
    oddzialy_id          INTEGER NOT NULL,
    pacjenci_id          INTEGER NOT NULL
);

ALTER TABLE badania ADD CONSTRAINT badania_pk PRIMARY KEY ( id );

CREATE TABLE choroby (
    id      INTEGER NOT NULL,
    nazwa   VARCHAR2(30) NOT NULL
);

ALTER TABLE choroby ADD CONSTRAINT choroby_pk PRIMARY KEY ( id );

CREATE TABLE leczenie (
    datarozpoczecia   DATE NOT NULL,
    datazakonczenia   DATE NOT NULL,
    dawka             VARCHAR2(20) NOT NULL,
    pacjenci_id       INTEGER NOT NULL,
    choroby_id        INTEGER NOT NULL,
    leczenieid        INTEGER NOT NULL
);

ALTER TABLE leczenie ADD CONSTRAINT leczenie_pk PRIMARY KEY ( choroby_id,
leczenieid );

CREATE TABLE lekarze (
    specjalizacja    VARCHAR2(20),
    stopiennaukowy   VARCHAR2(20),
    pracownikid      INTEGER NOT NULL
);

ALTER TABLE lekarze ADD CONSTRAINT lekarze_pk PRIMARY KEY ( pracownikid );

CREATE TABLE leki (
    id        INTEGER NOT NULL,
    nazwa     VARCHAR2(30) NOT NULL,
    leki_id   NUMBER NOT NULL
);

ALTER TABLE leki ADD CONSTRAINT leki_pk PRIMARY KEY ( leki_id );

CREATE TABLE oddzialy (
    id                          INTEGER NOT NULL,
    kierownikkliniki            INTEGER NOT NULL,
    nazwa                       VARCHAR2(30) NOT NULL,
    maksymalnaliczbapacjentow   INTEGER NOT NULL
);

ALTER TABLE oddzialy ADD CONSTRAINT oddzialy_pk PRIMARY KEY ( id );

CREATE TABLE operacje (
    operacjaid               INTEGER NOT NULL,
    rodzajoperacji           VARCHAR2(50),
    datagodzinarozpoczecia   DATE NOT NULL,
    pacjenci_id              INTEGER NOT NULL,
    oddzialy_id              INTEGER NOT NULL
);

ALTER TABLE operacje ADD CONSTRAINT operacje_pk PRIMARY KEY ( operacjaid );

CREATE TABLE pacjenci (
    id         INTEGER NOT NULL,
    imie       VARCHAR2(20) NOT NULL,
    nazwisko   VARCHAR2(20) NOT NULL,
    pesel      VARCHAR2(20) NOT NULL
);

ALTER TABLE pacjenci ADD CONSTRAINT pacjenci_pk PRIMARY KEY ( id );

CREATE TABLE pielegniarki (
    pracownikid   INTEGER NOT NULL
);

ALTER TABLE pielegniarki ADD CONSTRAINT pielegniarki_pk PRIMARY KEY ( pracownikid );

CREATE TABLE pobyt (
    pacjenci_id     INTEGER NOT NULL,
    dataprzyjecia   DATE NOT NULL,
    datawypisania   DATE,
    oddzialy_id     INTEGER NOT NULL
);

ALTER TABLE pobyt
    ADD CONSTRAINT pobyt_pk PRIMARY KEY ( oddzialy_id,
    pacjenci_id,
    dataprzyjecia );

CREATE TABLE pracownicy (
    imie          VARCHAR2(20) NOT NULL,
    nazwisko      VARCHAR2(20) NOT NULL,
    stanowisko    INTEGER NOT NULL,
    pensja        NUMBER(2,5) NOT NULL,
    oddzialy_id   INTEGER NOT NULL,
    pracownikid   INTEGER NOT NULL
);

ALTER TABLE pracownicy ADD CONSTRAINT pracownicy_pk PRIMARY KEY ( pracownikid );

CREATE TABLE relation_17 (
    leczenie_id1          INTEGER NOT NULL,
    leczenie_leczenieid   INTEGER NOT NULL,
    stosowane_leki_id     INTEGER NOT NULL
);

ALTER TABLE relation_17
    ADD CONSTRAINT relation_17_pk PRIMARY KEY ( leczenie_id1,
    leczenie_leczenieid,
    stosowane_leki_id );

CREATE TABLE sprzetnaodziale (
    badania_id   INTEGER NOT NULL,
    sprzety_id   INTEGER NOT NULL
);

ALTER TABLE sprzetnaodziale ADD CONSTRAINT sprzetnaodziale_pk PRIMARY KEY ( badania_id,
sprzety_id );

CREATE TABLE sprzety (
    id             INTEGER NOT NULL,
    nazwa          VARCHAR2(30) NOT NULL,
    rokprodukcji   INTEGER NOT NULL,
    stan           INTEGER,
    oddzialy_id    INTEGER NOT NULL
);

ALTER TABLE sprzety ADD CONSTRAINT sprzety_pk PRIMARY KEY ( id );

CREATE TABLE stosowane_leki (
    id             INTEGER NOT NULL,
    datawaznosci   DATE NOT NULL,
    nrserii        INTEGER NOT NULL,
    leki_leki_id   NUMBER NOT NULL
);

ALTER TABLE stosowane_leki ADD CONSTRAINT stosowane_leki_pk PRIMARY KEY ( id );

CREATE TABLE wykonywane_operacje (
    lekarze_pracownikid   INTEGER NOT NULL,
    operacje_operacjaid   INTEGER NOT NULL
);

ALTER TABLE wykonywane_operacje ADD CONSTRAINT wykonywaneoperacjepk PRIMARY KEY ( lekarze_pracownikid,
operacje_operacjaid );

CREATE TABLE wykonywanebadania (
    lekarze_pracownikid   INTEGER NOT NULL,
    badania_id            INTEGER NOT NULL
);

ALTER TABLE wykonywanebadania ADD CONSTRAINT wykonywanebadania_pk PRIMARY KEY ( lekarze_pracownikid,
badania_id );

ALTER TABLE badania
    ADD CONSTRAINT badania_oddzialy_fk FOREIGN KEY ( oddzialy_id )
        REFERENCES oddzialy ( id );

ALTER TABLE badania
    ADD CONSTRAINT badania_pacjenci_fk FOREIGN KEY ( pacjenci_id )
        REFERENCES pacjenci ( id );

ALTER TABLE leczenie
    ADD CONSTRAINT leczenie_choroby_fk FOREIGN KEY ( choroby_id )
        REFERENCES choroby ( id );

ALTER TABLE leczenie
    ADD CONSTRAINT leczenie_pacjenci_fk FOREIGN KEY ( pacjenci_id )
        REFERENCES pacjenci ( id );

ALTER TABLE lekarze
    ADD CONSTRAINT lekarze_pracownicy_fk FOREIGN KEY ( pracownikid )
        REFERENCES pracownicy ( pracownikid );

ALTER TABLE operacje
    ADD CONSTRAINT operacje_oddzialy_fk FOREIGN KEY ( oddzialy_id )
        REFERENCES oddzialy ( id );

ALTER TABLE operacje
    ADD CONSTRAINT operacje_pacjenci_fk FOREIGN KEY ( pacjenci_id )
        REFERENCES pacjenci ( id );

ALTER TABLE pielegniarki
    ADD CONSTRAINT pielegniarki_pracownicy_fk FOREIGN KEY ( pracownikid )
        REFERENCES pracownicy ( pracownikid );

ALTER TABLE pobyt
    ADD CONSTRAINT pobyt_oddzialy_fk FOREIGN KEY ( oddzialy_id )
        REFERENCES oddzialy ( id );

ALTER TABLE pobyt
    ADD CONSTRAINT pobyt_pacjenci_fk FOREIGN KEY ( pacjenci_id )
        REFERENCES pacjenci ( id );

ALTER TABLE pracownicy
    ADD CONSTRAINT pracownicy_oddzialy_fk FOREIGN KEY ( oddzialy_id )
        REFERENCES oddzialy ( id );

ALTER TABLE relation_17
    ADD CONSTRAINT relation_17_leczenie_fk FOREIGN KEY ( leczenie_id1,
    leczenie_leczenieid )
        REFERENCES leczenie ( choroby_id,
        leczenieid );

ALTER TABLE relation_17
    ADD CONSTRAINT relation_17_stosowane_leki_fk FOREIGN KEY ( stosowane_leki_id )
        REFERENCES stosowane_leki ( id );

ALTER TABLE sprzetnaodziale
    ADD CONSTRAINT sprzetnaodziale_badania_fk FOREIGN KEY ( badania_id )
        REFERENCES badania ( id );

ALTER TABLE sprzetnaodziale
    ADD CONSTRAINT sprzetnaodziale_sprzety_fk FOREIGN KEY ( sprzety_id )
        REFERENCES sprzety ( id );

ALTER TABLE sprzety
    ADD CONSTRAINT sprzety_oddzialy_fk FOREIGN KEY ( oddzialy_id )
        REFERENCES oddzialy ( id );

ALTER TABLE stosowane_leki
    ADD CONSTRAINT stosowane_leki_leki_fk FOREIGN KEY ( leki_leki_id )
        REFERENCES leki ( leki_id );

ALTER TABLE wykonywane_operacje
    ADD CONSTRAINT wykonywane_operacje_lekarze_fk FOREIGN KEY ( lekarze_pracownikid )
        REFERENCES lekarze ( pracownikid );

ALTER TABLE wykonywanebadania
    ADD CONSTRAINT wykonywanebadania_badania_fk FOREIGN KEY ( badania_id )
        REFERENCES badania ( id );

ALTER TABLE wykonywanebadania
    ADD CONSTRAINT wykonywanebadania_lekarze_fk FOREIGN KEY ( lekarze_pracownikid )
        REFERENCES lekarze ( pracownikid );

ALTER TABLE wykonywane_operacje
    ADD CONSTRAINT wykonywaneoperacje_fk FOREIGN KEY ( operacje_operacjaid )
        REFERENCES operacje ( operacjaid );

--  ERROR: No Discriminator Column found in Arc FKArc_3 - constraint trigger for Arc cannot be generated 

--  ERROR: No Discriminator Column found in Arc FKArc_3 - constraint trigger for Arc cannot be generated

CREATE SEQUENCE leki_leki_id_seq START WITH 1 NOCACHE ORDER;

CREATE OR REPLACE TRIGGER leki_leki_id_trg BEFORE
    INSERT ON leki
    FOR EACH ROW
    WHEN ( new.leki_id IS NULL )
BEGIN
    :new.leki_id := leki_leki_id_seq.nextval;
END;
/

CREATE OR REPLACE TRIGGER pacjent_dodaj_data_wypisu BEFORE
    INSERT ON pobyt
    when pobyt.DataZakonczenia is not null
declare 
    data_zakonczenia_przed_przyjecia exception
BEGIN
    if (:new.DataZakonczenia < :new.DataPrzyjecia) THEN
        RAISE data_zakonczenia_przed_przyjecia
    endif;
END;
/

CREATE OR REPLACE FUNCTION  PobierzLiczbeOsobNaOddziale
    (nazwa_oddzialu in varchar)
return number is
    liczba_osob number
BEGIN
        liczba_osob = select count(*) from oddzialy where  oddzialy.nazwa=nazwa_oddzialu;
        return liczba_osob;
END;
/




-- Oracle SQL Developer Data Modeler Summary Report: 
-- 
-- CREATE TABLE                            17
-- CREATE INDEX                             0
-- ALTER TABLE                             38
-- CREATE VIEW                              0
-- ALTER VIEW                               0
-- CREATE PACKAGE                           0
-- CREATE PACKAGE BODY                      0
-- CREATE PROCEDURE                         0
-- CREATE FUNCTION                          0
-- CREATE TRIGGER                           1
-- ALTER TRIGGER                            0
-- CREATE COLLECTION TYPE                   0
-- CREATE STRUCTURED TYPE                   0
-- CREATE STRUCTURED TYPE BODY              0
-- CREATE CLUSTER                           0
-- CREATE CONTEXT                           0
-- CREATE DATABASE                          0
-- CREATE DIMENSION                         0
-- CREATE DIRECTORY                         0
-- CREATE DISK GROUP                        0
-- CREATE ROLE                              0
-- CREATE ROLLBACK SEGMENT                  0
-- CREATE SEQUENCE                          1
-- CREATE MATERIALIZED VIEW                 0
-- CREATE SYNONYM                           0
-- CREATE TABLESPACE                        0
-- CREATE USER                              0
-- 
-- DROP TABLESPACE                          0
-- DROP DATABASE                            0
-- 
-- REDACTION POLICY                         0
-- 
-- ORDS DROP SCHEMA                         0
-- ORDS ENABLE SCHEMA                       0
-- ORDS ENABLE OBJECT                       0
-- 
-- ERRORS                                   2
-- WARNINGS                                 0