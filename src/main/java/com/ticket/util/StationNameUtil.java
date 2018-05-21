package com.ticket.util;

import org.jsoup.Jsoup;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StationNameUtil {

    private static Map<String, StationName> stationMap = new HashMap<>();

    public static StationName get(String stationName){
        return stationMap.get(stationName);
    }

    public static void loadStation(String stationNamePath) throws IOException {
        String station = Jsoup.parse(new File(stationNamePath), "utf-8").text();
        String[] stationNameArray = Util.regex(station, "var station_names ='(.*)'")[0].split("@");
        List<String> stationNames = Arrays.stream(stationNameArray).filter(t -> (t == null || t.trim().length() == 0) ? false : true).collect(Collectors.toList());

        for (int i = 0; i < stationNames.size(); i++) {
            String[] stationInfo = stationNames.get(i).split("\\|");
            stationMap.put(stationInfo[1], new StationName(stationInfo[0], stationInfo[1], stationInfo[2], stationInfo[3]));
        }
    }

}
