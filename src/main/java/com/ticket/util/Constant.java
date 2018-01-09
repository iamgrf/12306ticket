package com.ticket.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Constant {

    public static Map<String, List<String>> codes;
    static {
        codes = new HashMap<>();
        codes.put("1", Arrays.asList("41,43", "40,43", "41,46"));
        codes.put("2", Arrays.asList("116,41", "118,41", "119,46"));
        codes.put("3", Arrays.asList("188,40", "186,40", "190,39"));
        codes.put("4", Arrays.asList("256,43", "259,40", "256,40"));
        codes.put("5", Arrays.asList("41,116", "41,115", "41,113"));
        codes.put("6", Arrays.asList("114,117", "114,115", "110,117"));
        codes.put("7", Arrays.asList("188,114", "188,110", "189,114"));
        codes.put("8", Arrays.asList("255,112", "255,110", "258,112"));
    }

}
