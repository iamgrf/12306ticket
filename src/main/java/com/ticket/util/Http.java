package com.ticket.util;

import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xy on 2017/10/20.
 */
public class Http {

    public static final String TEXT_PLAIN = "text/plain";
    public static final String APPLICATION_JSON = "application/json;charset=utf-8";


    static okhttp3.OkHttpClient client = new okhttp3.OkHttpClient.Builder()
            .cookieJar(new CookieJar() {
                private List<Cookie> cookies;
                @Override
                public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                    this.cookies =  cookies;
                }
                @Override
                public List<Cookie> loadForRequest(HttpUrl url) {
                    if (cookies != null){
                        return cookies;
                    }
                    return new ArrayList<Cookie>();
                }
            })
            .build();



    public static String get(String url) throws IOException {
        Print.log("GET请求:" + url);
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static byte[] getForFile(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().bytes();
    }

    public static String post(String url, String json) {
        return post(url, json, APPLICATION_JSON);
    }

    public static String post(String url, String json, String media) {
        try {
            RequestBody body = RequestBody.create(MediaType.parse(media), json);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        }catch (Exception e){
            throw new RuntimeException("连接服务器失败.");
        }
    }

    public static void main(String[] args) throws IOException {
        String result = get(Route.LOGIN_CODE);
        System.out.println(result);
    }

}
