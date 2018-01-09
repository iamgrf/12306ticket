package com.ticket.util;

import java.time.LocalDateTime;

public final class Print {

    public static void log(Object msg){
        System.out.println("[" + LocalDateTime.now() + "]" + msg);
    }

}
