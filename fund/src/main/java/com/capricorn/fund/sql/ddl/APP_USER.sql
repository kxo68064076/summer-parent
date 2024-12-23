declare
num number;
begin
select count(1) into num from user_tables where table_name = upper('APP_USER') ;
if num > 0 then
        execute immediate 'drop table APP_USER' ;
end if;
end;

create table  APP_USER
(
  USER_ID           VARCHAR2(10) not null,
  USER_NAME         VARCHAR2(10),
  USER_PASS         VARCHAR2(10),
  TELEPHONE         VARCHAR2(11),
  EMAIL             VARCHAR2(30),
  USER_STATUS       VARCHAR2(1),
  LAST_LOGIN_TIME   VARCHAR2(23),
  DATA_CREATE_USER  VARCHAR2(10),
  DATA_CREATE_TIME  VARCHAR2(23),
  DATA_UPDATE_USER  VARCHAR2(10),
  DATA_UPDATE_TIME  VARCHAR2(23)
)
;
alter table APP_USER
  add constraint APP_USER primary key (USER_ID);

CREATE UNIQUE INDEX APP_USER_UNIQUE_IDX1 ON APP_USER(USER_NAME);

comment on table app_user is '用户表';
comment on column app_user.user_id is '用户ID';
comment on column app_user.user_pass is '用户密码';
comment on column app_user.user_name is '用户名';
comment on column app_user.telephone is '手机号';
comment on column app_user.email is '用户ID';
comment on column app_user.user_status is '用户ID';
comment on column app_user.last_login_time is '用户ID';
comment on column app_user.data_create_user is '用户ID';
comment on column app_user.data_create_time is '用户ID';
comment on column app_user.data_update_user is '用户ID';
comment on column app_user.data_update_time is '用户ID';

insert into APP_USER(User_Id,User_Name,User_Pass,Telephone,email,User_Status,Last_Login_Time,Data_Create_User,Data_Create_Time,Data_Update_user,Data_Update_Time)
values('s00000','admin','12345678','','','1',NULL,'SYSTEM',to_char(sysdate,'YYYYMMDD HH24:MI:SS'),'SYSTEM',to_char(sysdate,'YYYYMMDD HH24:MI:SS'));
insert into APP_USER(User_Id,User_Name,User_Pass,Telephone,email,User_Status,Last_Login_Time,Data_Create_User,Data_Create_Time,Data_Update_user,Data_Update_Time)
values('s99999','visitors','12345678','','','1',NULL,'SYSTEM',to_char(sysdate,'YYYYMMDD HH24:MI:SS'),'SYSTEM',to_char(sysdate,'YYYYMMDD HH24:MI:SS'));

select l.sid,s.serial#, s.username, s.program, l.type, l.ctime, l.lmode, l.request, l.block, l.id1, l.id2 from v$lock l, v$session s where l.sid=s.sid;

alter system kill session '277,45605';