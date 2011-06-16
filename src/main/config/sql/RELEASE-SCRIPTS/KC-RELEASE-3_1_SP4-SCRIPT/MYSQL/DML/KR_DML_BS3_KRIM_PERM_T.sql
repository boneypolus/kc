INSERT INTO KRIM_PERM_ID_BS_S VALUES (NULL);
INSERT INTO KRIM_PERM_T (PERM_ID,NMSPC_CD,NM,DESC_TXT,PERM_TMPL_ID,ACTV_IND,VER_NBR,OBJ_ID)
  VALUES ((SELECT MAX(ID) FROM KRIM_PERM_ID_BS_S),'KC-PROTOCOL','Create Any Amendment','Create amendments on any protocol',
         (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KC-IDM' AND NM = 'Perform Document Action'),
         'Y',1,UUID());

INSERT INTO KRIM_PERM_ID_BS_S VALUES (NULL);
INSERT INTO KRIM_PERM_T (PERM_ID,NMSPC_CD,NM,DESC_TXT,PERM_TMPL_ID,ACTV_IND,VER_NBR,OBJ_ID)
  VALUES ((SELECT MAX(ID) FROM KRIM_PERM_ID_BS_S),'KC-PROTOCOL','Create Any Renewal','Create renewals for any protocol',
         (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KC-IDM' AND NM = 'Perform Document Action'),
         'Y',1,UUID());

INSERT INTO KRIM_PERM_ID_BS_S VALUES (NULL);
INSERT INTO KRIM_PERM_T (PERM_ID,NMSPC_CD,NM,DESC_TXT,PERM_TMPL_ID,ACTV_IND,VER_NBR,OBJ_ID)
  VALUES ((SELECT MAX(ID) FROM KRIM_PERM_ID_BS_S),'KC-PROTOCOL','Submit Any Protocol','Submit Any Protocol',
         (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KC-IDM' AND NM = 'Perform Document Action'),
         'Y',1,UUID());

INSERT INTO KRIM_PERM_ID_BS_S VALUES (NULL);
INSERT INTO KRIM_PERM_T (PERM_ID,NMSPC_CD,NM,DESC_TXT,PERM_TMPL_ID,ACTV_IND,VER_NBR,OBJ_ID)
  VALUES ((SELECT MAX(ID) FROM KRIM_PERM_ID_BS_S),'KC-PROTOCOL','Maintain Protocol Review Comments','Maintain Protocol Review Comments',
         (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KC-IDM' AND NM = 'Edit Document Section'),
         'Y',1,UUID());

INSERT INTO KRIM_PERM_ID_BS_S VALUES (NULL);
INSERT INTO KRIM_PERM_T (PERM_ID,NMSPC_CD,NM,DESC_TXT,PERM_TMPL_ID,ACTV_IND,VER_NBR,OBJ_ID)
  VALUES ((SELECT MAX(ID) FROM KRIM_PERM_ID_BS_S),'KC-PROTOCOL','Maintain Protocol Related Proj','Maintain Protocols link to award and proposal',
         (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KC-IDM' AND NM = 'Edit Document Section'),
         'Y',1,UUID());

INSERT INTO KRIM_PERM_ID_BS_S VALUES (NULL);
INSERT INTO KRIM_PERM_T (PERM_ID,NMSPC_CD,NM,DESC_TXT,PERM_TMPL_ID,ACTV_IND,VER_NBR,OBJ_ID)
  VALUES ((SELECT MAX(ID) FROM KRIM_PERM_ID_BS_S),'KC-PROTOCOL','Maintain Any Protocol Access','Maintain Any Protocol Access',
         (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KC-IDM' AND NM = 'Edit Document Section'),
         'Y',1,UUID());

INSERT INTO KRIM_PERM_ID_BS_S VALUES (NULL);
INSERT INTO KRIM_PERM_T (PERM_ID,NMSPC_CD,NM,DESC_TXT,PERM_TMPL_ID,ACTV_IND,VER_NBR,OBJ_ID)
  VALUES ((SELECT MAX(ID) FROM KRIM_PERM_ID_BS_S),'KC-PROTOCOL','Add Any Protocol Notes','Add Any Protocol Notes',
         (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KC-IDM' AND NM = 'Edit Document Section'),
         'Y',1,UUID());
