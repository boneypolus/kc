create or replace view OSP$BUDGET_PERSONS as 
	select PROPOSAL_NUMBER,VERSION_NUMBER,PERSON_ID,JOB_CODE,EFFECTIVE_DATE,CALCULATION_BASE,
	APPOINTMENT_TYPE,PERSON_NAME,NON_EMPLOYEE_FLAG,UPDATE_TIMESTAMP,UPDATE_USER
	
	from BUDGET_PERSONS;