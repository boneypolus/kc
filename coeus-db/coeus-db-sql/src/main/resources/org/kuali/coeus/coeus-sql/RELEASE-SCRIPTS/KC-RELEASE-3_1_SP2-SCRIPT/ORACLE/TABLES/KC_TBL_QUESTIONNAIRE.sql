-- These must be run together to avoid errors --
ALTER TABLE QUESTIONNAIRE 
  ADD (DOCUMENT_NUMBER_TEMP VARCHAR2(40));
UPDATE QUESTIONNAIRE
  SET DOCUMENT_NUMBER_TEMP = DOCUMENT_NUMBER;  
ALTER TABLE QUESTIONNAIRE
  DROP COLUMN DOCUMENT_NUMBER;
ALTER TABLE QUESTIONNAIRE 
  ADD (DOCUMENT_NUMBER VARCHAR2(40));
UPDATE QUESTIONNAIRE
  SET DOCUMENT_NUMBER = DOCUMENT_NUMBER_TEMP;  
ALTER TABLE QUESTIONNAIRE
  DROP COLUMN DOCUMENT_NUMBER_TEMP;