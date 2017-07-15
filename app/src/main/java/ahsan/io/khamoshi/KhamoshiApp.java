package ahsan.io.khamoshi;

import android.Manifest;
import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.media.AudioManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.kontakt.proximity.KontaktProvider;
import com.kontakt.proximity.KontaktProximitySDK;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Ahsan on 2017-07-15.
 */

public class KhamoshiApp extends Application {
    private int ringerState;
    private String identifier;
    public String timestring;
    private static final int INITIAL_REQUEST=1337;
    
    private static final String[] INITIAL_PERMS={
            Manifest.permission.ACCESS_FINE_LOCATION
            
    };
    private KontaktProximitySDK sdk;
    
    @Override
    public void onCreate() {
        super.onCreate();
    
//
//        if (!canAccessLocation()) {
////            getApplicationContext().requestPermissions(INITIAL_PERMS, INITIAL_REQUEST);
//        }
        
        String apiKey = "POXKOugCoIJkUXxuhOEGTaORhuzBZIAu";
//        KontaktSDK.initialize("apiKey");
        Context context = getApplicationContext();
        sdk = KontaktProvider.provideProximitySDK(context, apiKey);
        
      
    }
    
    public KontaktProximitySDK getKontaktSDK(){
        return sdk;
    }
   
    
    
}
