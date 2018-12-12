package vn.com.example.locationbase.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import vn.com.example.locationbase.data.model.response.AccessToken;
import vn.com.example.locationbase.data.model.response.AccessTokenGroup;

public class Utils {
    public static String getValueFromPreferences(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }
    public static void saveAccessTokenGroup(Context context, AccessTokenGroup accessTokenGroup) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(accessTokenGroup == null) {
            editor.putString(Constants.PRE_ACCESS_TOKEN_LOGIN, null);
            editor.putString(Constants.PRE_REFRESH_TOKEN_LOGIN, null);
            editor.putString(Constants.PRE_STATUS_LOGIN, Constants.VALUE_LOGIN_FAIL);
        } else {
            editor.putString(Constants.PRE_ACCESS_TOKEN_LOGIN, accessTokenGroup.getAccessToken());
            editor.putString(Constants.PRE_REFRESH_TOKEN_LOGIN, accessTokenGroup.getRefreshToken());
            editor.putString(Constants.PRE_STATUS_LOGIN, Constants.VALUE_LOGIN_TRUE);
        }
        editor.apply();
    }
    public static void saveAccessToken(Context context, AccessToken accessToken) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(accessToken == null) {
            editor.putString(Constants.PRE_USER_ID, null);
            editor.putString(Constants.PRE_ACCESS_TOKEN_LOGIN, null);
            editor.putString(Constants.PRE_REFRESH_TOKEN_LOGIN, null);
            editor.putString(Constants.PRE_STATUS_LOGIN, Constants.VALUE_LOGIN_FAIL);
            editor.putBoolean(Constants.PRE_UPDATE_SHARE_PREF, false);
        } else {
            editor.putString(Constants.PRE_USER_ID, accessToken.getId());
            editor.putString(Constants.PRE_ACCESS_TOKEN_LOGIN, accessToken.getAccessToken());
            editor.putString(Constants.PRE_REFRESH_TOKEN_LOGIN, accessToken.getRefreshToken());
            editor.putString(Constants.PRE_STATUS_LOGIN, Constants.VALUE_LOGIN_TRUE);
            editor.putBoolean(Constants.PRE_UPDATE_SHARE_PREF, true);
        }
        editor.apply();
    }

    public static String getBearerAccessToken(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String accessToken = sharedPreferences.getString(Constants.PRE_ACCESS_TOKEN_LOGIN, null);
        return Constants.BEARER_TOKEN_PREFIX + accessToken;
    }

    public static String getUserID(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(Constants.PRE_USER_ID, null);
    }

    public static boolean isUserLoggedIn(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return Constants.VALUE_LOGIN_TRUE.equals(sharedPreferences.getString(Constants.PRE_STATUS_LOGIN, null)) &&
                sharedPreferences.getBoolean(Constants.PRE_UPDATE_SHARE_PREF, false);
    }
}
