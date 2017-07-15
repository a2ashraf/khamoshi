package ahsan.io.khamoshi.beacon;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kontakt.sdk.android.ble.connection.OnServiceReadyListener;
import com.kontakt.sdk.android.ble.device.BeaconRegion;
import com.kontakt.sdk.android.ble.manager.ProximityManager;
import com.kontakt.sdk.android.ble.manager.ProximityManagerFactory;
import com.kontakt.sdk.android.ble.manager.listeners.IBeaconListener;
import com.kontakt.sdk.android.ble.manager.listeners.simple.SimpleIBeaconListener;
import com.kontakt.sdk.android.common.profile.IBeaconDevice;
import com.kontakt.sdk.android.common.profile.IBeaconRegion;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;
//
//import ca.khuddam.connect.android.R;
//import ca.khuddam.connect.android.fragment.BaseAppFragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Beacon.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Beacon#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Beacon extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ProximityManager proximityManager;
    private TextView beaconview;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    
    private OnFragmentInteractionListener mListener;
    private Collection<IBeaconRegion> beaconRegions;
    private int ringerState;
     Context baseContext;
    public Beacon() {
        // Required empty public constructor
    }
  
//    @Override
//    public void oniBeaconServiceConnect() {
//
//
//        beaconManager.addRangeNotifier(new RangeNotifier() {
//            @Override
//            public void didRangeBeaconsInRegion(Collection<org.altbeacon.beacon.Beacon> collection, Region region) {
//                Log.d("AltBeacon: ", String.valueOf(collection.size()));
//            }
//
//        });
//
//        try {
//            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
//        } catch (RemoteException e) {   }
//    }
//
    
    
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Beacon.
     */
    // TODO: Rename and change types and number of parameters
    public static Beacon newInstance(String param1, String param2) {
        Beacon fragment = new Beacon();
   
        return fragment;
    }
    
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    
    
    @Override
    public void onStart() {
        super.onStart();
        configureBeacon();
        startScanning();
    }
    
    private void configureBeacon() {
        
    }
    
    @Override
    public void onStop() {
        proximityManager.stopScanning();
        super.onStop();
    }
    
    @Override
    public void onDestroy() {
        proximityManager.disconnect();
        proximityManager = null;
        super.onDestroy();
    }
    
    private void startScanning() {
        proximityManager.connect(new OnServiceReadyListener() {
            @Override
            public void onServiceReady() {
                proximityManager.startScanning();
            }
        });
    }
    
    private IBeaconListener createIBeaconListener() {
        return new SimpleIBeaconListener() {
            @Override
            public void onIBeaconDiscovered(IBeaconDevice ibeacon, IBeaconRegion region) {
    
//                beaconview.setText("IBeacon discovered: " + ibeacon.getFirmwareVersion());
            }
        };
    }
    
    
    
    private String getHumanReadableTime() {
        String dateString = "";
        long longTimeStamp=Calendar.getInstance().getTimeInMillis();
       
        Calendar calendar = Calendar.getInstance();
        TimeZone tz = TimeZone.getTimeZone(String.valueOf(TimeZone.getTimeZone("Toronto")));
        calendar.setTimeInMillis(longTimeStamp);
        calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        Date currenTimeZone = (Date) calendar.getTime();
        return sdf.format(currenTimeZone);
    }
  
    @Override
    public void onResume() {
        super.onResume();
//        proximityManager.setSpaceListener(new SpaceListener() {
//            private String identifier;
//
//            public String getIdentifier(String id) {
//                if(id.toLowerCase().contains("front")){
//                    return id + " - " + "qNl9";
//                } else if (id.toLowerCase().contains("side")) {
//                    return id + " - " + "qZwD";
//                }
//
//                return identifier;
//            }
//
//            private int ringerState;
//            public void setRingerMode() {
//                AudioManager am = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
//                am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
//            }
//
//
//
//            public int getRingerState() {
//                AudioManager am = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
//
//                switch (am.getRingerMode()) {
//                    case AudioManager.RINGER_MODE_SILENT:
//                        Log.i("Kontakt","Silent mode");
//                        break;
//                    case AudioManager.RINGER_MODE_VIBRATE:
//                        Log.i("Kontakt","Vibrate mode");
//                        break;
//                    case AudioManager.RINGER_MODE_NORMAL:
//                        Log.i("Kontakt","Normal mode");
//                        break;
//                }
//
//                return ringerState;
//            }
//
//            public String timestring;
//
//            @Override
//            public void onRegionEntered(IBeaconRegion region) {
//                //IBeacon region has been entered
//                Log.d("Kontakt", region.getIdentifier() + "ENTERED");
//                beaconview = getView().findViewById(R.id.beacon_msg);
//                timestring = "ENTERED " + getIdentifier(region.getIdentifier()) + " " + getHumanReadableTime();
//                beaconview.setText(beaconview.getText().toString() + "\n " + timestring);
//                ringerState = getRingerState();
//                silencePhone();
//
//
//            }
//
//            private void silencePhone() {
//                AudioManager am = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
//                am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
//            }
//
//            @Override
//            public void onRegionAbandoned(IBeaconRegion region) {
//                //IBeacon region has been abandoned
//                Log.d("Kontakt", region.getIdentifier() + "ABANDONED");
//                timestring = "ABANDONED" + region.getIdentifier() + " " + getHumanReadableTime();
//                beaconview.setText(beaconview.getText().toString() + "\n " + timestring);
//                setRingerMode();
//            }
//
//            @Override
//            public void onNamespaceEntered(IEddystoneNamespace namespace) {
//
//            }
//
//            @Override
//            public void onNamespaceAbandoned(IEddystoneNamespace namespace) {
//
//            }
//
//        });
    }
    
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        baseContext = context;
         if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    
        
    
    
        proximityManager = ProximityManagerFactory.create(getContext());
        proximityManager.spaces().iBeaconRegions(getBeaconRegions());
        proximityManager.setIBeaconListener(createIBeaconListener());
    
    
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        
        return inflater.inflate(null, container, false);
    }
    
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    
    public Collection<IBeaconRegion> getBeaconRegions() {
    
    
        Collection<IBeaconRegion> beaconRegions = new ArrayList<IBeaconRegion>();
    
        IBeaconRegion beacon_qNl9 = new BeaconRegion.Builder()
                .identifier("Mosque Front Mens Door")
                .proximity(UUID.fromString("f7826da6-4fa2-4e98-8024-bc5b71e0893e"))
                .major(57593)
                .minor(17906)
                .build();
    
        IBeaconRegion beacon_qZwD  = new BeaconRegion.Builder()
                .identifier("Mosque Side Mens Door")
                .proximity(UUID.fromString("f7826da6-4fa2-4e98-8024-bc5b71e0893e"))
                .major(47923) //any major, default value
                .minor(58355) //any minor, default value
                .build();


        beaconRegions.add(beacon_qZwD);
        beaconRegions.add(beacon_qNl9);
        
        return beaconRegions;
    }
    
   
    
//    @Override
//    public Context getApplicationContext() {
//        return baseContext ;
//    }
//
//    @Override
//    public void unbindService(ServiceConnection serviceConnection) {
//
//    }
//
//    @Override
//    public boolean bindService(Intent intent, ServiceConnection serviceConnection, int i) {
//
//        return false;
//    }
//
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
