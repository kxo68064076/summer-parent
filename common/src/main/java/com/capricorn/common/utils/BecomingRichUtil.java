package com.capricorn.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class BecomingRichUtil {

    private static CloseableHttpClient httpClient;

    private static final Object monitor = new Object();

    private BecomingRichUtil(){}

    public static String request(String code,String IP){
        String SUBMIT_METHOD_GET = "GET";

        String urlStr = IP+code+".js";

        HttpURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;
        URL url = null;

        String result = null;
        try{
            url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(SUBMIT_METHOD_GET);

            connection.setConnectTimeout(15000);

            connection.setReadTimeout(60000);


            connection.connect();

            if (connection.getResponseCode() == 200) {
                is = connection.getInputStream();
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

                StringBuffer sbf = new StringBuffer();
                String temp = null;
                while ((temp = br.readLine()) != null) {
                    sbf.append(temp);
                }
                result = sbf.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }

        return result;
    }

    public static Map<String,Object> regular(String result,String ifBuy) {
        String pattern = "\\{.+?}";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(result);
        Map<String,Object> data = new HashMap();
        if (m.find( )) {
            JSONObject parse = (JSONObject)JSON.parse(m.group(0));
            data.put("FUND_CODE",parse.get("fundcode")); //基金代码
            data.put("FUND_NAME",parse.get("name"));  //基金名称
            data.put("FUND_VALUE",parse.get("dwjz"));  //净值
            data.put("FUND_VALUATION_INCREASE",parse.get("gszzl"));  //估值
            data.put("FUND_VALUATION",parse.get("gsz"));  //估值涨幅
            data.put("UPDATE_TIME",parse.get("gztime"));  //更新时间
        } else {
            log.error("NO MATCH");
        }
        return data;
    }

    public static CloseableHttpClient getHttpClient(){
        if (null == httpClient){
            synchronized (monitor){
                if (null == httpClient){
                    httpClient = HttpClients.createDefault();

                }
            }
        }
        return httpClient;
    }

    public static String req(String code,String IP) {
        //创建URIBuilder
        URIBuilder uriBuilder = null;
        HttpGet httpGet = null;
        String result = null;
        try {
            uriBuilder = new URIBuilder(IP+code+".js");
            httpGet = new HttpGet(uriBuilder.build());
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(1000)
                    .setSocketTimeout(1000)
                    .setConnectTimeout(5000)
                    .build();
            httpGet.setConfig(requestConfig);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        //设置参数
//        uriBuilder.setParameter("keys","Java");

        //创建HttpGet对象，设置url访问地址

        log.info("发起请求的信息："+httpGet);
        CloseableHttpClient httpClient = getHttpClient();
        try(CloseableHttpResponse response = httpClient.execute(httpGet)) {
            //使用HttpClient发起请求，获取response

            //解析响应
            if (response.getStatusLine().getStatusCode() == 200) {
                String content = EntityUtils.toString(response.getEntity(), "utf8");
                result = content;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
