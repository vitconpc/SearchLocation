package vn.com.example.locationbase.common;

import android.util.Base64;



public class Base64AccountUtils {
    public static String getBase64Account(String username, String password) {
        String account = username + ":" + password;
        String base64Account = Base64.encodeToString(account.getBytes(), Base64.NO_WRAP | Base64.URL_SAFE);
        base64Account = Constants.BASIC_PREFIX + base64Account;
        return base64Account;
    }
}
