DELIMITER /

UPDATE KRCR_PARM_T SET VAL = 'N' WHERE NMSPC_CD = 'KC-GEN' AND PARM_NM = 'EMAIL_NOTIFICATION_TEST_ENABLED'
/
UPDATE KRCR_PARM_T SET VAL = 'kc.grants.gov@kuali.org' WHERE NMSPC_CD = 'KC-GEN' AND PARM_NM = 'EMAIL_NOTIFICATION_TEST_ADDRESS'
/
UPDATE KRCR_PARM_T SET VAL = 'kc.grants.gov@kuali.org' WHERE NMSPC_CD = 'KC-GEN' AND PARM_NM = 'EMAIL_NOTIFICATION_FROM_ADDRESS'
/
DELIMITER ;
