package Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    private static final String stringPattern = "^[a-zA-Z]+$";
    private static final String timePattern = "^[0-9:0-9]+$";

    public static boolean validateString(String text) {
        return isMatching(stringPattern, text);
    }

    public static boolean validateTime(String time) {
        if (isMatching(timePattern, time)) {
            String[] timeSplitted = time.split(":");
            if (timeSplitted.length != 2) {
                return false;
            }
            for (int i = 0; i < timeSplitted.length; i++) {
                try{
                    int num = Integer.parseInt(timeSplitted[i]);
                    if(i == 0 && num >= 24 || i == 1 && num >= 60)
                        return false;
                }catch(NumberFormatException e)
                {
                    return false;
                }
            }
            return true;
        }
        return false;
    }


    private static boolean isMatching(String pattern, String data) {
        if (pattern == null || pattern.isEmpty()) {
            return false;
        }

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(data);
        return m.matches();
    }
}