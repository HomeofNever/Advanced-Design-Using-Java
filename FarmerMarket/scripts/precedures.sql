DELIMITER //
DROP PROCEDURE IF EXISTS calc_distance //
CREATE PROCEDURE
    calc_distance(city CHAR(100), state CHAR(2), distance integer)
BEGIN
    DECLARE lat double;
    DECLARE lng double;
    DECLARE finished INTEGER DEFAULT 0;
    DECLARE loc_cur CURSOR FOR SELECT latitude, longitude
                               FROM zip_codes_states
                               WHERE zip_codes_states.city LIKE city AND zip_codes_states.state LIKE state;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET finished = 1;
    OPEN loc_cur;
    CREATE TEMPORARY TABLE IF NOT EXISTS fmids_total
    (
        fmid CHAR(25)
    );

    getLoc:
    LOOP
        FETCH loc_cur INTO lat, lng;
        IF finished = 1 THEN
            LEAVE getLoc;
        END IF;
        INSERT INTO fmids_total
        SELECT fmid
        FROM data
        WHERE 3959 * acos(
                        cos(radians(lat))
                        * cos(radians(y))
                        * cos(radians(x) - radians(lng))
                    + sin(radians(lat))
                            * sin(radians(y))
            ) < distance;
    END LOOP getLoc;
    CLOSE loc_cur;

    SELECT * FROM data WHERE FMID in (SELECT DISTINCT fmid from fmids_total);
    DROP TEMPORARY TABLE fmids_total;
END //

DELIMITER ;

# call calc_distance('Troy', 'NY', 100)