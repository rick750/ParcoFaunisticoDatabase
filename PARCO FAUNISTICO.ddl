-- Database Section
-- ________________ 
DROP DATABASE IF EXISTS parco_faunistico;
CREATE DATABASE IF NOT EXISTS parco_faunistico;
USE parco_faunistico;

-- Tabelle

CREATE TABLE AREA (
    nome VARCHAR(50) NOT NULL,
    orario_apertura TIME NOT NULL,
    orario_chiusura TIME NOT NULL,
    zona_amministrativa VARCHAR(50),
    zona_ricreativa VARCHAR(50),
    habitat VARCHAR(50),
    CONSTRAINT ID_AREA_ID PRIMARY KEY (nome)
);

CREATE TABLE HABITAT (
    nome VARCHAR(50) NOT NULL,
    CONSTRAINT ID_HABIT_AREA_ID PRIMARY KEY (nome)
);

CREATE TABLE ZONA_AMMINISTRATIVA (
    nome VARCHAR(50) NOT NULL,
    CONSTRAINT ID_ZONA__AREA_1_ID PRIMARY KEY (nome)
);

CREATE TABLE ZONA_RICREATIVA (
    nome VARCHAR(50) NOT NULL,
    CONSTRAINT ID_ZONA__AREA_ID PRIMARY KEY (nome)
);

CREATE TABLE PERSONA (
    codice_fiscale CHAR(16) NOT NULL,
    nome VARCHAR(30) NOT NULL,
    cognome VARCHAR(30) NOT NULL,
    eta INT NOT NULL,
    indirizzo VARCHAR(100) NOT NULL,
    telefono VARCHAR(15),
    email VARCHAR(50),
    CONSTRAINT ID_PERSONA_ID PRIMARY KEY (codice_fiscale)
);

CREATE TABLE DIPENDENTE (
    codice_fiscale CHAR(16) NOT NULL,
    mansione VARCHAR(50) NOT NULL,
    descrizione_mansione VARCHAR(200) NOT NULL,
    CONSTRAINT ID_DIPEN_PERSO_ID PRIMARY KEY (codice_fiscale)
);

CREATE TABLE GUIDA (
    codice_fiscale CHAR(16) NOT NULL,
    CONSTRAINT ID_GUIDA_DIPEN_ID PRIMARY KEY (codice_fiscale)
);

CREATE TABLE GIORNATA_LAVORATIVA (
    codice_fiscale CHAR(16) NOT NULL,
    data DATE NOT NULL,
    giorno VARCHAR(10) NOT NULL,
    orario_inizio TIME NOT NULL,
    orario_fine TIME NOT NULL,
    CONSTRAINT ID_GIORNATA_LAVORATIVA_ID PRIMARY KEY (codice_fiscale, data)
);

CREATE TABLE SPECIE (
    nome_scientifico VARCHAR(100) NOT NULL,
    nome_comune VARCHAR(50) NOT NULL,
    abitudini TEXT NOT NULL,
    numero_esemplari INT NOT NULL,
    nome VARCHAR(50) NOT NULL,
    CONSTRAINT ID_SPECIE_ID PRIMARY KEY (nome_scientifico)
);

CREATE TABLE DIETA (
    alimento VARCHAR(50) NOT NULL,
    numero_pasti INT NOT NULL,
    CONSTRAINT ID_DIETA_ID PRIMARY KEY (alimento)
);

CREATE TABLE ESEMPLARE (
    nome VARCHAR(50) NOT NULL,
    sesso CHAR(1) NOT NULL,
    eta INT NOT NULL,
    altezza DECIMAL(5,2),
    peso INT,
    malato BOOLEAN,
    alimento VARCHAR(50) NOT NULL,
    nome_scientifico VARCHAR(100) NOT NULL,
    CONSTRAINT ID_ESEMPLARE_ID PRIMARY KEY (nome)
);

CREATE TABLE PERCORSO (
    codice_percorso VARCHAR(20) NOT NULL,
    durata TIME NOT NULL,
    numero_aree INT NOT NULL,
    CONSTRAINT ID_PERCORSO_ID PRIMARY KEY (codice_percorso)
);

CREATE TABLE VISITATORE (
    codice_fiscale CHAR(16) NOT NULL,
    CONSTRAINT ID_VISIT_PERSO_ID PRIMARY KEY (codice_fiscale)
);

CREATE TABLE GRUPPO (
    codice_gruppo VARCHAR(20) NOT NULL,
    numero_partecipanti INT NOT NULL,
    CONSTRAINT ID_GRUPPO_ID PRIMARY KEY (codice_gruppo)
);

CREATE TABLE SCONTO (
    codice_sconto VARCHAR(20) NOT NULL,
    percentuale DECIMAL(5,2) NOT NULL,
    tipologia VARCHAR(50) NOT NULL,
    CONSTRAINT ID_SCONTO_ID PRIMARY KEY (codice_sconto)
);

CREATE TABLE PAGAMENTO_VISITA (
    codice_transazione VARCHAR(20) NOT NULL,
    codice_fiscale CHAR(16),
    codice_gruppo VARCHAR(20),
    data_effettuazione DATE NOT NULL,
    codice_sconto VARCHAR(20),
    nome VARCHAR(50),
    CONSTRAINT ID_PAGAMENTO_VISITA_ID UNIQUE (codice_fiscale, codice_transazione),
    CONSTRAINT SID_PAGAMENTO_VISITA_ID UNIQUE (codice_gruppo, codice_transazione)
);

CREATE TABLE BIGLIETTO (
    codice_biglietto VARCHAR(20) NOT NULL,
    data_validita DATE NOT NULL,
    codice_fiscale CHAR(16),
    codice_gruppo VARCHAR(20),
    codice_transazione VARCHAR(20) NOT NULL,
    prezzo_base DECIMAL(7,2) NOT NULL,
    prezzo_effettivo DECIMAL(7,2) NOT NULL,
    codice_percorso VARCHAR(20) NOT NULL,
    CONSTRAINT ID_BIGLIETTO_ID PRIMARY KEY (codice_biglietto, data_validita),
    CONSTRAINT SID_BIGLI_PAGAM_ID UNIQUE (codice_fiscale, codice_transazione)
);

CREATE TABLE PRODOTTO (
    codice_prodotto VARCHAR(20) NOT NULL,
    nome VARCHAR(150) NOT NULL,
    descrizione TEXT,
    prezzo_unitario DECIMAL(10,2) NOT NULL,
    CONSTRAINT PK_PRODOTTO PRIMARY KEY (codice_prodotto)
);

CREATE TABLE ORDINE (
    codice_prodotto VARCHAR(20) NOT NULL,
    codice_ordine VARCHAR(20) NOT NULL,
    quantita_acquistata INT NOT NULL,
    data DATE NOT NULL,
    nome VARCHAR(50) NOT NULL,
    CONSTRAINT ID_ORDINE_ID PRIMARY KEY (codice_prodotto, codice_ordine)
);

CREATE TABLE composizione (
    nome VARCHAR(50) NOT NULL,
    data DATE NOT NULL,
    codice_prodotto VARCHAR(20) NOT NULL,
    codice_ordine VARCHAR(20) NOT NULL,
    CONSTRAINT ID_composizione_ID PRIMARY KEY (codice_prodotto, codice_ordine, nome, data)
);


CREATE TABLE RENDIMENTO_GIORNALIERO (
    nome VARCHAR(50) NOT NULL,
    data DATE NOT NULL,
    numero_vendite INT NOT NULL,
    fatturato DECIMAL(10,2) NOT NULL,
    CONSTRAINT ID_RENDIMENTO_GIORNALIERO_ID PRIMARY KEY (nome, data)
);

CREATE TABLE lavora (
    nome VARCHAR(50) NOT NULL,
    codice_fiscale CHAR(16) NOT NULL,
    CONSTRAINT ID_lavora_ID PRIMARY KEY (codice_fiscale, nome)
);

CREATE TABLE gestione (
    codice_fiscale CHAR(16) NOT NULL,
    codice_percorso VARCHAR(20) NOT NULL,
    CONSTRAINT ID_gestione_ID PRIMARY KEY (codice_fiscale, codice_percorso)
);

CREATE TABLE partecipazione (
    codice_gruppo VARCHAR(20) NOT NULL,
    codice_fiscale CHAR(16) NOT NULL,
    CONSTRAINT ID_partecipazione_ID PRIMARY KEY (codice_gruppo, codice_fiscale)
);

CREATE TABLE richiesta (
    codice_prodotto VARCHAR(20) NOT NULL,
    codice_ordine VARCHAR(20) NOT NULL,
    codice_fiscale CHAR(16) NOT NULL,
    CONSTRAINT ID_richiesta_ID PRIMARY KEY (codice_prodotto, codice_ordine, codice_fiscale)
);

CREATE TABLE visita (
    codice_fiscale CHAR(16) NOT NULL,
    nome VARCHAR(50) NOT NULL,
    data DATE NOT NULL,
    CONSTRAINT ID_visita_ID PRIMARY KEY (codice_fiscale, nome)
);

CREATE TABLE MANUTENZIONE (
    nome VARCHAR(50) NOT NULL,
    data_inizio DATE NOT NULL,
    data_fine DATE NOT NULL,
    CONSTRAINT ID_MANUTENZIONE_ID PRIMARY KEY (nome, data_inizio)
);



-- Constraints Section
-- ___________________ 

alter table AREA add constraint EXTONE_AREA
     check((ZONA_RICREATIVA is not null and ZONA_AMMINISTRATIVA is null and HABITAT is null)
           or (ZONA_RICREATIVA is null and ZONA_AMMINISTRATIVA is not null and HABITAT is null)
           or (ZONA_RICREATIVA is null and ZONA_AMMINISTRATIVA is null and HABITAT is not null)); 

alter table BIGLIETTO add constraint REF_BIGLI_PERCO_FK
     foreign key (codice_percorso)
     references PERCORSO(codice_percorso);

alter table BIGLIETTO add constraint SID_BIGLI_PAGAM_FK
     foreign key (codice_fiscale, codice_transazione)
     references PAGAMENTO_VISITA(codice_fiscale, codice_transazione);

alter table composizione add constraint REF_compo_ORDIN
     foreign key (codice_prodotto, codice_ordine)
     references ORDINE(codice_prodotto, codice_ordine);

alter table composizione add constraint REF_compo_RENDI_FK
     foreign key (nome, data)
     references RENDIMENTO_GIORNALIERO(nome, data);

/*alter table DIETA add constraint ID_DIETA_CHK
     check(exists(select * from ESEMPLARE
                  where ESEMPLARE.alimento = alimento));*/

alter table DIPENDENTE add constraint ID_DIPEN_PERSO_FK
     foreign key (codice_fiscale)
     references PERSONA(codice_fiscale);

/*alter table DIPENDENTE add constraint ID_DIPEN_PERSO_CHK
     check(exists(select * from GIORNATA_LAVORATIVA
                  where GIORNATA_LAVORATIVA.codice_fiscale = codice_fiscale));*/

/*alter table DIPENDENTE add constraint ID_DIPEN_PERSO_CHK
     check(exists(select * from lavora
                  where lavora.codice_fiscale = codice_fiscale));*/

alter table ESEMPLARE add constraint EQU_ESEMP_DIETA_FK
     foreign key (alimento)
     references DIETA(alimento);

alter table ESEMPLARE add constraint EQU_ESEMP_SPECI_FK
     foreign key (nome_scientifico)
     references SPECIE(nome_scientifico);

alter table gestione add constraint REF_gesti_PERCO_FK
     foreign key (codice_percorso)
     references PERCORSO(codice_percorso);

alter table gestione add constraint EQU_gesti_GUIDA
     foreign key (codice_fiscale)
     references GUIDA(codice_fiscale);

alter table GIORNATA_LAVORATIVA add constraint EQU_GIORN_DIPEN
     foreign key (codice_fiscale)
     references DIPENDENTE(codice_fiscale);

/*alter table GRUPPO add constraint ID_GRUPPO_CHK
     check(exists(select * from PAGAMENTO_VISITA
                  where PAGAMENTO_VISITA.codice_gruppo = codice_gruppo)); */

/*alter table GRUPPO add constraint ID_GRUPPO_CHK
     check(exists(select * from partecipazione
                  where partecipazione.codice_gruppo = codice_gruppo)); */

alter table GUIDA add constraint ID_GUIDA_DIPEN_FK
     foreign key (codice_fiscale)
     references DIPENDENTE(codice_fiscale);

/*alter table GUIDA add constraint ID_GUIDA_DIPEN_CHK
     check(exists(select * from gestione
                  where gestione.codice_fiscale = codice_fiscale)); */

/*alter table HABITAT add constraint ID_HABIT_AREA_CHK
     check(exists(select * from SPECIE
                  where SPECIE.nome = nome)); */

alter table HABITAT add constraint ID_HABIT_AREA_FK
     foreign key (nome)
     references AREA(nome);

alter table lavora add constraint EQU_lavor_DIPEN
     foreign key (codice_fiscale)
     references DIPENDENTE(codice_fiscale);

alter table lavora add constraint EQU_lavor_AREA_FK
     foreign key (nome)
     references AREA(nome);

alter table MANUTENZIONE add constraint REF_MANUT_AREA
     foreign key (nome)
     references AREA(nome);

/*alter table ORDINE add constraint ID_ORDINE_CHK
     check(exists(select * from richiesta
                  where richiesta.codice_prodotto = codice_prodotto and richiesta.codice_ordine = codice_ordine)); */

alter table ORDINE add constraint REF_ORDIN_PRODO
     foreign key (codice_prodotto)
     references PRODOTTO(codice_prodotto);

alter table ORDINE add constraint REF_ORDIN_ZONA__FK
     foreign key (nome)
     references ZONA_RICREATIVA(nome);

/*alter table PAGAMENTO_VISITA add constraint ID_PAGAMENTO_VISITA_CHK
     check(exists(select * from BIGLIETTO
                  where BIGLIETTO.codice_fiscale = codice_fiscale and BIGLIETTO.codice_transazione = codice_transazione)); */

alter table PAGAMENTO_VISITA add constraint EQU_PAGAM_VISIT
     foreign key (codice_fiscale)
     references VISITATORE(codice_fiscale);

alter table PAGAMENTO_VISITA add constraint EQU_PAGAM_GRUPP
     foreign key (codice_gruppo)
     references GRUPPO(codice_gruppo);

alter table PAGAMENTO_VISITA add constraint EQU_PAGAM_SCONT_FK
     foreign key (codice_sconto)
     references SCONTO(codice_sconto);

alter table PAGAMENTO_VISITA add constraint EQU_PAGAM_ZONA__FK
     foreign key (nome)
     references ZONA_AMMINISTRATIVA(nome);

alter table partecipazione add constraint REF_parte_VISIT_FK
     foreign key (codice_fiscale)
     references VISITATORE(codice_fiscale);

alter table partecipazione add constraint EQU_parte_GRUPP
     foreign key (codice_gruppo)
     references GRUPPO(codice_gruppo);

alter table RENDIMENTO_GIORNALIERO add constraint EQU_RENDI_ZONA_
     foreign key (nome)
     references ZONA_RICREATIVA(nome);

alter table richiesta add constraint EQU_richi_ORDIN
     foreign key (codice_prodotto, codice_ordine)
     references ORDINE(codice_prodotto, codice_ordine);

alter table richiesta add constraint REF_richi_VISIT_FK
     foreign key (codice_fiscale)
     references VISITATORE(codice_fiscale);

/*alter table SCONTO add constraint ID_SCONTO_CHK
     check(exists(select * from PAGAMENTO_VISITA
                  where PAGAMENTO_VISITA.codice_sconto = codice_sconto)); */

/*alter table SPECIE add constraint ID_SPECIE_CHK
     check(exists(select * from ESEMPLARE
                  where ESEMPLARE.nome_scientifico = nome_scientifico)); */

alter table SPECIE add constraint EQU_SPECI_HABIT_FK
     foreign key (nome)
     references HABITAT(nome);

alter table visita add constraint REF_visit_AREA_FK
     foreign key (nome)
     references AREA(nome);

alter table visita add constraint EQU_visit_VISIT
     foreign key (codice_fiscale)
     references VISITATORE(codice_fiscale);

alter table VISITATORE add constraint ID_VISIT_PERSO_FK
     foreign key (codice_fiscale)
     references PERSONA(codice_fiscale);

/*alter table VISITATORE add constraint ID_VISIT_PERSO_CHK
     check(exists(select * from visita
                  where visita.codice_fiscale = codice_fiscale)); */

/*alter table VISITATORE add constraint ID_VISIT_PERSO_CHK
     check(exists(select * from PAGAMENTO_VISITA
                  where PAGAMENTO_VISITA.codice_fiscale = codice_fiscale)); */

alter table ZONA_AMMINISTRATIVA add constraint ID_ZONA__AREA_1_FK
     foreign key (nome)
     references AREA(nome);

/*alter table ZONA_AMMINISTRATIVA add constraint ID_ZONA__AREA_1_CHK
     check(exists(select * from PAGAMENTO_VISITA
                  where PAGAMENTO_VISITA.nome = nome)); */

alter table ZONA_RICREATIVA add constraint ID_ZONA__AREA_FK
     foreign key (nome)
     references AREA(nome);

/*alter table ZONA_RICREATIVA add constraint ID_ZONA__AREA_CHK
     check(exists(select * from RENDIMENTO_GIORNALIERO
                  where RENDIMENTO_GIORNALIERO.nome = nome)); */


-- Index Section
-- _____________ 

create unique index ID_AREA_IND
     on AREA (nome);

create unique index ID_BIGLIETTO_IND
     on BIGLIETTO (codice_biglietto, data_validita);

create index REF_BIGLI_PERCO_IND
     on BIGLIETTO (codice_percorso);

create unique index SID_BIGLI_PAGAM_IND
     on BIGLIETTO (codice_fiscale, codice_transazione);

create unique index ID_composizione_IND
     on composizione (codice_prodotto, codice_ordine, nome, data);

create index REF_compo_RENDI_IND
     on composizione (nome, data);

create unique index ID_DIETA_IND
     on DIETA (alimento);

create unique index ID_DIPEN_PERSO_IND
     on DIPENDENTE (codice_fiscale);

create unique index ID_ESEMPLARE_IND
     on ESEMPLARE (nome);

create index EQU_ESEMP_DIETA_IND
     on ESEMPLARE (alimento);

create index EQU_ESEMP_SPECI_IND
     on ESEMPLARE (nome_scientifico);

create unique index ID_gestione_IND
     on gestione (codice_fiscale, codice_percorso);

create index REF_gesti_PERCO_IND
     on gestione (codice_percorso);

create unique index ID_GIORNATA_LAVORATIVA_IND
     on GIORNATA_LAVORATIVA (codice_fiscale, data);

create unique index ID_GRUPPO_IND
     on GRUPPO (codice_gruppo);

create unique index ID_GUIDA_DIPEN_IND
     on GUIDA (codice_fiscale);

create unique index ID_HABIT_AREA_IND
     on HABITAT (nome);

create unique index ID_lavora_IND
     on lavora (codice_fiscale, nome);

create index EQU_lavor_AREA_IND
     on lavora (nome);

create unique index ID_MANUTENZIONE_IND
     on MANUTENZIONE (nome, data_inizio);

create unique index ID_ORDINE_IND
     on ORDINE (codice_prodotto, codice_ordine);

create index REF_ORDIN_ZONA__IND
     on ORDINE (nome);

create unique index ID_PAGAMENTO_VISITA_IND
     on PAGAMENTO_VISITA (codice_fiscale, codice_transazione);

create unique index SID_PAGAMENTO_VISITA_IND
     on PAGAMENTO_VISITA (codice_gruppo, codice_transazione);

create index EQU_PAGAM_SCONT_IND
     on PAGAMENTO_VISITA (codice_sconto);

create index EQU_PAGAM_ZONA__IND
     on PAGAMENTO_VISITA (nome);

create unique index ID_partecipazione_IND
     on partecipazione (codice_gruppo, codice_fiscale);

create index REF_parte_VISIT_IND
     on partecipazione (codice_fiscale);

create unique index ID_PERCORSO_IND
     on PERCORSO (codice_percorso);

create unique index ID_PERSONA_IND
     on PERSONA (codice_fiscale);

create unique index ID_PRODOTTO_IND
     on PRODOTTO (codice_prodotto);

create unique index ID_RENDIMENTO_GIORNALIERO_IND
     on RENDIMENTO_GIORNALIERO (nome, data);

create unique index ID_richiesta_IND
     on richiesta (codice_prodotto, codice_ordine, codice_fiscale);

create index REF_richi_VISIT_IND
     on richiesta (codice_fiscale);

create unique index ID_SCONTO_IND
     on SCONTO (codice_sconto);

create unique index ID_SPECIE_IND
     on SPECIE (nome_scientifico);

create index EQU_SPECI_HABIT_IND
     on SPECIE (nome);

create unique index ID_visita_IND
     on visita (codice_fiscale, nome);

create index REF_visit_AREA_IND
     on visita (nome);

create unique index ID_VISIT_PERSO_IND
     on VISITATORE (codice_fiscale);

create unique index ID_ZONA__AREA_1_IND
     on ZONA_AMMINISTRATIVA (nome);

create unique index ID_ZONA__AREA_IND
     on ZONA_RICREATIVA (nome);