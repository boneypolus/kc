ALTER TABLE IACUC_PROTOCOL_FUNDING_SOURCE 
ADD CONSTRAINT FK_IACUC_PROTO_FUND_SOURCE_TYP 
FOREIGN KEY (FUNDING_SOURCE_TYPE_CODE) 
REFERENCES FUNDING_SOURCE_TYPE (FUNDING_SOURCE_TYPE_CODE)
/

ALTER TABLE IACUC_PROTOCOL_FUNDING_SOURCE 
ADD CONSTRAINT FK_IACUC_PROTO_FUNDING_SOURCE 
FOREIGN KEY (PROTOCOL_ID) 
REFERENCES IACUC_PROTOCOL (PROTOCOL_ID)
/

