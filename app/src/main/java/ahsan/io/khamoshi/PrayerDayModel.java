package ahsan.io.khamoshi;

import java.util.Date;

/**
 * Created by Ahsan on 2017-07-14.
 */

public class PrayerDayModel {
    
    String fajr;
    String zuhr;
    String asr;
    String maghrib;
    String isha;
    String sunrise;
    String sunset;
    String unformateddate;
    Date  date;
    String day;
    
    
    public PrayerDayModel(Date date,String fajr,  String sunrise,String zuhr, String asr,  String sunset,String maghrib, String isha, String day) {
        this.fajr = fajr;
        this.zuhr = zuhr;
        this.asr = asr;
        this.maghrib = maghrib;
        this.isha = isha;
        this.sunrise = sunrise;
        this.sunset = sunset;
        
        this.date = date;
        this.day = day;
    }
    
    public String getFajr() {
        return fajr;
    }
    
    public void setFajr(String fajr) {
        this.fajr = fajr;
    }
    
    public String getZuhr() {
        return zuhr;
    }
    
    public void setZuhr(String zuhr) {
        this.zuhr = zuhr;
    }
    
    public String getAsr() {
        return asr;
    }
    
    public void setAsr(String asr) {
        this.asr = asr;
    }
    
    public String getMaghrib() {
        return maghrib;
    }
    
    public void setMaghrib(String maghrib) {
        this.maghrib = maghrib;
    }
    
    public String getIsha() {
        return isha;
    }
    
    public void setIsha(String isha) {
        this.isha = isha;
    }
    
    public String getSunrise() {
        return sunrise;
    }
    
    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }
    
    public String getSunset() {
        return sunset;
    }
    
    public void setSunset(String sunset) {
        this.sunset = sunset;
    }
    
    
    public Date getDate() {
        return date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    
    public String getDay() {
        return day;
    }
    
    public void setDay(String day) {
        this.day = day;
    }
    
    
    
}
