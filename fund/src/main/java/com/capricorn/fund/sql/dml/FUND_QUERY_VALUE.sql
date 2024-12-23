CREATE OR REPLACE PROCEDURE FUND_QUERY_VALUE(
    USER_NAME IN varchar ,
    TOTAL_VALUE out number,
    VALUATION_VALUE out number,
    out_flag out varchar

)
    is
    cursor buy_cur is select fund_code,how_many,if_buy,CREATE_USER from FUND_CODE_TB where if_buy = '1';
    cur buy_cur%rowtype;
    val number;
    v_val number;
BEGIN
    TOTAL_VALUE:=0;
    VALUATION_VALUE:=0;
    out_flag:='0';

    --DBMS_OUTPUT.PUT_LINE('view：'||USER_NAME);
for cur in buy_cur loop
            --DBMS_OUTPUT.PUT_LINE('fund holders：'||cur.CREATE_USER);
            IF cur.CREATE_USER!=USER_NAME or (USER_NAME is not null and USER_NAME != '') THEN
                CONTINUE;
END IF;
            --dbms_output.put_line('打印持有份额：'||cur.how_many);
            --持有金额
select to_number(fund_value)*cur.how_many into val from FUND_DETAIL_TB d
where d.fund_code = cur.fund_code;
TOTAL_VALUE := TOTAL_VALUE+val;
            --估算涨跌
select (to_number(fund_valuation)-to_number(fund_value))*cur.how_many into v_val from FUND_DETAIL_TB d
where d.fund_code = cur.fund_code;
VALUATION_VALUE := VALUATION_VALUE+v_val;
end loop;

exception
    when others then
        out_flag :='1';

END;
