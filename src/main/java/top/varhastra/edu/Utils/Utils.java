package top.varhastra.edu.Utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Utils {
    public static String formatTime(Timestamp timestamp) {
        return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(timestamp);
    }
}
