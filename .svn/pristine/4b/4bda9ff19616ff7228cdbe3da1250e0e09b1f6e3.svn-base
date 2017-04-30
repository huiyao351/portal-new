-------------------------------------------
-- Export file for user PF_GTIS_JSYD     --
-- Created by ibm on 2016/7/13, 22:35:53 --
-------------------------------------------

spool sub.log

prompt
prompt Creating table PF_SUBSYSTEM
prompt ===========================
prompt
create table PF_SUBSYSTEM
(
  SUBSYSTEM_ID    VARCHAR2(32) not null,
  SUBSYSTEM_NAME  VARCHAR2(50),
  SUBSYSTEM_TITLE VARCHAR2(100),
  ENABLED         NUMBER(1),
  SUB_TYPE        NUMBER(1),
  SUB_NO          NUMBER(4),
  SUB_URL         VARCHAR2(200),
  SUB_MENU_TYPE   VARCHAR2(20)
);
comment on column PF_SUBSYSTEM.SUB_TYPE
  is '主题类型：0菜单还是1超链接';
comment on column PF_SUBSYSTEM.SUB_NO
  is '编号（排序码）';
comment on column PF_SUBSYSTEM.SUB_URL
  is '超链接地址';
comment on column PF_SUBSYSTEM.SUB_MENU_TYPE
  is '菜单风格，two左两级、three三级';
alter table PF_SUBSYSTEM
  add constraint PK_SUBSYSTEM_ID primary key (SUBSYSTEM_ID)
  using index 
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table PF_SUBSYSTEM
  add constraint UK_SUBSYSTEM_NAME unique (SUBSYSTEM_NAME)
  using index 
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table PF_SUBSYSTEM_MENU_REL
prompt ====================================
prompt
create table PF_SUBSYSTEM_MENU_REL
(
  MENU_ID      VARCHAR2(32) not null,
  SUBSYSTEM_ID VARCHAR2(32) not null
)
;


-- Create table
create table PF_BUSINESS_GROUP
(
  BUSINESS_GROUP_ID   VARCHAR2(32) not null,
  BUSINESS_GROUP_NAME VARCHAR2(200),
  BUSINESS_GROUP_NO   NUMBER(12),
  BUSINESS_IDS        VARCHAR2(2000)
);
-- Add comments to the table
comment on table PF_BUSINESS_GROUP
  is '工作流定义分组';
-- Add comments to the columns
comment on column PF_BUSINESS_GROUP.BUSINESS_GROUP_ID
  is '业务分组';
comment on column PF_BUSINESS_GROUP.BUSINESS_GROUP_NAME
  is '业务分组名称';
comment on column PF_BUSINESS_GROUP.BUSINESS_GROUP_NO
  is '业务分组编号排序用';
comment on column PF_BUSINESS_GROUP.BUSINESS_IDS
  is '业务定义ID';
-- Create/Recreate primary, unique and foreign key constraints
alter table PF_BUSINESS_GROUP
  add constraint PK_WD_GROUP_ID primary key (BUSINESS_GROUP_ID)
  using index
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );


spool off
