package zpi.szymala.kasia.firstversion;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Katarzyna on 2017-05-21.
 */

public class Atrakcja {

    private  String nazwa, szczegoly;
    private  int zdjecie;
    private LatLng location;

    public Atrakcja(String nazwa, String szczegoly,int zdjecie){
        this.nazwa=nazwa;
        this.szczegoly=szczegoly;
        this.zdjecie=zdjecie;
        location = new LatLng(0.0,0.0);
    }

    public Atrakcja(String nazwa, String szczegoly,int zdjecie, LatLng location ){
        this.nazwa=nazwa;
        this.szczegoly=szczegoly;
        this.zdjecie=zdjecie;
        this.location = location;

    }

    public String getNazwa(){
        return  nazwa;
    }

    public String getSzczegoly(){
        return szczegoly;
    }

    public int getZdjecie(){
        return zdjecie;
    }

    public LatLng getLocation()
    {
        return location;
    }
}
