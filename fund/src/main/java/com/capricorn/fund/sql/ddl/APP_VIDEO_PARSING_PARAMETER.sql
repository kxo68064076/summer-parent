declare
num number;
begin
select count(1) into num from user_tables where table_name = upper('APP_VIDEO_PARSING_PARAMETER') ;
if num > 0 then
        execute immediate 'drop table APP_VIDEO_PARSING_PARAMETER' ;
end if;
end;

create table  APP_VIDEO_PARSING_PARAMETER
(
    PARSING_URL        VARCHAR2(100)
)
;
alter table APP_VIDEO_PARSING_PARAMETER
    add constraint APP_VIDEO_PARSING_PARAMETER primary key (PARSING_URL);

comment on table APP_VIDEO_PARSING_PARAMETER is '视频解析参数';
comment on column APP_VIDEO_PARSING_PARAMETER.PARSING_URL is '解析路径';
