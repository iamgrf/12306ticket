package com.ticket.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ticket.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

public abstract class TicketCore {

    public TicketConfig ticketConfig = new TicketConfig();
    public String result = null;

    public TicketCore(String configPath) throws Exception {
        loadConfig(configPath);
    }

    abstract public void loginCall();

    /**
     * 加载配置信息
     * @param configPath
     * @throws IOException
     */
    private void loadConfig(String configPath) throws Exception {
        Properties prop = new Properties();
        prop.load(new FileReader(configPath));
        Enumeration<?> names = prop.propertyNames();
        while (names.hasMoreElements()){
            String key = (String) names.nextElement();
            switch (key){
                case "username":
                    ticketConfig.setUsername(prop.getProperty(key));
                    break;
                case "password":
                    ticketConfig.setPassword(prop.getProperty(key));
                    break;
                case "train_date":
                    ticketConfig.setTrainDate(prop.getProperty(key));
                    break;
                case "from_station":
                    ticketConfig.setFromStation(prop.getProperty(key));
                    break;
                case "to_station":
                    ticketConfig.setToStation(prop.getProperty(key));
                    break;
                case "fight_train_num":
                    ticketConfig.setFightTrainNum(prop.getProperty(key));
                    break;
                case "seat_type":
                    ticketConfig.setSeatType(prop.getProperty(key));
                    break;
                case "query_from_station_name":
                    ticketConfig.setQueryFromStationName(prop.getProperty(key));
                    if (StationNameUtil.get(ticketConfig.getQueryFromStationName()) == null){
                        Print.log("未找到出发车站");
                        System.exit(0);
                    }
                    ticketConfig.setFromStation(StationNameUtil.get(ticketConfig.getQueryFromStationName()).getCode());
                    break;
                case "query_to_station_name":
                    ticketConfig.setQueryToStationName(prop.getProperty(key));
                    if (StationNameUtil.get(ticketConfig.getQueryToStationName()) == null){
                        Print.log("未找到出发车站");
                        System.exit(0);
                    }
                    ticketConfig.setToStation(StationNameUtil.get(ticketConfig.getQueryToStationName()).getCode());
                    break;
                case "passenger_ticket_str":
                    ticketConfig.setPassengerTicketStr(prop.getProperty(key));
                    break;
                case "old_passenger_str":
                    ticketConfig.setOldPassengerStr(prop.getProperty(key));
                    break;
            }
        }
    }

    /**
     * 验证验证码是否正确
     */
    public boolean captchaCheck() {
        String login_code = Util.fetchCode(Constant.codePoints);
        ticketConfig.setLoginCode(login_code);
        result = Http.post(Route.HOST + "/passport/captcha/captcha-check", "answer=" + login_code + "&login_site=E&rand=sjrand");
        JSONObject msgJson = JSON.parseObject(result);
        Print.log(msgJson.getString("result_message"));
        if (4 == msgJson.getInteger("result_code")){
            return true;
        }
        return false;
    }

    /**
     * 登录12306
     */
    public boolean login() {
        int index = 1;
        while (index > 0){
            result = Http.post(Route.HOST + "/passport/web/login", String.format("username=%s&password=%s&appid=otn", ticketConfig.getUsername(), ticketConfig.getPassword()));
            if (result != null && result.length() > 0){
                break;
            }
            index--;
        }
        JSONObject msgJson = JSON.parseObject(result);
        Print.log(msgJson.getString("result_message"));
        if (0 != msgJson.getInteger("result_code")){
            return false;
        }
        ticketConfig.setUamtk(msgJson.getString("uamtk"));
        result = Http.post(Route.HOST + "/otn/login/userLogin", "_json_att=");
        Print.log(result);
        result = Http.get(Route.HOST + "/otn/passport?redirect=/otn/login/userLogin");
        result = Http.post(Route.HOST + "/passport/web/auth/uamtk", "appid=otn");
        msgJson = JSON.parseObject(result);
        ticketConfig.setNewapptk(msgJson.getString("newapptk"));
        result = Http.post(Route.HOST + "/otn/uamauthclient", "tk="+ticketConfig.getNewapptk());
        Print.log(result);
        result = Http.get(Route.HOST + "/otn/login/userLogin");
        return true;
    }

    public void initMy12306(){
        result = Http.get(Route.HOST + "/otn/index/initMy12306");
        result = Http.get(Route.HOST + "/otn/leftTicket/init");
        Document c = Jsoup.parse(result);
        Elements js = c.getElementsByAttributeValueMatching("src", "/otn/dynamicJs*");
        Http.get(Route.HOST + js.get(0).attr("src"));
    }



    /**
     * 查询车次信息
     * @return 返回车次信息
     */
    public String queryTrain(){
        String result = Http.get(Route.HOST + String.format("/otn/leftTicket/query?leftTicketDTO.train_date=%s&" +
                        "leftTicketDTO.from_station=%s&leftTicketDTO.to_station=%s&purpose_codes=ADULT", ticketConfig.getTrainDate(),
                ticketConfig.getFromStation(), ticketConfig.getToStation()));
        return result;
    }

    public void submit() {
        String result = Http.post(Route.HOST + "/otn/login/checkUser", "_json_att=");
        Print.log(result);

        String parameter = "secretStr=%s&train_date=%s&back_train_date=%s&tour_flag=dc&purpose_codes=ADULT&query_from_station_name=%s&query_to_station_name=%s&undefined=";
        result = Http.get(Route.HOST + "/otn/leftTicket/submitOrderRequest?" + String.format(parameter, ticketConfig.getSecretStr(),
                ticketConfig.getTrainDate(), ticketConfig.getTrainDate(), ticketConfig.getQueryFromStationName(), ticketConfig.getQueryToStationName()));
        Print.log(result);
        JSONObject msgJson = JSON.parseObject(result);
        if (!msgJson.getBoolean("status")){
            //您还有未处理的订单
            System.exit(0);
        }

        result = Http.post(Route.HOST + "/otn/confirmPassenger/initDc", "_json_att=");

        String[] tokens = Util.regex(result, "var globalRepeatSubmitToken = '(.*)';");
        ticketConfig.setRepeatSubmitToken(tokens[0]);

        String[] keyCheckIsChange = Util.regex(result, "'key_check_isChange':'(.*)','leftDetails'");
        ticketConfig.setKeyCheckIsChange(keyCheckIsChange[0]);

        Document c = Jsoup.parse(result);
        Elements j = c.getElementsByAttributeValueMatching("src", "/otn/dynamicJs*");
        Http.get(Route.HOST + j.get(0).attr("src"));

        parameter = "cancel_flag=2&bed_level_order_num=000000000000000000000000000000&passengerTicketStr=%s&oldPassengerStr=%s&tour_flag=dc&randCode=&whatsSelect=1&_json_att=&REPEAT_SUBMIT_TOKEN=%s";
        result = Http.post(Route.HOST + "/otn/confirmPassenger/checkOrderInfo", String.format(parameter, ticketConfig.getPassengerTicketStr(), ticketConfig.getOldPassengerStr(), ticketConfig.getRepeatSubmitToken()));
        Print.log(result);

        parameter = "train_date=%s&train_no=%s&stationTrainCode=%s&seatType=%s&fromStationTelecode=%s&toStationTelecode=%s&leftTicket=%s&purpose_codes=00&train_location=%s&_json_att=&REPEAT_SUBMIT_TOKEN=%s";
        result = Http.post(Route.HOST + "/otn/confirmPassenger/getQueueCount", String.format(parameter, Util.gmtDate(), ticketConfig.getTrainNo(),
                ticketConfig.getStationTrainCode(), ticketConfig.getSeatType(), ticketConfig.getFromStation(), ticketConfig.getToStation(), ticketConfig.getLeftTicket(), ticketConfig.getTrainLocation(), ticketConfig.getRepeatSubmitToken()));
        Print.log(result);

        parameter = "passengerTicketStr=%s&oldPassengerStr=%s&randCode=&purpose_codes=00&key_check_isChange=%s&leftTicketStr=%s&train_location=%s&choose_seats=&seatDetailType=000&whatsSelect=1&roomType=00&dwAll=N&_json_att=&REPEAT_SUBMIT_TOKEN=%s";
        result = Http.post(Route.HOST + "/otn/confirmPassenger/confirmSingleForQueue", String.format(parameter, ticketConfig.getPassengerTicketStr(), ticketConfig.getOldPassengerStr(), ticketConfig.getKeyCheckIsChange(), ticketConfig.getLeftTicket(), ticketConfig.getTrainLocation(), ticketConfig.getRepeatSubmitToken()));
        Print.log(result);

        int count = 3;
        while (count > 0){
            result = Http.get(Route.HOST + String.format("/otn/confirmPassenger/queryOrderWaitTime?random=%d&tourFlag=dc&_json_att=&REPEAT_SUBMIT_TOKEN=%s", System.currentTimeMillis(), ticketConfig.getRepeatSubmitToken()));
            Print.log(result);
            msgJson = JSON.parseObject(result);
            if (msgJson.getJSONObject("data").get("orderId") != null){
                ticketConfig.setOrderId(msgJson.getJSONObject("data").getString("orderId"));
                break;
            }
            count--;
        }
        if (ticketConfig.getOrderId() == null){
            Print.log("获取订单信息失败，请查看未完成订单，继续支付！");
            System.exit(0);
        }

        parameter = "orderSequence_no=%s&_json_att=&REPEAT_SUBMIT_TOKEN=%s";
        result = Http.post(Route.HOST + "/otn/confirmPassenger/resultOrderForDcQueue", String.format(parameter, ticketConfig.getOrderId(), ticketConfig.getRepeatSubmitToken()));
        Print.log(result);

        parameter = "random=%d&_json_att=&REPEAT_SUBMIT_TOKEN=%s";
        Http.post(Route.HOST + "/otn//payOrder/init", String.format(parameter, System.currentTimeMillis(), ticketConfig.getRepeatSubmitToken()));
        Print.log("购票成功, 退出程序");
        System.exit(0);
    }
}
