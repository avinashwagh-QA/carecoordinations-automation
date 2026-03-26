package utilities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestDataUtil {

    public static String generateTestEmail(){
        String time = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("HHmmss"));
        return "automationUser" + time +"@yopmail.com";
    }

    public static String generatePhone(){
        int num = 100000000 + (int)(Math.random() * 900000000);
        return "9" + num;
    }



}
