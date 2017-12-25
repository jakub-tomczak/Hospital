-- Generated by Oracle SQL Developer Data Modeler 17.3.0.261.1541
--   at:        2017-12-25 15:25:29 CET
--   site:      Oracle Database 12cR2
--   type:      Oracle Database 12cR2



CREATE TABLE sz_badania (
    id                   INTEGER NOT NULL,
    nazwa                VARCHAR2(40) NOT NULL,
    datagodzinabadania   DATE NOT NULL,
    oddzialy_id       INTEGER NOT NULL,
    pacjenci_id       INTEGER NOT NULL
);

ALTER TABLE sz_badania ADD CONSTRAINT badania_pk PRIMARY KEY ( id );

CREATE TABLE sz_choroby (
    id      INTEGER NOT NULL,
    nazwa   VARCHAR2(30) NOT NULL
);

ALTER TABLE sz_choroby ADD CONSTRAINT choroby_pk PRIMARY KEY ( id );

CREATE TABLE sz_leczenia (
    datarozpoczecia   DATE NOT NULL,
    datazakonczenia   DATE NOT NULL,
    dawka             VARCHAR2(20) NOT NULL,
    pacjenci_id    INTEGER NOT NULL,
    choroby_id     INTEGER NOT NULL,
    leczenieid        INTEGER NOT NULL
);

ALTER TABLE sz_leczenia ADD CONSTRAINT leczenia_pk PRIMARY KEY ( choroby_id,
leczenieid );

CREATE TABLE sz_lekarze (
    specjalizacja    VARCHAR2(20),
    stopiennaukowy   VARCHAR2(20),
    pracownikid      INTEGER NOT NULL
);

ALTER TABLE sz_lekarze ADD CONSTRAINT lekarze_pk PRIMARY KEY ( pracownikid );

CREATE TABLE sz_leki (
    id           INTEGER NOT NULL,
    nazwa        VARCHAR2(30) NOT NULL,
    leki_id   NUMBER NOT NULL
);

ALTER TABLE sz_leki ADD CONSTRAINT leki_pk PRIMARY KEY ( leki_id );

CREATE TABLE sz_oddzialy (
    id                          INTEGER NOT NULL,
    kierownikkliniki            INTEGER NOT NULL,
    nazwa                       VARCHAR2(30) NOT NULL,
    maksymalnaliczbapacjentow   INTEGER NOT NULL
);

ALTER TABLE sz_oddzialy ADD CONSTRAINT oddzialy_pk PRIMARY KEY ( id );

CREATE TABLE sz_operacje (
    operacjaid               INTEGER NOT NULL,
    rodzajoperacji           VARCHAR2(50),
    datagodzinarozpoczecia   DATE NOT NULL,
    pacjenci_id           INTEGER NOT NULL,
    oddzialy_id           INTEGER NOT NULL
);

ALTER TABLE sz_operacje ADD CONSTRAINT operacje_pk PRIMARY KEY ( operacjaid );

CREATE TABLE sz_pacjenci (
    id         INTEGER NOT NULL,
    imie       VARCHAR2(20) NOT NULL,
    nazwisko   VARCHAR2(20) NOT NULL,
    pesel      VARCHAR2(20) NOT NULL
);

ALTER TABLE sz_pacjenci ADD CONSTRAINT pacjenci_pk PRIMARY KEY ( id );

CREATE TABLE sz_pielegniarki (
    pracownikid   INTEGER NOT NULL
);

ALTER TABLE sz_pielegniarki ADD CONSTRAINT pielegniarki_pk PRIMARY KEY ( pracownikid );

CREATE TABLE sz_pobyty (
    pacjenci_id   INTEGER NOT NULL,
    dataprzyjecia    DATE NOT NULL,
    datawypisania    DATE,
    oddzialy_id   INTEGER NOT NULL
);

ALTER TABLE sz_pobyty
    ADD CONSTRAINT pobyty_pk PRIMARY KEY ( oddzialy_id,
    pacjenci_id,
    dataprzyjecia );

CREATE TABLE sz_pracownicy (
    imie             VARCHAR2(20) NOT NULL,
    nazwisko         VARCHAR2(20) NOT NULL,
    stanowisko       number(2) NOT NULL,
    pensja           NUMBER(2,5) NOT NULL,
    oddzialy_id   INTEGER NOT NULL,
    pracownikid      INTEGER NOT NULL
);

ALTER TABLE sz_pracownicy ADD CONSTRAINT pracownicy_pk PRIMARY KEY ( pracownikid );

CREATE TABLE sz_sprzetnaoddziale (
    badania_id   INTEGER NOT NULL,
    sprzety_id   INTEGER NOT NULL
);

ALTER TABLE sz_sprzetnaoddziale ADD CONSTRAINT sprzetnaoddziale_pk PRIMARY KEY ( badania_id,
sprzety_id );

CREATE TABLE sz_sprzety (
    id               INTEGER NOT NULL,
    nazwa            VARCHAR2(30) NOT NULL,
    rokprodukcji     INTEGER NOT NULL,
    stan             INTEGER,
    sz_oddzialy_id   INTEGER NOT NULL
);

ALTER TABLE sz_sprzety ADD CONSTRAINT sprzety_pk PRIMARY KEY ( id );

CREATE TABLE sz_stosowaneleki (
    id                   INTEGER NOT NULL,
    datawaznosci         DATE NOT NULL,
    nrserii              INTEGER NOT NULL,
    leki_leki_id   NUMBER NOT NULL
);

ALTER TABLE sz_stosowaneleki ADD CONSTRAINT szstosowaneleki_pk PRIMARY KEY ( id );

CREATE TABLE sz_uzywaneleki (
    leczenia_choroby_id   INTEGER NOT NULL,
    leczenia_leczenieid      INTEGER NOT NULL,
    stosowaneleki_id         INTEGER NOT NULL
);

ALTER TABLE sz_uzywaneleki
    ADD CONSTRAINT sz_uzywaneleki_pk PRIMARY KEY ( leczenia_choroby_id,
    leczenia_leczenieid,
    stosowaneleki_id );

CREATE TABLE sz_wykbadania (
    pracownicy_pracownikid   INTEGER NOT NULL,
    badania_id               INTEGER NOT NULL
);

ALTER TABLE sz_wykbadania ADD CONSTRAINT wykbadania_pk PRIMARY KEY ( pracownicy_pracownikid,
badania_id );

CREATE TABLE sz_wykonywaneoperacje (
    lekarze_pracownikid   INTEGER NOT NULL,
    operacje_operacjaid   INTEGER NOT NULL
);

ALTER TABLE sz_wykonywaneoperacje ADD CONSTRAINT wykonywaneoperacje_pk PRIMARY KEY ( lekarze_pracownikid,
operacje_operacjaid );

ALTER TABLE sz_badania
    ADD CONSTRAINT badania_oddzialy_fk FOREIGN KEY ( oddzialy_id )
        REFERENCES sz_oddzialy ( id );

ALTER TABLE sz_badania
    ADD CONSTRAINT badania_pacjenci_fk FOREIGN KEY ( pacjenci_id )
        REFERENCES sz_pacjenci ( id );

ALTER TABLE sz_leczenia
    ADD CONSTRAINT leczenia_choroby_fk FOREIGN KEY ( choroby_id )
        REFERENCES sz_choroby ( id );

ALTER TABLE sz_leczenia
    ADD CONSTRAINT leczenia_pacjenci_fk FOREIGN KEY ( pacjenci_id )
        REFERENCES sz_pacjenci ( id );

ALTER TABLE sz_lekarze
    ADD CONSTRAINT lekarze_pracownicy_fk FOREIGN KEY ( pracownikid )
        REFERENCES sz_pracownicy ( pracownikid );

ALTER TABLE sz_operacje
    ADD CONSTRAINT operacje_oddzialy_fk FOREIGN KEY ( oddzialy_id )
        REFERENCES sz_oddzialy ( id );

ALTER TABLE sz_operacje
    ADD CONSTRAINT operacje_pacjenci_fk FOREIGN KEY ( pacjenci_id )
        REFERENCES sz_pacjenci ( id );

--  ERROR: FK name length exceeds maximum allowed length(30) 
ALTER TABLE sz_pielegniarki
    ADD CONSTRAINT pielegniarki_pracownicy_fk FOREIGN KEY ( pracownikid )
        REFERENCES sz_pracownicy ( pracownikid );

ALTER TABLE sz_pobyty
    ADD CONSTRAINT pobyty_oddzialy_fk FOREIGN KEY ( oddzialy_id )
        REFERENCES sz_oddzialy ( id );

ALTER TABLE sz_pobyty
    ADD CONSTRAINT pobyty_pacjenci_fk FOREIGN KEY ( pacjenci_id )
        REFERENCES sz_pacjenci ( id );

ALTER TABLE sz_pracownicy
    ADD CONSTRAINT pracownicy_oddzialy_fk FOREIGN KEY ( oddzialy_id )
        REFERENCES sz_oddzialy ( id );

--  ERROR: FK name length exceeds maximum allowed length(30) 
ALTER TABLE sz_sprzetnaoddziale
    ADD CONSTRAINT sprzetnaoddziale_badania_fk FOREIGN KEY ( badania_id )
        REFERENCES sz_badania ( id );

--  ERROR: FK name length exceeds maximum allowed length(30) 
ALTER TABLE sz_sprzetnaoddziale
    ADD CONSTRAINT sprzetnaoddziale_sprzety_fk FOREIGN KEY ( sprzety_id )
        REFERENCES sz_sprzety ( id );

ALTER TABLE sz_sprzety
    ADD CONSTRAINT sprzety_oddzialy_fk FOREIGN KEY ( oddzialy_id )
        REFERENCES sz_oddzialy ( id );

ALTER TABLE sz_stosowaneleki
    ADD CONSTRAINT stosowaneleki_leki_fk FOREIGN KEY ( leki_leki_id )
        REFERENCES sz_leki ( sz_leki_id );

ALTER TABLE sz_uzywaneleki
    ADD CONSTRAINT sz_uzywaneleki_sz_leczenia_fk FOREIGN KEY ( leczenia_choroby_id,
    sz_leczenia_leczenieid )
        REFERENCES sz_leczenia ( sz_choroby_id,
        leczenieid );

--  ERROR: FK name length exceeds maximum allowed length(30) 
ALTER TABLE sz_uzywaneleki
    ADD CONSTRAINT uzywaneleki_stosowaneleki_fk FOREIGN KEY ( stosowaneleki_id )
        REFERENCES sz_stosowaneleki ( id );

ALTER TABLE sz_wykbadania
    ADD CONSTRAINT wykbadania_badania_fk FOREIGN KEY ( badania_id )
        REFERENCES sz_badania ( id );

ALTER TABLE sz_wykbadania
    ADD CONSTRAINT wykbadania_pracownicy_fk FOREIGN KEY ( pracownicy_pracownikid )
        REFERENCES sz_pracownicy ( pracownikid );

--  ERROR: FK name length exceeds maximum allowed length(30) 
ALTER TABLE sz_wykonywaneoperacje
    ADD CONSTRAINT wykonywaneoperacje_lekarze_fk FOREIGN KEY ( lekarze_pracownikid )
        REFERENCES sz_lekarze ( pracownikid );

--  ERROR: FK name length exceeds maximum allowed length(30) 
ALTER TABLE sz_wykonywaneoperacje
    ADD CONSTRAINT wykonywaneoperacje_operacje_fk FOREIGN KEY ( operacje_operacjaid )
        REFERENCES sz_operacje ( operacjaid );

--  ERROR: No Discriminator Column found in Arc FKArc_4 - constraint trigger for Arc cannot be generated 

--  ERROR: No Discriminator Column found in Arc FKArc_4 - constraint trigger for Arc cannot be generated

CREATE SEQUENCE leki_leki_id_seq START WITH 1 NOCACHE ORDER;
CREATE SEQUENCE pracownicy_id_seq START WITH 1 NOCACHE ORDER;
CREATE SEQUENCE oddzialy_id_seq START WITH 1 NOCACHE ORDER;
CREATE SEQUENCE badania_id_seq START WITH 1 NOCACHE ORDER;
CREATE SEQUENCE sprzet_id_seq START WITH 1 NOCACHE ORDER;
CREATE SEQUENCE pacjenci_id_seq START WITH 1 NOCACHE ORDER;
CREATE SEQUENCE sprzet_id_seq START WITH 1 NOCACHE ORDER;
CREATE SEQUENCE choroby_id_seq START WITH 1 NOCACHE ORDER;
CREATE SEQUENCE leczenie_id_seq START WITH 1 NOCACHE ORDER;
CREATE SEQUENCE leki_id_seq START WITH 1 NOCACHE ORDER;
CREATE SEQUENCE leczenia_id_seq START WITH 1 NOCACHE ORDER;
CREATE SEQUENCE operacje_id_seq START WITH 1 NOCACHE ORDER;
CREATE SEQUENCE pobyty_id_seq START WITH 1 NOCACHE ORDER;

CREATE OR REPLACE TRIGGER leki_leki_id_trg BEFORE
    INSERT ON sz_leki
    FOR EACH ROW
    WHEN ( new.sz_leki_id IS NULL )
BEGIN
    :new.sz_leki_id := leki_leki_id_seq.nextval;
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
-- ERRORS                                   8
-- WARNINGS                                 0
