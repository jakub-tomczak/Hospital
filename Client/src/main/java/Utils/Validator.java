package Utils;

import javafx.scene.control.TextField;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    private static final String stringPattern = "^[a-zńćźżłśąóęA-ZŃĆŹŻŁŚĄÓĘ ]+$";
    private static final String timePattern = "^[0-9:0-9]+$";

    public static boolean validateString(String text) {
        return isMatching(stringPattern, text) ;
    }


    public static boolean validateNumber(String text)
    {
        try
        {
            Double.parseDouble(text);
            return true;
        }catch(NumberFormatException e)
        {
            return false;
        }
    }

    public static boolean validateNumber(String text, double minVal, double maxVal, String message)
    {
        try
        {
            double value = Double.parseDouble(text);
            if(value < minVal || value > maxVal)
            {
                ExceptionHandler.displayException(message);
                return false;
            }
            return true;
        }catch(NumberFormatException e)
        {
            return false;
        }
    }
    public static boolean validateInteger(String text, int minVal, int maxVal, String message)
    {
        try
        {
            double value = Integer.parseInt(text);
            if(value < minVal || value > maxVal)
            {
                ExceptionHandler.displayException(message);
                return false;
            }
            return true;
        }catch(NumberFormatException e)
        {
            ExceptionHandler.displayException("Podany ciąg znaków nie jest liczbą całkowitoliczbową!");
            return false;
        }
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

    private static String replaceCommas(String text)
    {
        return text.replace(',', '.');
    }

    public static boolean validateNumberField(TextField field)
    {
        field.setText(replaceCommas(field.getText()));

        if(!validateNumber(field.getText()))
        {
            field.setStyle("-fx-background-color: #cd3402");
            return false;
        }
        field.setStyle("-fx-background-color: #ffffff");
        return true;
    }
    public static boolean validateNumberField(TextField field, double minVal, double maxVal, String message)
    {
        field.setText(replaceCommas(field.getText()));

        if(!validateNumber(field.getText(), minVal, maxVal, message))
        {
            field.setStyle("-fx-background-color: #cd3402");
            return false;
        }
        field.setStyle("-fx-background-color: #ffffff");
        return true;
    }

    public static boolean validateIntegerField(TextField field, int minVal, int maxVal, String message)
    {
        field.setText(replaceCommas(field.getText()));

        if(!validateInteger(field.getText(), minVal, maxVal, message))
        {
            field.setStyle("-fx-background-color: #cd3402");
            return false;
        }
        field.setStyle("-fx-background-color: #ffffff");
        return true;
    }

    public static boolean validateStringField(TextField field)
    {
        if(!validateString(field.getText()))
        {
            field.setStyle("-fx-background-color: #cd3402");
            return false;
        }
        field.setStyle("-fx-background-color: #ffffff");
        return true;
    }

}
