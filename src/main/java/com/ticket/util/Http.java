package com.ticket.util;

import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xy on 2017/10/20.
 */
public class Http {
//    Content-Type:
    private static RequestConfig requestConfig = RequestConfig.custom()
            .setConnectTimeout(50000).setConnectionRequestTimeout(10000)
            .setSocketTimeout(50000).build();

    private static String inputStreamTOString(InputStream in) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int count = -1;
        while((count = in.read(data,0,1024)) != -1){
            outStream.write(data, 0, count);
        }
        return new String(outStream.toByteArray(), "utf-8");
    }

    private static byte[] inputStreamTOByteArray(InputStream in) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int count = -1;
        while((count = in.read(data,0,1024)) != -1){
            outStream.write(data, 0, count);
        }
        return outStream.toByteArray();
    }

    public static String get(String url) {
        CloseableHttpResponse response = null;
        try {
            Print.log("GET请求:" + url);
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=UTF-8");
            httpGet.addHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
            httpGet.setConfig(requestConfig);
            response = HttpClient.fetchClient().execute(httpGet);
            InputStream x = response.getEntity().getContent();
            return inputStreamTOString(x);
        }catch (Exception e){
        }finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static byte[] getForFile(String url) {
        CloseableHttpResponse response = null;
        try {
            Print.log("GET请求:" + url);
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=UTF-8");
            httpGet.addHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
            httpGet.setConfig(requestConfig);
            response = HttpClient.fetchClient().execute(httpGet);
            return inputStreamTOByteArray(response.getEntity().getContent());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public static String post(String url, String parameter) {
        CloseableHttpResponse response = null;
        try {
            Print.log("POST请求:" + url + "?" + parameter);
            List<NameValuePair> formParams = new ArrayList<NameValuePair>();
            if (parameter != null && parameter.length() > 0){
                String[] parameters = parameter.split("&");
                for (int i = 0; i < parameters.length; i++) {
                    String[] kv = parameters[i].split("=");
                    String k = kv[0];
                    String v = kv.length > 1 ? kv[1] : "";
                    formParams.add(new BasicNameValuePair(k, v));
                }
            }
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=UTF-8");
            httpPost.addHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
            httpPost.setConfig(requestConfig);
            httpPost.setEntity(new UrlEncodedFormEntity(formParams, "UTF-8"));
            response = HttpClient.fetchClient().execute(httpPost);
            InputStream x = response.getEntity().getContent();
            return inputStreamTOString(x);
        }catch (Exception e){
        }finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

}
