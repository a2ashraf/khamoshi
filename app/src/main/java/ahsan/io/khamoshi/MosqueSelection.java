package ahsan.io.khamoshi;

import android.Manifest;
import android.app.NotificationManager;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kontakt.proximity.KontaktProximitySDK;
import com.kontakt.proximity.listener.RegionListener;
import com.kontakt.sdk.android.ble.device.BeaconRegion;
import com.kontakt.sdk.android.common.profile.IBeaconDevice;
import com.kontakt.sdk.android.common.profile.IBeaconRegion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

import ahsan.io.khamoshi.Utilities.KhamoshiPreferences;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MosqueSelection extends AppCompatActivity {
    
    private int ringerState;
    private String identifier;
    public String timestring;
    private static final int INITIAL_REQUEST=1337;
    private static final int LOCATION_REQUEST=INITIAL_REQUEST+3;
    
    private static final String[] INITIAL_PERMS={
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
    
    };
    private static int scrollToPosition = 0;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
//    @BindView(R.id.fab)
//    FloatingActionButton fab;
    //    @BindArray(R.array.batimings)
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    
    @BindView(R.id.prayerview)
    public RecyclerView prayerview;
    
    @Override
    protected void onResume() {
        super.onResume();
    
    }
    
    
    private int getRowNumber(Date time) {
//        Calendar.getInstance().get(Calendar.FRIDAY);
//
        return 0;
    }
    
    
    
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch(requestCode) {
            case LOCATION_REQUEST:
                if (canAccessLocation()) {
                    checkLocation();
                }
                else {
                
                }
                break;
        }
    }
    
    private void checkLocation() {
        IBeaconRegion region = new BeaconRegion.Builder()
                .identifier("Region 1")
                .proximity(UUID.fromString("f7826da6-4fa2-4e98-8024-bc5b71e0893e"))
                .major(57593)
                .minor(17906)
                .build();
    
        KontaktProximitySDK kontaktSDK = ((KhamoshiApp) getApplication()).getKontaktSDK();
        kontaktSDK.iBeacon(region, new RegionListener() {
            @Override
            public void onEntered(IBeaconRegion region, List<IBeaconDevice> iBeacons) {
                timestring = "ENTERED " + getIdentifier(region.getIdentifier()) + " " + getHumanReadableTime();
                Log.d("Kontakt!", timestring);
            
                ringerState = getRingerState();
                silencePhone();
                triggerNoti(true);
                Toast.makeText(getApplicationContext(),"ENTERED-Silenced",Toast.LENGTH_SHORT);
            
            }
        
            @Override
            public void onAbandoned(IBeaconRegion region) {
                triggerNoti(false);
                timestring = "EXITED " + getIdentifier(region.getIdentifier()) + " " + getHumanReadableTime();
                Log.d("Kontakt!", timestring);
                setRingerMode();
                Toast.makeText(getApplicationContext(),"ABANDONED-Ringer NORMAL",Toast.LENGTH_SHORT);
            }
        });
    
    }
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mosque_selection);
        ButterKnife.bind(this);



        if (!canAccessLocation()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(INITIAL_PERMS, INITIAL_REQUEST);
            }
        }
    
      
        
        
        KhamoshiPreferences.putStringPref(KhamoshiPreferences.DEFAULT_MOSQUE, "baitulislam", getApplicationContext());
        
        setSupportActionBar(toolbar);
    
    
        PrayerDayModel[] prayerDayModels = loadMosque();
        
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(prayerview);
        PrayerViewAdapter adapter = new PrayerViewAdapter(this, prayerDayModels);
           prayerview.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
 
    
        prayerview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        prayerview.smoothScrollToPosition(scrollToPosition);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                loadNextMosque();
//            }
//        });
      
       

    }
    
    
    private boolean canAccessLocation() {
        return(hasPermission(Manifest.permission.ACCESS_FINE_LOCATION));
    }
    
    private boolean hasPermission(String perm) {
        return true;
//        return(PackageManager.PERMISSION_GRANTED==checkSelfPermission(perm));
    }
    
    private void doLocationThing() {
//        Toast.makeText(this, R.string.toast_location, Toast.LENGTH_SHORT).show();
    }
    
    public String getIdentifier(String id) {
        
        if (id.toLowerCase().contains("front")) {
            return id + " - " + "qNl9";
        } else if (id.toLowerCase().contains("side")) {
            return id + " - " + "qZwD";
        }
        
        return identifier;
    }
    
    private String getHumanReadableTime() {
        String dateString = "";
        long longTimeStamp = Calendar.getInstance().getTimeInMillis();
        
        Calendar calendar = Calendar.getInstance();
        TimeZone tz = TimeZone.getTimeZone(String.valueOf(TimeZone.getTimeZone("Toronto")));
        calendar.setTimeInMillis(longTimeStamp);
        calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        Date currenTimeZone = (Date) calendar.getTime();
        return sdf.format(currenTimeZone);
    }
    
    public void setRingerMode() {
        AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
    }
    private void silencePhone() {
        AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
    }
    
    public void triggerNoti(boolean enter) {
        String title;
        String text;
        if (enter) {
            title = "Beacon Recognized";
            text = "Phone in silent mode";
        } else {
            title = "Beacon NO FOUND";
            text = "Phone in NORMAL mode";
        }
        
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.bait_ul_islam)
                .setContentTitle(title)
                .setContentText(text);
        
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(001, mBuilder.build());
    }
    
    public int getRingerState() {
        AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        
        switch (am.getRingerMode()) {
            case AudioManager.RINGER_MODE_SILENT:
                Log.i("Kontakt", "Silent mode");
                break;
            case AudioManager.RINGER_MODE_VIBRATE:
                Log.i("Kontakt", "Vibrate mode");
                break;
            case AudioManager.RINGER_MODE_NORMAL:
                Log.i("Kontakt", "Normal mode");
                break;
        }
        
        return ringerState;
    }
    
    
    
    public class PrayerViewAdapter  extends RecyclerView.Adapter<PrayerViewAdapter.ViewHolder> {
        private final Context ctx;
        private ViewHolder viewHolder;
        PrayerDayModel[] prayerlist;
        private SimpleDateFormat time_format;
    
        public PrayerViewAdapter(Context context,PrayerDayModel[] prayers) {
            time_format = new SimpleDateFormat("dd - MMM yyyy");
    
            prayerlist = prayers;
            ctx = context;
        }
    
        @Override
        public void onBindViewHolder(PrayerViewAdapter.ViewHolder holder, int position) {
                if(null!=prayerlist && prayerlist.length>0){
                    PrayerDayModel aPrayer = prayerlist[position];
                    holder.fajrTime.setText(aPrayer.getFajr());
                    holder.zuhrTime.setText(aPrayer.getZuhr());
                    holder.asrTime.setText(aPrayer.getAsr());
                    holder.maghribTime.setText(aPrayer.getMaghrib());
                    holder.ishaTime.setText(aPrayer.getIsha());
                    holder.dateRange.setText(time_format.format(aPrayer.getDate()));
                }
        }
    
        @Override
        public int getItemCount() {
            return prayerlist.length;
            
        }
    
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View weeklyPrayerTimesView = LayoutInflater.from(ctx).inflate(R.layout.week_prayer_timings, parent, false);
            viewHolder = new ViewHolder(weeklyPrayerTimesView);
            return viewHolder;
        }
    
    
    
        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView fajrTime;
            TextView zuhrTime;
            TextView asrTime;
            TextView maghribTime;
            TextView ishaTime;
            TextView dateRange;
            
            
            
            public ViewHolder(View itemView) {
                super(itemView);
    
                if (itemView != null) {
                    dateRange = (TextView) itemView.findViewById(R.id.daterange);
                    dateRange.setOnClickListener(this);
    
                    fajrTime = (TextView) itemView.findViewById(R.id.textview_fajr_value);
                    fajrTime.setOnClickListener(this);
        
                    zuhrTime = (TextView) itemView.findViewById(R.id.textview_zuhr_value);
                    zuhrTime.setOnClickListener(this);
        
                    asrTime = (TextView) itemView.findViewById(R.id.textview_asr_value);
                    asrTime.setOnClickListener(this);
        
                    maghribTime = (TextView) itemView.findViewById(R.id.textview_magrib_value);
                    maghribTime.setOnClickListener(this);
        
                    ishaTime = (TextView) itemView.findViewById(R.id.textview_isha_value);
                    ishaTime.setOnClickListener(this);
        
        
                }
            }
    
            @Override
            public void onClick(View view) {
        
            }
        }
    }
    
    private PrayerDayModel[] loadMosque() {
      //?? rename to seticon.
        loadNextMosque();
    
        String savedMosqueName = loadSavedMosque();
        if (loadSavedMosque() != null) {
            return loadMosque(savedMosqueName);
        } else {
            return loadMosque(getDefaultMosqueName());
        }
    }
    
    private String getDefaultMosqueName() {
        String defaultMosque = KhamoshiPreferences.getStringPref(KhamoshiPreferences.DEFAULT_MOSQUE, getApplicationContext());
        return defaultMosque;
    }
    
    //TODO: iterate mosque array (loadnextmosque over and over until you get your mosqueToLoad = to the right mosque...then take data and load it. in recyclerview.
    private PrayerDayModel[] loadMosque(String defaultMosque) {
        //background if possible.
        String[][] timings = getTimings(R.array.batimings);
        
        //highlight this week's data.  get todays date, if its less than date and greater than date, then selected row is the upper.
        
        PrayerDayModel[] prayers = objectConvert(timings.length, timings);
        
        return prayers;
    }
    
    
    
    //goal: get the time, comapre to today, return array of 5 rows  - highlighting the second one.
    public static PrayerDayModel[] objectConvert(int count, String[][] timings) {
        Date todaysDate = Calendar.getInstance().getTime();
    
        String[][] returnArray = new String[5][];
        PrayerDayModel[] prayers = new PrayerDayModel[count];
    
        String day;
        for (int i = 0; i < count; i++) {
            if (!timings[i][0].contains("*")) {
                day = "FRIDAY";
            } else {
                day = "SUNDAY";
                timings[i][0] = timings[i][0].replace("*", "");
            }
            String[] daymonth;
            Calendar calendardateOfWeek = Calendar.getInstance();
            Date dateofWeek = null;
                dateofWeek = calendardateOfWeek.getTime();
            
                daymonth = timings[i][0].split("-");
                calendardateOfWeek = Calendar.getInstance();
                String date = timings[i][0] + " 2017";
                SimpleDateFormat time_format = new SimpleDateFormat("dd - MMM yyyy");
              
                try {
                    dateofWeek = time_format.parse(date);
                    if(todaysDate.getTime()>dateofWeek.getTime()){
                         scrollToPosition = i;
                        
                    }else{
                        
                    }
        
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            
    
    
            
            prayers[i] = new PrayerDayModel(dateofWeek, timings[i][1],
                    timings[i][2], timings[i][3], timings[i][4], timings[i][5],
                    timings[i][6], timings[i][7],day);
        }
    
         return prayers;
    }
    
    /*test by creating a variable, putint and then checking if exists.*/
    private String loadSavedMosque() {
        String savedMosque = KhamoshiPreferences.getStringPref(KhamoshiPreferences.SAVED_MOSQUE, getApplicationContext());
        if (savedMosque.equals(null)) {
            return savedMosque;
        } else {
            return null;
        }
        
    }
    
    public void loadNextMosque() {
        
        collapsingToolbarLayout.setBackground(getResources().getDrawable(R.drawable.bait_ul_islam));
        String[][] timingData = getTimings(R.array.batimings);
        //TODO: now what to do with this data?
    }
    
    String[][] getTimings(@ArrayRes int mosqueTimingsArray) {
        Resources res = getResources();
        TypedArray ta = res.obtainTypedArray(mosqueTimingsArray);
        int n = ta.length();
        String[][] array = new String[n][];
        for (int i = 0; i < n; ++i) {
            int id = ta.getResourceId(i, 0);
            if (id > 0) {
                array[i] = res.getStringArray(id);
            } else {
                // something wrong with the XML
            }
        }
        ta.recycle(); // Important!
        
        return array;
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      //  getMenuInflater().inflate(R.menu.menu_mosque_selection, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
//    @OnClick(fab)
//    void submit(View view) {
//        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show();
//
//        //TODO: onclick, swap - later it will be iterate...image with animation, show new timings
//    }
}
