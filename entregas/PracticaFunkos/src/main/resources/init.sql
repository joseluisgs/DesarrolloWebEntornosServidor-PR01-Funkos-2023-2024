CREATE TABLE IF NOT EXISTS FUNKOS (
                                      ID INT AUTO_INCREMENT PRIMARY KEY,
                                      COD UUID NOT NULL DEFAULT RANDOM_UUID(),
                                      NOMBRE VARCHAR(255),
                                      MODELO VARCHAR(255) CHECK (MODELO IN ('MARVEL', 'DISNEY', 'ANIME', 'OTROS')),
                                      PRECIO DECIMAL(10, 2),
                                      FECHA_LANZAMIENTO DATE,
                                      CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
