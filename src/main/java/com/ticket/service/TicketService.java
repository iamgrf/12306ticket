package com.ticket.service;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class TicketService {

    private TicketCore ticketCore;
    private static Map<String, String> config = new HashMap<>();
    static {
        try {
            Properties prop = new Properties();
            prop.load(new FileInputStream("D:\\work\\workspace\\java\\12306ticket\\src\\main\\resources\\config.properties"));
            Enumeration<?> names = prop.propertyNames();
            while (names.hasMoreElements()){
                String name = (String) names.nextElement();
                if (name.contains("[")){
                    continue;
                }
                config.put(name, prop.getProperty(name));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TicketService(){
        this.ticketCore = new TicketCore(config);
        service();
    }

    private void service(){
        ticketCore.login();
        ticketCore.queryTrain();
    }


}
