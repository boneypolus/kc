ALTER TABLE RESEARCH_AREAS ADD ACTIVE VARCHAR(1);
UPDATE RESEARCH_AREAS SET ACTIVE = 'Y';
ALTER TABLE RESEARCH_AREAS MODIFY ACTIVE VARCHAR(1) NOT NULL;
