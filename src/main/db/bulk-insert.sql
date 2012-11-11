CREATE PROCEDURE `BulkInsert`()
BEGIN
	
DECLARE i INT DEFAULT 1;
    DECLARE j INT DEFAULT 1;
    DECLARE bulksize INT DEFAULT 	10000;
    DECLARE totalsize INT DEFAULT 10000000;
    
    CREATE TEMPORARY TABLE IF NOT EXISTS t (id INT, bname VARCHAR(255), cname VARCHAR(255)) ENGINE = MEMORY;
		TRUNCATE TABLE t;

    WHILE i <= totalsize DO
				SET j =0;
				SET AUTOCOMMIT=0;
        START TRANSACTION;
				WHILE j < bulksize DO
            INSERT INTO t VALUES (j+i, CONCAT('图书',j+i), CONCAT('用户',j+i));
            SET j = j + 1; 
        END WHILE;
        INSERT IGNORE INTO book (id, title) SELECT id,bname FROM t;
        INSERT IGNORE INTO customer (id, name) SELECT id,cname FROM t;
        TRUNCATE TABLE t;
				COMMIT;
				SET AUTOCOMMIT=1;

        SET i = i + bulksize; 
    END WHILE;
    DROP TABLE t;
END;

