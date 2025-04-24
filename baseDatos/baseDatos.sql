--Creación de la base de datos--
CREATE DATABASE gestionCine;
USE gestionCine;

CREATE TABLE actor (
    actor_id INT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    año_nacimiento INT,
    nacionalidad VARCHAR(50)
);
CREATE TABLE genero (
    genero_id INT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);
CREATE TABLE pelicula (
    pelicula_id INT PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    año_estreno INT,
    duracion INT,
    genero_id INT,
    FOREIGN KEY (genero_id) REFERENCES genero(genero_id)
);
CREATE TABLE reparto (
    pelicula_id INT NOT NULL,
    actor_id INT NOT NULL,
    personaje VARCHAR(100),
    PRIMARY KEY (pelicula_id, actor_id),
    FOREIGN KEY (pelicula_id) REFERENCES pelicula(pelicula_id),
    FOREIGN KEY (actor_id) REFERENCES actor(actor_id)
);
--Filtros--
ALTER TABLE actor
ADD PRIMARY KEY (actor_id);

ALTER TABLE genero
ADD PRIMARY KEY (genero_id),
ADD UNIQUE KEY nombre (nombre);
ALTER TABLE pelicula
ADD PRIMARY KEY (pelicula_id),
ADD KEY genero_id (genero_id);

ALTER TABLE reparto
ADD PRIMARY KEY (pelicula_id, actor_id),
ADD KEY actor_id (actor_id);

ALTER TABLE actor
MODIFY actor_id int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
ALTER TABLE genero
MODIFY genero_id int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

ALTER TABLE pelicula
MODIFY pelicula_id int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

ALTER TABLE pelicula
ADD CONSTRAINT pelicula_ibfk_1 FOREIGN KEY (genero_id) REFERENCES genero (genero_id);

ALTER TABLE reparto
ADD CONSTRAINT reparto_ibfk_1 FOREIGN KEY (pelicula_id) REFERENCES pelicula (pelicula_id) ON DELETE CASCADE,
ADD CONSTRAINT reparto_ibfk_2 FOREIGN KEY (actor_id) REFERENCES actor (actor_id) ON DELETE CASCADE;

Datos insertados en las tablas
INSERT INTO genero (genero_id, nombre) VALUES
(1, 'Terror'),
(2, 'Drama'),
(3, 'Romance'),
(4, 'Ciencia Ficción'),
(5, 'Aventura'),
(6, 'Fantasía');

INSERT INTO actor (actor_id, nombre, apellidos, año_nacimiento, nacionalidad) VALUES
(1, 'Robert', 'Downey Jr', 1965, 'Estadounidense'),
(2, 'Chris', 'Evans', 1981, 'Estadounidense'),
(3, 'Christian', 'Bale', 1974, 'Británico'),
(4, 'Vera', 'Farmiga', 1973, 'Estadounidense'),
(5, 'Patrick', 'Wilson', 1973, 'Estadounidense'),
(6, 'Bill', 'Skarsgård', 1990, 'Sueco'),
(7, 'Tom', 'Hanks', 1956, 'Estadounidense'),
(8, 'Viggo', 'Mortensen', 1958, 'Estadounidense-Danés'),
(9, 'Leonardo', 'DiCaprio', 1974, 'Estadounidense'),
(10, 'Matthew', 'McConaughey', 1969, 'Estadounidense'),
(11, 'Toni', 'Collette', 1972, 'Australiana'),
(12, 'Alex', 'Wolff', 1997, 'Estadounidense'),
(13, 'Tim', 'Robbins', 1958, 'Estadounidense'),
(14, 'Morgan', 'Freeman', 1937, 'Estadounidense'),
(15, 'Emma', 'Stone', 1988, 'Estadounidense'),
(16, 'Ryan', 'Gosling', 1980, 'Canadiense'),
(17, 'Kate', 'Winslet', 1975, 'Británica'),
(18, 'Harrison', 'Ford', 1942, 'Estadounidense'),
(19, 'Karen', 'Allen', 1951, 'Estadounidense'),
(20, 'Sam', 'Neill', 1947, 'Neozelandés'),
(21, 'Laura', 'Dern', 1967, 'Estadounidense'),
(22, 'Elijah', 'Wood', 1981, 'Estadounidense'),
(23, 'Ian', 'McKellen', 1939, 'Británico'),
(24, 'Daniel', 'Radcliffe', 1989, 'Británico'),
(25, 'Rupert', 'Grint', 1988, 'Británico'),
(26, 'Ana', 'de Armas', 1988, 'Española'),
(27, 'Mark', 'Ruffalo', 1967, 'Estadounidense'),
(28, 'Scarlett', 'Johansson', 1984, 'Estadounidense');

INSERT INTO pelicula (pelicula_id, titulo, año_estreno, duracion, genero_id) VALUES
(1, 'El Conjuro', 2013, 112, 1),
(2, 'Hereditary', 2018, 127, 1),
(3, 'Forrest Gump', 1994, 142, 2),
(4, 'The Shawshank Redemption', 1994, 142, 2),
(5, 'Titanic', 1997, 195, 3),
(6, 'La La Land', 2016, 128, 3),
(7, 'Avengers', 2012, 143, 4),
(8, 'Blade Runner 2049', 2017, 164, 4),
(9, 'Indiana Jones: Raiders of the Lost Ark', 1981, 115, 5),
(10, 'Jurassic Park', 1993, 127, 5),
(11, 'El Señor de los Anillos: La Comunidad del Anillo', 2001, 178, 6),
(12, 'Harry Potter y la Piedra Filosofal', 2001, 152, 6);

INSERT INTO reparto (pelicula_id, actor_id, personaje) VALUES
(1, 4, 'Lorraine Warren'),
(1, 5, 'Ed Warren'),
(2, 11, 'Annie Graham'),
(2, 12, 'Peter Graham'),
(3, 7, 'Forrest Gump'),
(3, 17, 'Jenny Curran'),
(4, 13, 'Andy Dufresne'),
(4, 14, 'Ellis Boyd "Red" Redding'),
(5, 9, 'Jack Dawson'),
(5, 17, 'Rose DeWitt Bukater'),
(6, 16, 'Sebastian Wilder'),
(6, 15, 'Mia Dolan'),
(7, 1, 'Tony Stark/Iron Man'),
(7, 28, 'Natasha Romanoff/Black Widow'),
(8, 16, 'K'),
(8, 26, 'Joi'),
(9, 18, 'Indiana Jones'),
(9, 19, 'Marion Ravenwood'),
(10, 20, 'Dr. Alan Grant'),
(10, 21, 'Dr. Ellie Sattler'),
(11, 22, 'Frodo Bolsón'),
(11, 23, 'Gandalf'),
(12, 24, 'Harry Potter'),
(12, 25, 'Ron Weasley');
