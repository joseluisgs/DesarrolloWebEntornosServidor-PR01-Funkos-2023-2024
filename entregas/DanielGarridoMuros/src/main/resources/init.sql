CREATE TABLE IF NOT EXISTS funkos (
   id INT AUTO_INCREMENT PRIMARY KEY,
   cod UUID DEFAULT RANDOM_UUID() NOT NULL,
   nombre VARCHAR(255) NOT NULL,
   modelo VARCHAR(20) CHECK (modelo IN ('MARVEL', 'DISNEY', 'ANIME', 'OTROS')),
   precio DECIMAL(6,2),
   fecha_lanzamiento DATE,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

INSERT INTO funkos (cod, nombre, modelo, precio, fecha_lanzamiento) VALUES('999c6f58-79b9-434b-82ab-01a2d6e4434a', 'Spiderman', 'MARVEL', 15.99, '2022-05-01');
