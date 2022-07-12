
CREATE TABLE if not exists NHSSPEC (
    id INT PRIMARY KEY,
    groupe VARCHAR(128) NOT NULL,
    spec VARCHAR(128) NOT NULL
) ;

CREATE TABLE if not exists HOSPITALS (
    id INT PRIMARY KEY,
    name VARCHAR(128) NOT NULL,
    bed INT NOT NULL,
    latitude FLOAT NOT NULL,
    longitude FLOAT NOT NULL,
    distance FLOAT NOT NULL,
    distancestr VARCHAR(128) NOT NULL,
    duration VARCHAR(128) NOT NULL
) ;

CREATE TABLE if not exists HOSPITAL_SPEC (
    id INT PRIMARY KEY,
    id_hospital INT NOT NULL,
    id_spec INT NOT NULL
) ;