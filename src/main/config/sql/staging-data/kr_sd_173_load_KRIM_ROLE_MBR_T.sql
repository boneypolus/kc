INSERT INTO KRIM_ROLE_MBR_T (ROLE_MBR_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, OBJ_ID, VER_NBR, LAST_UPDT_DT) 
  VALUES (KRIM_ROLE_MBR_ID_S.NEXTVAL, 
         (SELECT ROLE_ID FROM KRIM_ROLE_T RT WHERE RT.ROLE_NM = 'Modify all Protocols'), 
         '10000000001', 'P', SYS_GUID(), 1, SYSDATE)
/
INSERT INTO KRIM_ROLE_MBR_T (ROLE_MBR_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, OBJ_ID, VER_NBR, LAST_UPDT_DT) 
  VALUES (KRIM_ROLE_MBR_ID_S.NEXTVAL, 
         (SELECT ROLE_ID FROM KRIM_ROLE_T RT WHERE RT.ROLE_NM = 'Modify all Protocols'), 
         '10000000112', 'P', SYS_GUID(), 1, SYSDATE)
/
