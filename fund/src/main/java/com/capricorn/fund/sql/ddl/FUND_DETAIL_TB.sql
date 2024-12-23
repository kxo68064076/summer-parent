declare
num number;
begin
select count(1) into num from user_tables where table_name = upper('FUND_DETAIL_TB') ;
if num > 0 then
        execute immediate 'drop table FUND_DETAIL_TB' ;
end if;
end;

create table  FUND_DETAIL_TB
(
  fund_code                 VARCHAR2(10) not null,
  fund_name                 VARCHAR2(40) not null,
  fund_value                VARCHAR2(10),
  FUND_valuation_Increase   VARCHAR2(10),
  fund_valuation            VARCHAR2(10),
  update_time               VARCHAR2(20)
)
;
comment on column FUND_DETAIL_TB.fund_code
  is '基金代码';
comment on column FUND_DETAIL_TB.fund_name
  is '基金名称';
comment on column FUND_DETAIL_TB.fund_value
  is '上个开盘日净值';
comment on column FUND_DETAIL_TB.fund_valuation
  is '估算涨幅';
comment on column FUND_DETAIL_TB.update_time
  is '更新日期';
comment on column FUND_DETAIL_TB.FUND_valuation_Increase
  is '估算涨幅';
alter table FUND_DETAIL_TB
    add constraint FUND_DETAIL_TB_ID primary key (FUND_CODE, FUND_NAME);

