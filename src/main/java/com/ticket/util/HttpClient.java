package com.ticket.util;

import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class HttpClient {

//    private static CloseableHttpClient httpClient = HttpClients.createDefault();
    private static CloseableHttpClient httpClient = null;
    private static CookieStore cookieStore  = null;
    static {
        cookieStore = new BasicCookieStore();
        httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
    }
    public static CloseableHttpClient fetchClient(){
        return httpClient;
    }

    public static CookieStore fetchCookieStore(){
        return cookieStore;
    }

}
