CREATE TABLE IF NOT EXISTS member(
    id SERIAL NOT NULL,
    fullname VARCHAR(50) NOT NULL,
    email VARCHAR(25) NOT NULL,
    age INT NOT NULL,
    PRIMARY KEY(id)
    )
