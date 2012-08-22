INSERT INTO IACUC_BATCH_CORRESPONDENCE (BATCH_CORRESPONDENCE_TYPE_CODE, DESCRIPTION, DAYS_TO_EVENT_UI_TEXT, SEND_CORRESPONDENCE, FINAL_ACTION_DAY, FINAL_ACTION_TYPE_CODE, FINAL_ACTION_CORRESP_TYPE, UPDATE_USER, UPDATE_TIMESTAMP, OBJ_ID, VER_NBR) 
    VALUES ('1', 'Protocol Renewal Reminders', 'Days prior Protocol Expiration', 'BEFORE', 0, (SELECT PROTOCOL_ACTION_TYPE_CODE FROM IACUC_PROTOCOL_ACTION_TYPE WHERE DESCRIPTION = 'Expired'), null, 'admin', SYSDATE, SYS_GUID(), 1)
/

INSERT INTO IACUC_BATCH_CORRESPONDENCE (BATCH_CORRESPONDENCE_TYPE_CODE, DESCRIPTION, DAYS_TO_EVENT_UI_TEXT, SEND_CORRESPONDENCE, FINAL_ACTION_DAY, FINAL_ACTION_TYPE_CODE, FINAL_ACTION_CORRESP_TYPE, UPDATE_USER, UPDATE_TIMESTAMP, OBJ_ID, VER_NBR) 
    VALUES ('2', 'Reminder to IACUC Notifications', 'Days since Committee Action', 'AFTER', 30, null, (SELECT PROTO_CORRESP_TYPE_CODE FROM IACUC_PROTOCOL_CORRESP_TYPE WHERE DESCRIPTION = 'Notify IACUC Letter'), 'admin', SYSDATE, SYS_GUID(), 1)
/



INSERT INTO IACUC_BATCH_CORRESP_DETAIL (BATCH_CORRESPONDENCE_DETAIL_ID, BATCH_CORRESPONDENCE_TYPE_CODE, PROTO_CORRESP_TYPE_CODE, DAYS_TO_EVENT, UPDATE_USER, UPDATE_TIMESTAMP, OBJ_ID, VER_NBR) 
    VALUES (SEQ_IACUC_PROTOCOL_ID.NEXTVAL, (SELECT BATCH_CORRESPONDENCE_TYPE_CODE FROM IACUC_BATCH_CORRESPONDENCE WHERE DESCRIPTION = 'Protocol Renewal Reminders'), (SELECT PROTO_CORRESP_TYPE_CODE FROM IACUC_PROTOCOL_CORRESP_TYPE WHERE DESCRIPTION = 'Renewal Reminder Notification'), 60, 'admin', SYSDATE,SYS_GUID(), 1)
/
INSERT INTO IACUC_BATCH_CORRESP_DETAIL (BATCH_CORRESPONDENCE_DETAIL_ID, BATCH_CORRESPONDENCE_TYPE_CODE, PROTO_CORRESP_TYPE_CODE, DAYS_TO_EVENT, UPDATE_USER, UPDATE_TIMESTAMP, OBJ_ID, VER_NBR) 
    VALUES (SEQ_IACUC_PROTOCOL_ID.NEXTVAL, (SELECT BATCH_CORRESPONDENCE_TYPE_CODE FROM IACUC_BATCH_CORRESPONDENCE WHERE DESCRIPTION = 'Protocol Renewal Reminders'), (SELECT PROTO_CORRESP_TYPE_CODE FROM IACUC_PROTOCOL_CORRESP_TYPE WHERE DESCRIPTION = 'Renewal Reminder Notification'), 15, 'admin', SYSDATE,SYS_GUID(), 1)
/
INSERT INTO IACUC_BATCH_CORRESP_DETAIL (BATCH_CORRESPONDENCE_DETAIL_ID, BATCH_CORRESPONDENCE_TYPE_CODE, PROTO_CORRESP_TYPE_CODE, DAYS_TO_EVENT, UPDATE_USER, UPDATE_TIMESTAMP, OBJ_ID, VER_NBR) 
    VALUES (SEQ_IACUC_PROTOCOL_ID.NEXTVAL, (SELECT BATCH_CORRESPONDENCE_TYPE_CODE FROM IACUC_BATCH_CORRESPONDENCE WHERE DESCRIPTION = 'Reminder to IACUC Notifications'), (SELECT PROTO_CORRESP_TYPE_CODE FROM IACUC_PROTOCOL_CORRESP_TYPE WHERE DESCRIPTION = 'Notify IACUC Letter'), 15, 'admin', SYSDATE,SYS_GUID(), 1)
/

