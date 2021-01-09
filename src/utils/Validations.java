package utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Validations {
    public static boolean isEmailValid(String email) {
        String regExpn = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?" + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?" + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;
        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if (!matcher.matches() && email.isEmpty())
            return true;
        else
            return false;
    }

    public static boolean isPasswordValid(String password) {
        String PASSWORD_PATTERN = "^[a-z0-9]{6,12}$";
        CharSequence inputStr = password;
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if (!matcher.matches() || password.isEmpty())
            return true;
        else
            return false;
    }

    public static boolean isValidPhoneNumber(String number) {
        String validNumber = "^0[35789]{1}\\d{8}$";
        Pattern pattern = Pattern.compile(validNumber, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(number);
        String validNumber1 = "^(([+84]{3})|[84]{2})[35789]{1}\\d{8}$";
        Pattern pattern1 = Pattern.compile(validNumber1, Pattern.CASE_INSENSITIVE);
        Matcher matcher1 = pattern1.matcher(number);
        String validNumber3 = "^[35789]{1}\\d{8}$";
        Pattern pattern3 = Pattern.compile(validNumber3, Pattern.CASE_INSENSITIVE);
        Matcher matcher3 = pattern3.matcher(number);
        if (matcher.find() || matcher1.find() || matcher3.find()) {
            return true;
        }
        return false;
    }

    public static boolean isValidName(String s) {
        String validName = "[0-9]*";
        if (s.toString().matches(validName) || s.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean isValidSpecialCharacters(String s) {
        Pattern regex = Pattern.compile("[$&+,:;=\\?@#|/'<>.^*()%!-]");// ~`•√ππ÷×¶∆\}{°¢€£©®™✓
        if (regex.matcher(s).find()) {
            return true;
        }
        return false;
    }

    public static boolean isValidAddress(String s) {
        Pattern regex = Pattern.compile("[$&+:;=\\?@#|/'<>.^*()%!]");// ~`•√ππ÷×¶∆\}{°¢€£©®™✓
        if (regex.matcher(s).find() || s.isEmpty()) {
            return true;
        }
        return false;
    }

    public static String replaceMultiple(String baseString, String... replaceParts) {
        for (String s : replaceParts) {
            baseString = baseString.replaceAll(s, "");
        }
        return baseString;
    }

    public static boolean checkValidation(String phone, String pass) {
        if (!Validations.isValidPhoneNumber(phone)) {
            System.out.println("The phone number is not in the correct format\r\n" + "");
            return false;
        }
        if (Validations.isPasswordValid(pass) || pass.equals(phone)) {
            System.out.println("The password is not in the correct format");
            return false;
        }
        if (netIsAvailable() == false) {
            System.out.println("Internet is not connected");
            return false;
        }

        return true;
    }

    private static boolean netIsAvailable() {
        try {
            final URL url = new URL("http://www.google.com");
            final URLConnection conn = url.openConnection();
            conn.connect();
            conn.getInputStream().close();
            return true;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean isNumeric(String str) {
        if (str.equals("")) {
            return false;
        } else {
            for (char c : str.toCharArray()) {
                if (!Character.isDigit(c))
                    return true;
            }
            return false;
        }
    }

    // -----------------------Get List Video------------------------------
    public static boolean checkContentPost(String content) {
        String regex = "\\w+";
        if (content != null && content.matches(regex)) {
            return true;
        }

        return false;
    }

    public static boolean checkIsLiked(String liked) {
        if (!liked.equals("") && liked.equals("1") || liked.equals("0")) {
            return true;
        }
        return false;
    }

    public static boolean validateJavaDate(String strDate)
    {
        /* Check if date is 'null' */
        if (strDate.equals(""))
        {
            return false;
        }
        /* Date is not 'null' */
        else
        {
            /*
             * Set preferred date format,
             * For example MM-dd-yyyy, MM.dd.yyyy,dd.MM.yyyy etc.*/
            SimpleDateFormat sdfrmt = new SimpleDateFormat("MM/dd/yyyy");
            sdfrmt.setLenient(false);
            /* Create Date object
             * parse the string into date
             */
            try
            {
                Date javaDate = sdfrmt.parse(strDate);
                System.out.println(strDate+" is valid date format");
            }
            /* Date format is invalid */
            catch (java.text.ParseException e)
            {
                System.out.println(strDate+" is Invalid Date format");
                return false;
            }
            /* Return true if date format is valid */
            return true;
        }
    }
}

