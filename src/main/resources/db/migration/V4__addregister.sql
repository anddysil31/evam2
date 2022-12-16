CREATE TABLE IF NOT EXISTS register(
    id SERIAL NOT NULL,
    code VARCHAR(10) NOT NULL,
    registered_at VARCHAR(40) NOT NULL,
    assisted BOOLEAN NOT NULL,
    conference_id INT NOT NULL,
    member_id INT NOT NULL,
    PRIMARY KEY(id),
    UNIQUE(code),
    FOREIGN KEY(conference_id) REFERENCES conference(id),
    FOREIGN KEY(member_id) REFERENCES member(id)
    )