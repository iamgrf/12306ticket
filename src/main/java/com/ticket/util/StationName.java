package com.ticket.util;

public class StationName {

    private String simple;
    private String stationName;
    private String code;
    private String full;

    public StationName(String simple, String stationName, String code, String full) {
        this.simple = simple;
        this.stationName = stationName;
        this.code = code;
        this.full = full;
    }

    public String getSimple() {
        return simple;
    }

    public void setSimple(String simple) {
        this.simple = simple;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }

    @Override
    public String toString() {
        return "StationName{" +
                "simple='" + simple + '\'' +
                ", stationName='" + stationName + '\'' +
                ", code='" + code + '\'' +
                ", full='" + full + '\'' +
                '}';
    }
}
