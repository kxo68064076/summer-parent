declare
num number;
begin
select count(1) into num from user_tables where table_name = upper('FUND_CODE_TB') ;
if num > 0 then
        execute immediate 'drop table FUND_CODE_TB' ;
end if;
end;

create table  FUND_CODE_TB
(
  fund_code VARCHAR2(10) not null,
  fund_name VARCHAR2(100) ,
  how_many  BINARY_FLOAT,
  if_buy    VARCHAR2(1),
  create_time   VARCHAR2(20),
  create_user   VARCHAR2(20),
  update_time   VARCHAR2(20),
  update_user   VARCHAR2(20)
)
;
alter table FUND_CODE_TB
  add constraint FUND_CODE_TB_ID primary key (FUND_CODE,create_user);

INSERT INTO C##CAPRICORN.FUND_CODE_TB (FUND_CODE, FUND_NAME, HOW_MANY, IF_BUY, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER) VALUES ('003835', '鹏华沪深港新兴成长混合A', 100, '1', to_char(sysdate,'YYYYMMDD_HH24MISS'), 'visitors', to_char(sysdate,'YYYYMMDD_HH24MISS'), 'visitors');
INSERT INTO C##CAPRICORN.FUND_CODE_TB (FUND_CODE, FUND_NAME, HOW_MANY, IF_BUY, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER) VALUES ('005669', '前海开源公用事业股票', 100, '1', to_char(sysdate,'YYYYMMDD_HH24MISS'), 'visitors', to_char(sysdate,'YYYYMMDD_HH24MISS'), 'visitors');
INSERT INTO C##CAPRICORN.FUND_CODE_TB (FUND_CODE, FUND_NAME, HOW_MANY, IF_BUY, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER) VALUES ('000689', '前海开源新经济混合A', 100, '1', to_char(sysdate,'YYYYMMDD_HH24MISS'), 'visitors', to_char(sysdate,'YYYYMMDD_HH24MISS'), 'visitors');
INSERT INTO C##CAPRICORN.FUND_CODE_TB (FUND_CODE, FUND_NAME, HOW_MANY, IF_BUY, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER) VALUES ('002943', '广发多因子混合', 100, '1', to_char(sysdate,'YYYYMMDD_HH24MISS'), 'visitors', to_char(sysdate,'YYYYMMDD_HH24MISS'), 'visitors');
