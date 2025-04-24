-- Obtener Filmografía de un Actor--
 DELIMITER //
CREATE PROCEDURE obtener_filmografia_actor(IN actor_id_param INT)
BEGIN
    SELECT 
        p.titulo AS Pelicula,
        p.año_estreno AS Año,
        p.duracion AS 'Duración (min)',
        g.nombre AS Genero,
        r.personaje AS Personaje
    FROM 
        reparto r
    JOIN 
        pelicula p ON r.pelicula_id = p.pelicula_id
    JOIN 
        genero g ON p.genero_id = g.genero_id
    WHERE 
        r.actor_id = actor_id_param
    ORDER BY 
        p.año_estreno DESC;
END //
DELIMITER ;

-- Disparador: Validar Año de Nacimiento del Actor--
DELIMITER //
CREATE TRIGGER validar_edad_actor
BEFORE INSERT ON actor FOR EACH ROW
BEGIN
    DECLARE error_edad CONDITION FOR SQLSTATE '70001';
    DECLARE año_actual INT;
        SET año_actual = YEAR(CURDATE());  
   -- Verificar que el actor tenga entre 5 y 120 años
    IF NEW.año_nacimiento IS NOT NULL AND 
       (NEW.año_nacimiento < (año_actual - 120) OR NEW.año_nacimiento > (año_actual - 5)) THEN
        SIGNAL error_edad
        SET MESSAGE_TEXT = 'El año de nacimiento no es válido. El actor debe tener entre 5 y 120 años',
        MYSQL_ERRNO = 7001;
    END IF;  
      -- Validar la nacionalidad usando la función creada
    IF NOT validar_nacionalidad(NEW.nacionalidad) THEN
        SIGNAL SQLSTATE '70002'
        SET MESSAGE_TEXT = 'Nacionalidad no válida. Use uno de los valores permitidos',
        MYSQL_ERRNO = 7002;
    END IF;
END //
DELIMITER ;
