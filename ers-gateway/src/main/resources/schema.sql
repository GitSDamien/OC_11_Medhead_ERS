

CREATE TABLE if not exists APIKEY (
    id INT PRIMARY KEY,
    token VARCHAR(255) NOT NULL,
    route VARCHAR(255) NOT NULL
) ;
