package com.gleamsoft.avamigables.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Developer on 4/09/2017.
 */

public class Methods {

// validating email id
public static boolean isValidEmail(String email) {
    String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                                   + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    
    Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    Matcher matcher = pattern.matcher(email);
    return matcher.matches();
}

// validating password
public static boolean isValidPassword(String pass) {
    if (pass != null && pass.length() >= 3) {
        return true;
    }
    return false;
}
//validating input username
public static boolean isValidUsername(String inout) {
    if (inout != null && inout.length() >= 4) {
        return true;
    }
    return false;
}

public static boolean isValidInput(String inout) {
    if (inout != null && inout.length() >= 2) {
        return true;
    }
    return false;
}
}
