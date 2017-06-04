package zpi.mazurek.tomasz.firstversion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import zpi.szymala.kasia.firstversion.Atrakcja;

/**
 * Created by Tomasz on 28.05.2017.
 */

public class BaseTrip {

    private long estimatedTime;
    private float rate;
    private String photoURL;
    private String name;
    private HashMap<String, HashMap<String, String>> attractions;

    public BaseTrip()
    {
//        setName("");
        attractions = new HashMap<>();
//        setRate(0);
    }

    public BaseTrip(String name, String photoURL)
    {
        this.setPhotoURL(photoURL);
        this.setName(name);
        setRate(0);
    }

    public long getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(long estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, HashMap<String, String>> getAttractions() {
        return attractions;
    }

    public void setAttractions(HashMap<String, HashMap<String, String>> attractions) {
        this.attractions = attractions;
    }
}
