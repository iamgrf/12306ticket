package com.ticket.util;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class HttpClient {

    private static CloseableHttpClient httpclient = HttpClients.createDefault();

    public static CloseableHttpClient fetchClient(){
        return httpclient;
    }

}
