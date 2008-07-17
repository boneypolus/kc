CREATE TABLE EPS_PROP_YNQ 
   (	"PROPOSAL_NUMBER" NUMBER(12,0) constraint EPS_PROP_YNQN1 NOT NULL ENABLE, 
	"QUESTION_ID" VARCHAR2(4) constraint EPS_PROP_YNQN2 NOT NULL ENABLE, 
	"ANSWER" CHAR(1), 
	"EXPLANATION" LONG, 
	"REVIEW_DATE" DATE, 
	"UPDATE_TIMESTAMP" DATE constraint EPS_PROP_YNQN4 NOT NULL ENABLE, 
	"UPDATE_USER" VARCHAR2(60) constraint EPS_PROP_YNQN5 NOT NULL ENABLE, 
	 "VER_NBR" NUMBER(8,0) DEFAULT 1  constraint  EPS_PROP_YNQN6  NOT NULL ENABLE,
	"OBJ_ID" VARCHAR2(36) DEFAULT SYS_GUID()  constraint  EPS_PROP_YNQN7  NOT NULL ENABLE,
	CONSTRAINT "PK_EPS_PROP_YNQ_KRA" PRIMARY KEY ("PROPOSAL_NUMBER", "QUESTION_ID") ENABLE
)
/

ALTER TABLE EPS_PROP_YNQ ADD (CONSTRAINT "FK_EPS_PROP_YNQ_KRA" FOREIGN KEY ("PROPOSAL_NUMBER")
	  REFERENCES "EPS_PROPOSAL" ("PROPOSAL_NUMBER") ) 
/


 ALTER TABLE EPS_PROP_YNQ ADD (CONSTRAINT "FK_EPS_PROP_YNQ_ID_KRA" FOREIGN KEY ("QUESTION_ID")
	  REFERENCES "YNQ" ("QUESTION_ID") )
/
