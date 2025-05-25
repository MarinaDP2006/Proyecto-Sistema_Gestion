-- Registrar nueva película con validación de género
DELIMITER //
CREATE PROCEDURE registrar_pelicula(
    IN titulo_param VARCHAR(100),
    IN año_param INT,
    IN duracion_param INT,
    IN genero_param INT
)
BEGIN
    IF (SELECT COUNT(*) FROM genero WHERE genero_id = genero_param) > 0 THEN
        INSERT INTO pelicula (titulo, año_estreno, duracion, genero_id)
        VALUES (titulo_param, año_param, duracion_param, genero_param);
    ELSE
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El género especificado no existe.';
    END IF;
END //
DELIMITER ;
-- Actualizar información de un actor con control de existencia: Verifica que el actor existe antes de cambiar algo de su información
DELIMITER //
CREATE PROCEDURE actualizar_actor(
    IN actor_id_param INT,
    IN nombre_param VARCHAR(50),
    IN apellidos_param VARCHAR(100),
    IN año_nacimiento_param INT,
    IN nacionalidad_param VARCHAR(50)
)
BEGIN
    IF (SELECT COUNT(*) FROM actor WHERE actor_id = actor_id_param) > 0 THEN
        UPDATE actor
        SET nombre = nombre_param,
            apellidos = apellidos_param,
            año_nacimiento = año_nacimiento_param,
            nacionalidad = nacionalidad_param
        WHERE actor_id = actor_id_param;
    ELSE
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El actor especificado no existe.';
    END IF;
END //
DELIMITER ;
-- Obtener el total de películas de un actor
DELIMITER //
CREATE PROCEDURE contar_peliculas_actor(
    IN actor_id_param INT
)
BEGIN
    SELECT COUNT(*) AS Total_Peliculas
    FROM reparto
    WHERE actor_id = actor_id_param;
END //
DELIMITER ;
-- Eliminar un género con validación
DELIMITER //
CREATE PROCEDURE eliminar_genero(
    IN genero_id_param INT
)
BEGIN
    IF (SELECT COUNT(*) FROM pelicula WHERE genero_id = genero_id_param) = 0 THEN
        DELETE FROM genero WHERE genero_id = genero_id_param;
    ELSE
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'No se puede eliminar el género, hay películas asociadas.';
    END IF;
END //
DELIMITER ;
-- Actualizar la duración de una película
DELIMITER //
CREATE PROCEDURE actualizar_duracion(
    IN pelicula_id_param INT,
    IN nueva_duracion INT
)
BEGIN
    UPDATE pelicula
    SET duración = nueva_duracion
    WHERE pelicula_id = pelicula_id_param;
END //
DELIMITER ;
-- Obtener la información de todos los actores
DELIMITER //
CREATE PROCEDURE obtener_todos_los_actores()
BEGIN
    SELECT * FROM actor;
END //
DELIMITER ;



-- Funciones:
-- Calcular la edad de un actor
DELIMITER //
CREATE FUNCTION calcular_edad_actor(año_nac INT) RETURNS INT DETERMINISTIC
BEGIN
    RETURN YEAR(CURDATE()) - año_nac;
END //
DELIMITER ;
-- Verificar si un actor es menor de 30 años
DELIMITER //
CREATE FUNCTION es_joven_actor(año_nac INT) RETURNS BOOLEAN DETERMINISTIC
BEGIN
    RETURN (YEAR(CURDATE()) - año_nac < 30);
END //
DELIMITER ;
-- Obtener el nombre completo de un actor
DELIMITER //
CREATE FUNCTION obtener_nombre_completo(actor_id_param INT) RETURNS VARCHAR(100) DETERMINISTIC
BEGIN
    DECLARE nombre_completo VARCHAR(100);
    SELECT CONCAT(nombre, ' ', apellidos)
    INTO nombre_completo
    FROM actor WHERE actor_id = actor_id_param;
    RETURN nombre_completo;
END //
DELIMITER ;
-- Calcular el total de películas en un año específico
DELIMITER //
CREATE FUNCTION contar_peliculas_por_año(año_param INT) RETURNS INT DETERMINISTIC
BEGIN
    DECLARE total INT;
    SELECT COUNT(*) INTO total
    FROM pelicula WHERE año_estreno = año_param;
    RETURN total;
END //
DELIMITER ;

-- Cursores:
-- Recuperar todas las películas de un actor
DELIMITER //
CREATE PROCEDURE obtener_peliculas_actor_cursor(
    IN actor_id_param INT
)
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE peli_titulo VARCHAR(100);
    DECLARE peli_año INT;
    DECLARE cur CURSOR FOR SELECT p.titulo, p.año_estreno FROM pelicula p JOIN reparto r ON p.pelicula_id = r.pelicula_id WHERE r.actor_id = actor_id_param;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    OPEN cur;
    leer_peliculas: LOOP
        FETCH cur INTO peli_titulo, peli_año;
        IF done THEN
            LEAVE leer_peliculas;
        END IF;
        SELECT peli_titulo AS 'Título', peli_año AS 'Año';
    END LOOP;
    CLOSE cur;
END //
DELIMITER ;
-- Listar todos los géneros sin duplicados
DELIMITER //
CREATE PROCEDURE obtener_generos_cursor()
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE genero_nombre VARCHAR(50);
    DECLARE cur CURSOR FOR SELECT DISTINCT nombre FROM genero;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    OPEN cur;
    leer_generos: LOOP
        FETCH cur INTO genero_nombre;
        IF done THEN
            LEAVE leer_generos;
        END IF;
        SELECT genero_nombre AS 'Género';
    END LOOP;
    CLOSE cur;
END //
DELIMITER ;
-- Mostrar todos los actores de una nacionalidad
DELIMITER //
CREATE PROCEDURE obtener_actores_nacionalidad_cursor(
    IN nacionalidad_param VARCHAR(50)
)
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE actor_nombre VARCHAR(50);
    DECLARE cur CURSOR FOR SELECT nombre FROM actor WHERE nacionalidad = nacionalidad_param;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    OPEN cur;
    leer_actores: LOOP
        FETCH cur INTO actor_nombre;
        IF done THEN
            LEAVE leer_actores;
        END IF;
        SELECT actor_nombre AS 'Actor';
    END LOOP;
    CLOSE cur;
END //
DELIMITER ;
-- Listar todos los actores y sus años de nacimiento
DELIMITER //
CREATE PROCEDURE obtener_actores_años_cursor()
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE nombre_actor VARCHAR(50);
    DECLARE año_nacimiento INT;
    DECLARE cur CURSOR FOR SELECT nombre, año_nacimiento FROM actor;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    OPEN cur;
    leer_actores: LOOP
        FETCH cur INTO nombre_actor, año_nacimiento;
        IF done THEN
            LEAVE leer_actores;
        END IF;
        SELECT nombre_actor AS 'Actor', año_nacimiento AS 'Año de Nacimiento';
    END LOOP;
    CLOSE cur;
END //
DELIMITER ;

-- Triggers: --
-- Actualizar género de la película si su género se actualiza
DELIMITER //
CREATE TRIGGER actualizar_genero_pelicula
AFTER UPDATE ON genero
FOR EACH ROW
BEGIN
    UPDATE pelicula
    SET genero_id = NEW.genero_id
    WHERE genero_id = OLD.genero_id;
END //
DELIMITER ;
-- Registrar fecha de eliminación de la película
DELIMITER //
CREATE TRIGGER registrar_fecha_eliminacion
BEFORE DELETE ON pelicula
FOR EACH ROW
BEGIN
    INSERT INTO log_eliminacion (pelicula_id, fecha_eliminacion)
    VALUES (OLD.pelicula_id, NOW());
END //
DELIMITER ;
-- Registrar cambios de actor al actualizar datos de un actor
DELIMITER //
CREATE TRIGGER registrar_cambio_actor
AFTER UPDATE ON actor
FOR EACH ROW
BEGIN
    INSERT INTO log_cambios_actor (actor_id, fecha_cambio, campo_modificado, nuevo_valor)
    VALUES (OLD.actor_id, NOW(), 'nombre', NEW.nombre);
END //
DELIMITER ;

-- Impide la inserción de una película si el actor no existe
DELIMITER //
CREATE TRIGGER verificar_existencia_actor
BEFORE INSERT ON pelicula
FOR EACH ROW
BEGIN
    IF (SELECT COUNT(*) FROM actor WHERE actor_id = NEW.actor_id) = 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El actor especificado no existe.';
    END IF;
END //
DELIMITER ;
