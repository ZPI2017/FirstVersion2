package zpi.szymala.kasia.firstversion;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Katarzyna on 2017-05-21.
 */

public class Atrakcja {

    private String nazwa;
    private String szczegoly;
    private String zdjecieURL;
    private double latitude;
    private double longitude;

    public Atrakcja(){};

    public Atrakcja(String nazwa, String szczegoly,String zdjecieURL){
        this.setNazwa(nazwa);
        this.setSzczegoly(szczegoly);
        this.setZdjecieURL(zdjecieURL);
        setLatitude(0.0);
        setLongitude(0.0);
    }

    public Atrakcja(String nazwa, String szczegoly, String zdjecieURL, double latitude, double longitude ){
        this.setNazwa(nazwa);
        this.setSzczegoly(szczegoly);
        this.setZdjecieURL(zdjecieURL);
        this.setLatitude(latitude);
        this.setLongitude(longitude);

    }


    public LatLng buildLocation()
    {
        return new LatLng(latitude, longitude);
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getSzczegoly() {
        return szczegoly;
    }

    public void setSzczegoly(String szczegoly) {
        this.szczegoly = szczegoly;
    }

    public String getZdjecieURL() {
        return zdjecieURL;
    }

    public void setZdjecieURL(String zdjecieURL) {
        this.zdjecieURL = zdjecieURL;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
