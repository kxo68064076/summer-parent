declare
num number;
begin
select count(1) into num from user_tables where table_name = upper('SIF_SYS_AUTH') ;
if num > 0 then
        execute immediate 'drop table SIF_SYS_AUTH' ;
end if;
end;

create table  SIF_SYS_AUTH
(
  RANKCD            VARCHAR2(2),
  AUTHCD            VARCHAR2(32) NOT NULL ,
  AUTHUR            VARCHAR2(100),
  SORTNO            VARCHAR2(3),
  PARTAH            VARCHAR2(32),
  AUTHNA_CH         VARCHAR2(100)
)
;
alter table SIF_SYS_AUTH
  add constraint SIF_SYS_AUTH primary key (AUTHCD);

comment on table SIF_SYS_AUTH is '前端路由表';
comment on column SIF_SYS_AUTH.RANKCD is '路由级别';
comment on column SIF_SYS_AUTH.AUTHCD is '路由名称';
comment on column SIF_SYS_AUTH.AUTHUR is '路由路径';
comment on column SIF_SYS_AUTH.SORTNO is '排序';
comment on column SIF_SYS_AUTH.PARTAH is '父路径';
comment on column SIF_SYS_AUTH.AUTHNA_CH is '菜单名称';


INSERT INTO C##CAPRICORN.SIF_SYS_AUTH (RANKCD, AUTHCD, AUTHUR, SORTNO, PARTAH, AUTHNA_CH) VALUES ('1', 'parameterManager', null, '02', null, '参数管理');
INSERT INTO C##CAPRICORN.SIF_SYS_AUTH (RANKCD, AUTHCD, AUTHUR, SORTNO, PARTAH, AUTHNA_CH) VALUES ('1', 'reportManager', null, '03', null, '报表管理');
INSERT INTO C##CAPRICORN.SIF_SYS_AUTH (RANKCD, AUTHCD, AUTHUR, SORTNO, PARTAH, AUTHNA_CH) VALUES ('1', 'systemManager', null, '01', null, '系统管理');
INSERT INTO C##CAPRICORN.SIF_SYS_AUTH (RANKCD, AUTHCD, AUTHUR, SORTNO, PARTAH, AUTHNA_CH) VALUES ('2', 'fundManager', null, '01', 'systemManager', '基金管理');
INSERT INTO C##CAPRICORN.SIF_SYS_AUTH (RANKCD, AUTHCD, AUTHUR, SORTNO, PARTAH, AUTHNA_CH) VALUES ('2', 'userManager', null, '03', 'systemManager', '用户管理');
INSERT INTO C##CAPRICORN.SIF_SYS_AUTH (RANKCD, AUTHCD, AUTHUR, SORTNO, PARTAH, AUTHNA_CH) VALUES ('3', 'holdings', '/holdings', '01', 'fundManager', '持仓');
INSERT INTO C##CAPRICORN.SIF_SYS_AUTH (RANKCD, AUTHCD, AUTHUR, SORTNO, PARTAH, AUTHNA_CH) VALUES ('3', 'collectStocks', '/collectStocks', '02', 'fundManager', '自选');
INSERT INTO C##CAPRICORN.SIF_SYS_AUTH (RANKCD, AUTHCD, AUTHUR, SORTNO, PARTAH, AUTHNA_CH) VALUES ('3', 'videoParsingParameter', '/videoParsingParameter', '01', 'videoParsingParameterManager', '视频解析参数');
INSERT INTO C##CAPRICORN.SIF_SYS_AUTH (RANKCD, AUTHCD, AUTHUR, SORTNO, PARTAH, AUTHNA_CH) VALUES ('2', 'videoParsingParameterManager', null, '01', 'parameterManager', '视频解析参数管理');
INSERT INTO C##CAPRICORN.SIF_SYS_AUTH (RANKCD, AUTHCD, AUTHUR, SORTNO, PARTAH, AUTHNA_CH) VALUES ('2', 'videoParsingManager', null, '02', 'systemManager', '视频解析管理');