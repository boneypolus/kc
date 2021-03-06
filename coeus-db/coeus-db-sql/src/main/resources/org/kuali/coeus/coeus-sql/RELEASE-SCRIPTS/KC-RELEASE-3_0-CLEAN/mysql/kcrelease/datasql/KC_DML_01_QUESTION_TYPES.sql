delimiter /
TRUNCATE TABLE QUESTION_TYPES
/
INSERT INTO QUESTION_TYPES (QUESTION_TYPE_ID,QUESTION_TYPE_NAME,UPDATE_USER,UPDATE_TIMESTAMP,OBJ_ID,VER_NBR) 
    VALUES (1,'Yes/No','admin',NOW(),UUID(),1)
/
INSERT INTO QUESTION_TYPES (QUESTION_TYPE_ID,QUESTION_TYPE_NAME,UPDATE_USER,UPDATE_TIMESTAMP,OBJ_ID,VER_NBR) 
    VALUES (2,'Yes/No/NA','admin',NOW(),UUID(),1)
/
INSERT INTO QUESTION_TYPES (QUESTION_TYPE_ID,QUESTION_TYPE_NAME,UPDATE_USER,UPDATE_TIMESTAMP,OBJ_ID,VER_NBR) 
    VALUES (3,'Number','admin',NOW(),UUID(),1)
/
INSERT INTO QUESTION_TYPES (QUESTION_TYPE_ID,QUESTION_TYPE_NAME,UPDATE_USER,UPDATE_TIMESTAMP,OBJ_ID,VER_NBR) 
    VALUES (4,'Date','admin',NOW(),UUID(),1)
/
INSERT INTO QUESTION_TYPES (QUESTION_TYPE_ID,QUESTION_TYPE_NAME,UPDATE_USER,UPDATE_TIMESTAMP,OBJ_ID,VER_NBR) 
    VALUES (5,'Text','admin',NOW(),UUID(),1)
/
INSERT INTO QUESTION_TYPES (QUESTION_TYPE_ID,QUESTION_TYPE_NAME,UPDATE_USER,UPDATE_TIMESTAMP,OBJ_ID,VER_NBR) 
    VALUES (6,'Lookup','admin',NOW(),UUID(),1)
/
delimiter ;
