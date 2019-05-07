package exam2.moneyapp.others;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import exam2.moneyapp.R;
public class Const {
    public static String transId = "transId";
    public static String realmName = "app_db_4564";
    private static String date_format = "dd-MM-yyyy";
    public static String spent = "spent";
    public static String added = "added";

    public static long getTimeMillies(String from) {
        SimpleDateFormat f = new SimpleDateFormat(date_format);
        try {
            Date d = f.parse(from);
            long milliseconds = d.getTime();
            return milliseconds;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
