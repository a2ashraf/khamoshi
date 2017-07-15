package ahsan.io.khamoshi.Utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Ahsan on 15-12-20.
 */
public class KhamoshiPreferences {

    public static final String DEFAULT_MOSQUE = "default_mosque";
    public static final String SAVED_MOSQUE = "saved_mosque";


    public static void putStringPref(String key, String value, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getStringPref(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, "");
    }

    public static void putIntPref(String key, int value, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static int getIntPref(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        return preferences.getInt(key, -1);
    }
    
    
     public static  String getHumanReadableTime(Long longTimeStamp) {
        String dateString = "";
        
        Calendar calendar = Calendar.getInstance();
        TimeZone tz = TimeZone.getTimeZone(String.valueOf(TimeZone.getTimeZone("Toronto")));
        calendar.setTimeInMillis(longTimeStamp);
        calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        Date currenTimeZone = (Date) calendar.getTime();
        return sdf.format(currenTimeZone);
    }
}
