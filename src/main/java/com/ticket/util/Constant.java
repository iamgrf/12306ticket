package com.ticket.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Constant {

    public static Map<String, List<String>> codes;
    static {
        codes = new HashMap<>();
        codes.put("1", Arrays.asList("39,46", "35,40", "32,35"));
        codes.put("2", Arrays.asList("112,40", "110,36", "109,42"));
        codes.put("3", Arrays.asList("188,39", "183,40", "186,40"));
        codes.put("4", Arrays.asList("253,38", "251,35", "252,36"));
        codes.put("5", Arrays.asList("32,112", "33,118", "29,110"));
        codes.put("6", Arrays.asList("182,109", "188,113", "187,116"));
        codes.put("7", Arrays.asList("253,112", "252,115", "257,118"));
        codes.put("8", Arrays.asList("113,112", "112,117", "118,112"));
    }

}
