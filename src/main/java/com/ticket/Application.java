package com.ticket;

import com.ticket.service.TicketService;
import com.ticket.util.StationNameUtil;

public class Application {

    public static void main(String[] args) throws Exception {
        String stationNamePath = "D:\\work\\workspace\\java\\12306ticket\\src\\main\\resources\\station_name.js";
        StationNameUtil.loadStation(stationNamePath);

        String configPath = "D:\\work\\workspace\\java\\12306ticket\\src\\main\\resources\\config.properties";
        TicketService ticketService = new TicketService(configPath);
        ticketService.run();
    }

}
