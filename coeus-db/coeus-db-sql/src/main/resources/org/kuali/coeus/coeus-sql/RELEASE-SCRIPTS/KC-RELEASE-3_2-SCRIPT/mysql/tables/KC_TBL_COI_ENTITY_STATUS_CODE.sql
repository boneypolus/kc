DELIMITER /
CREATE TABLE COI_ENTITY_STATUS_CODE ( 
    ENTITY_STATUS_CODE VARCHAR(3) NOT NULL, 
    DESCRIPTION VARCHAR(200), 
    UPDATE_TIMESTAMP DATE, 
    UPDATE_USER VARCHAR(60), 
    VER_NBR DECIMAL(8,0) DEFAULT 1 NOT NULL, 
    OBJ_ID VARCHAR(36) NOT NULL) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/
ALTER TABLE COI_ENTITY_STATUS_CODE 
ADD CONSTRAINT COI_ENTITY_STATUS_COD_PK 
PRIMARY KEY (ENTITY_STATUS_CODE)
/


DELIMITER ;
