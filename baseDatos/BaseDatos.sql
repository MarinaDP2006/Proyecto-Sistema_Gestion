-- Crear Base Datos--
CREATE DATABASE gestioncine;

--Tablas--
CREATE TABLE `actor` (
  `actor_id` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `apellidos` varchar(100) NOT NULL,
  `año_nacimiento` int(11) DEFAULT NULL,
  `nacionalidad` varchar(50) DEFAULT NULL
);

CREATE TABLE `genero` (
  `genero_id` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL
);

CREATE TABLE `pelicula` (
  `pelicula_id` int(11) NOT NULL,
  `titulo` varchar(100) NOT NULL,
  `año_estreno` int(11) DEFAULT NULL CHECK (`año_estreno` > 1880),
  `duracion` int(11) DEFAULT NULL,
  `genero_id` int(11) DEFAULT NULL
);

CREATE TABLE `reparto` (
  `pelicula` varchar(100) DEFAULT NULL,
  `actor` varchar(150) DEFAULT NULL,
  `personaje` varchar(100) DEFAULT NULL
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

--Datos insertados en las tablas--
INSERT INTO actor (actor_id, nombre, apellidos, año_nacimiento, nacionalidad) VALUES
(1, Robert, Downey Jr, 1965, Estadounidense),
(2, Chris, Evans, 1981, Estadounidense),
(3, Christian, Bale, 1974, Británico),
(4, Vera, Farmiga, 1973, Estadounidense),
(5, Patrick, Wilson, 1973, Estadounidense),
(6, Bill, Skarsgård, 1990, Sueco),
(7, Tom, Hanks, 1956, Estadounidense),
(8, Viggo, Mortensen, 1958, Estadounidense-Danés),
(9, Leonardo, DiCaprio, 1974, Estadounidense),
(10, Matthew, McConaughey, 1969, Estadounidense);

INSERT INTO genero (genero_id, nombre) VALUES
(4, Ciencia Ficción y Aventura),
(2, Drama),
(3, Romance y Fantasía),
(1, Terror y Suspenso);

INSERT INTO pelicula (pelicula_id, titulo, año_estreno, duracion, genero_id) VALUES
(1, Avengers, 2012, 143, 4),
(2, El Conjuro, 2013, 112, 1),
(3, It, 2017, 135, 1),
(4, Forrest Gump, 1994, 142, 2),
(5, El Señor de los Anillos, 2001, 178, 3),
(6, Titanic, 1997, 195, 3),
(7, Interstellar, 2014, 169, 4);
