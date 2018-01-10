package com.ticket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

    public static void main(String[] args) {
        String result = "{\"data\":{\"flag\":\"1\",\"map\":{\"BJQ\":\"深圳东\",\"HHQ\":\"怀化\",\"KAQ\":\"怀化南\",\"NZQ\":\"福田\",\"OSQ\":\"深圳西\",\"SZQ\":\"深圳\"},\"result\":[\"|预订|65000K452605|K4526|BJQ|CUW|BJQ|HHQ|01:55|18:05|16:10|N|jiRmtRsf7m14XtXoCWh%2B83xviGmy4SjhHa3iMObSD8HodXhm|20180208|3|Q9|01|03|0|0||||无|||无|||无|||||101040|114|0\",\"|预订|6i000G616404|G6164|NZQ|KAQ|NZQ|KAQ|07:35|13:00|05:25|N|Zrq1qe4P0WSgW2IrgtlE8UPNi5XlnPgtADB%2B%2FaynxBHHLr%2FZ4Jzd9AKP3Zw%3D|20180208|3|Q9|01|09|1|0||||||无|无||||无|无|||O0O0M0P0|OOMP|0\",\"|预订|6i000G614207|G6142|NZQ|KAQ|NZQ|KAQ|08:11|13:34|05:23|N|AdDvde%2BwDiB5wYfrjHa0ezUCHz7HlHT7xDJT%2BnxS8tfJrE%2BUTam7EFJg2yI%3D|20180208|3|QZ|01|09|1|0|||||||无||||无|无|无||O090O0M0|O9OM|0\",\"|预订|690000K5860J|K586|OSQ|CDW|OSQ|HHQ|08:50|08:15|23:25|N|8sWWfrxDvyAyq1isH1Nmn6s68OdfdLSTNveLzyyPidxqJGK%2FGYrWROMIlFU%3D|20180208|3|Q9|01|20|0|0||||无|||无||无|无|||||10401030|1413|0\",\"G6tsYvpqMpxWAFEaHjFwbD3NsyX0L%2BWSYVpklHjQyZuCNVbUZyf%2FyWQLkrLpumCFLuzkILJ3weu2%0AsjeF4lr7WXHUu4%2Bn7fS3S4%2FceH0tqkxQ98ix4UgAYzB06KAtpMgjhonNZCSK%2FxLtgHPzg6UQ1WUT%0A3WyApO19S0U8OlH%2BxI5yFjTIiGUxYUWHUi%2B6Cd1J4VNFzXAzk2W9glcdhUH5fiZp6KETAeVpW%2Fmt%0A7Ic9iQEZDwbR3g6I%2F%2FUH4k26sqApZXY1N1G7uyM%3D|预订|690000K8360E|K836|OSQ|CUW|OSQ|HHQ|09:54|09:13|23:19|Y|mJa%2BapowAx4ahMHiCGTkIj%2BAOGcIigFZqrAlxnJbXumHNdKo6K763xDzXV0%3D|20180208|3|Q9|01|17|0|0||||无|||2||无|无|||||10401030|1413|0\",\"|预订|69000K90640E|K9064|OSQ|RDQ|OSQ|HHQ|10:30|07:56|21:26|N|We6HylfIeSwgbNkBxyz1zbznWqZtXh2PBAAKqtZPNExDr4j0Cw7iioBooDA%3D|20180208|3|Q9|01|16|0|0||||无|||无||无|无|||||10401030|1413|0\",\"EXUJmqNpV4GWNSUwPeG8LRrb9k7E%2BIp7aJXk9AjKH0MP5ZspLVkvGNtwhKKR62TrSAbYfSc9nHjh%0AszjFvuqVNSxTLjx3mRNaeGoRMA721IMVWDhXURZgUHIdTdzOsGv0hDNYE7E94pWUBhd1IhSvTzmp%0AWP3Rj%2BOuFcmt7szpyhwYJdmG8puHYB9oJDN6PEYQx8%2FBM0WXnz%2BN6938u14Dz0vfzwxEgp%2B7fsJn%0Ajy2maw0i%2Bo0gCKWcUR1%2FNwMSihTA8nGBZLdkKHQ%3D|预订|69000K90600I|K9060|OSQ|HHQ|OSQ|HHQ|11:40|06:08|18:28|Y|kCfBU4JBedepudKbrWs%2FUZN4g%2FhpyJqXpobU2SHitJS%2By6Y1E3LevolziOc%3D|20180208|3|QZ|01|15|0|0||||无|||有||无|无|||||10403010|1431|0\",\"|预订|6i000G614605|G6146|NZQ|KAQ|NZQ|KAQ|14:00|19:48|05:48|N|1k%2BELL20KlQjqmwW%2F6e2P6WVjGwv7Z6iNXYdoTyn2NKLA27xNO9eVRuSnek%3D|20180208|3|Q9|01|12|1|0||||||无|无||||无|无|||O0M0O0P0|OMOP|0\",\"|预订|65000K90520D|K9052|BJQ|HHQ|BJQ|HHQ|14:46|07:00|16:14|N|ncmMx4yiK5V9MVJeQeuhTdjIzJNQ%2FaZOoWvHs9n9xXiGgcdMxRXxWOJKm%2B0%3D|20180208|3|QZ|01|14|0|0||||无|||无||无|无|||||10401030|1413|0\",\"|预订|6i000G616005|G6160|NZQ|KAQ|NZQ|KAQ|16:25|22:06|05:41|N|yKYBxGrow2XzIOMazYufiMAD2hX7mAVT9WhjijnI1e6J8xxJt%2FrQouWz%2FhzbgycFrgIVHL8r6lI%3D|20180208|3|Q7|01|11|1|0||||||无|无||||无|无|无||O090M0O0P0|O9MOP|0\",\"|预订|65000K900601|K9006|SZQ|DIQ|SZQ|HHQ|16:42|09:27|16:45|N|bx3ObGxQZc3bCJiTGOHWXpjyDsHncxUlVcMAv9vSCq67tEt7|20180208|3|Q9|01|14|0|0|||||||无||无|无|||||103010|131|0\",\"|预订|69000K465620|K4656|OSQ|NJW|OSQ|HHQ|20:50|14:10|17:20|N|%2BOCwMCXZcD32bixLEjVRnG%2Fy5bt2bYx96bQGuHbljkCQlpFX|20180208|0|Q9|01|06|0|0|||||||无||无|无|||||101030|113|0\",\"pOxvRSFis0DaHFXurjoS%2BPeQYpViI%2BiqT76lp2173SN4r77%2FJEhbXLgcz7ew89%2B3TlcOeek00qX6%0Aigwo0HJOrpSGxPMgoI9vD59YZOWPF6lSVKh3f03NrjOF6M42uTZO3WmLZEk4%2FsGgd4E4QLnpr5zH%0AZQkc7SDrxBvPv7Mmwdi1TbXfpF5kacaRQG61xiZYWir%2FoNWmhXxM2PvO6Ea%2FPVP3kEW52hjqTtKn%0AnvHwHW0EZ22HGeG8bx1xE7Q%3D|预订|65000K407606|K4076|SZQ|CDW|SZQ|HHQ|21:55|16:03|18:08|Y|ZN5jpgZuTI%2FljY%2BuNgVdsa%2BO0xYYTpoMnzmLl3wHFwDXWjm8|20180208|0|Q9|01|05|0|0|||||||有||无|无|||||101030|113|0\",\"Mew1gPu%2BsXtaA4WrQXyq0c%2BcYk5QqCI3639w9C%2BxeTBVm5P0O4aKDxbkOc949N55wdRX%2BqRJOk0t%0AK7FW5fPqtaSGFLwF9U89Lnn%2B1N9yN31QYWPKIYiK6kfG4Gb%2BZag2ITj8byqbF2QB%2Fkg7i8xEMH3q%0ATfsgYzUdbDFklZAZAu1%2FTZ%2FC7V6BSXcEEH%2F4IWedbAEcBLE4TEeKE8h9uCYvIwX8VaGBo1ySCbqS%0AYhzKhfH9NgEBr96oxpWVR2bU9u7o|预订|69000K657804|K6578|OSQ|HHQ|OSQ|HHQ|23:57|15:55|15:58|Y|zxiYI2u6YZnz9QQ1XkJMstrw5Bfnzvp3617mZWqxNB%2Fj5r84|20180208|3|Q7|01|05|0|0|||||无||有|||有|||||101020|112|0\"]},\"httpstatus\":200,\"messages\":\"\",\"status\":true}";
        JSONObject msgJson = JSON.parseObject(result);
        result = msgJson.getJSONObject("data").getString("result");
        System.out.println(result);
        String rgt = "(\"(\".*\")\")";
        Pattern pattern = Pattern.compile(rgt);
        Matcher m = pattern.matcher(result);
        while (m.find()) {
            System.out.println(m.group(1));
        }

    }

}
