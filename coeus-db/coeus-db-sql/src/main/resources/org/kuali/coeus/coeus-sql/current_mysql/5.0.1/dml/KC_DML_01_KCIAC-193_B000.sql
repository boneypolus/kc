DELIMITER /
UPDATE IACUC_PAIN_CATEGORY SET PAIN_LEVEL='0' WHERE PAIN_CATEGORY='None'
/
UPDATE IACUC_PAIN_CATEGORY SET PAIN_LEVEL='1' WHERE PAIN_CATEGORY='A'
/
UPDATE IACUC_PAIN_CATEGORY SET PAIN_LEVEL='2' WHERE PAIN_CATEGORY='B'
/
UPDATE IACUC_PAIN_CATEGORY SET PAIN_LEVEL='3' WHERE PAIN_CATEGORY='C'
/
UPDATE IACUC_PAIN_CATEGORY SET PAIN_LEVEL='4' WHERE PAIN_CATEGORY='D'
/
UPDATE IACUC_PAIN_CATEGORY SET PAIN_LEVEL='5' WHERE PAIN_CATEGORY='E'
/

DELIMITER ;
