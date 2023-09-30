DROP TABLE IF EXISTS FUNKO;
CREATE TABLE IF NOT EXISTS FUNKO (
                                       id INTEGER PRIMARY KEY AUTO_INCREMENT,
                                       uuid UUID NOT NULL DEFAULT RANDOM_UUID(),
                                       nombre VARCHAR(255) NOT NULL,
    modelo ENUM('MARVEL', 'DISNEY', 'ANIME', 'OTROS')NOT NULL,
    precio DOUBLE NOT NULL,
    fecha_lanzamiento DATE NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    );

