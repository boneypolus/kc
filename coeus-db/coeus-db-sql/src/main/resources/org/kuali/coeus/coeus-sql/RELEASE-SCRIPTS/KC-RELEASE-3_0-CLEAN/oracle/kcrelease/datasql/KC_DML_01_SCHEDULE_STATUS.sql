TRUNCATE TABLE SCHEDULE_STATUS DROP STORAGE
/
INSERT INTO SCHEDULE_STATUS (SCHEDULE_STATUS_CODE,DESCRIPTION,UPDATE_USER,UPDATE_TIMESTAMP,OBJ_ID,VER_NBR) 
    VALUES (1,'Scheduled','admin',SYSDATE,SYS_GUID(),1)
/
INSERT INTO SCHEDULE_STATUS (SCHEDULE_STATUS_CODE,DESCRIPTION,UPDATE_USER,UPDATE_TIMESTAMP,OBJ_ID,VER_NBR) 
    VALUES (2,'Cancelled','admin',SYSDATE,SYS_GUID(),1)
/
INSERT INTO SCHEDULE_STATUS (SCHEDULE_STATUS_CODE,DESCRIPTION,UPDATE_USER,UPDATE_TIMESTAMP,OBJ_ID,VER_NBR) 
    VALUES (3,'Agenda Closed','admin',SYSDATE,SYS_GUID(),1)
/
INSERT INTO SCHEDULE_STATUS (SCHEDULE_STATUS_CODE,DESCRIPTION,UPDATE_USER,UPDATE_TIMESTAMP,OBJ_ID,VER_NBR) 
    VALUES (4,'Committee has met','admin',SYSDATE,SYS_GUID(),1)
/
