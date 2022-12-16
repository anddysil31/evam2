CREATE TABLE IF NOT EXISTS conference(
    id SERIAL NOT NULL,
    title VARCHAR(50) NOT NULL,
    speaker VARCHAR(50) NOT NULL,
    hour TIME NOT NULL,
    day DATE NOT NULL,
    total_attendees INT NOT NULL,
    event_id INT NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(event_id) REFERENCES event(id)
)
