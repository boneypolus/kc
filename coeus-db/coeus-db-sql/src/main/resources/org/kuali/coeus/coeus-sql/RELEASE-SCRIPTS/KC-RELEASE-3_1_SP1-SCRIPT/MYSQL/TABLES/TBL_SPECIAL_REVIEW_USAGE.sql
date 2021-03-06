CREATE TABLE SPECIAL_REVIEW_USAGE (
  SPECIAL_REVIEW_USAGE_ID DECIMAL(12,0) COLLATE utf8_bin NOT NULL,
  SPECIAL_REVIEW_CODE     VARCHAR(3) COLLATE utf8_bin NOT NULL, 
  MODULE_CODE             DECIMAL(3, 0) COLLATE utf8_bin NOT NULL,
  ACTIVE_FLAG             CHAR(1) COLLATE utf8_bin,
  UPDATE_TIMESTAMP        DATE COLLATE utf8_bin NOT NULL, 
  UPDATE_USER             VARCHAR(60) COLLATE utf8_bin NOT NULL, 
  VER_NBR                 DECIMAL(8,0) COLLATE utf8_bin DEFAULT 1 NOT NULL,
  OBJ_ID                  VARCHAR(36) COLLATE utf8_bin NOT NULL
) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin;

ALTER TABLE SPECIAL_REVIEW_USAGE 
  ADD CONSTRAINT PK_SPECIAL_REVIEW_USAGE PRIMARY KEY (SPECIAL_REVIEW_USAGE_ID);