package zpi.mazurek.tomasz.firstversion;

import java.util.ArrayList;

import zpi.szymala.kasia.firstversion.Atrakcja;

/**
 * Created by Tomasz on 28.05.2017.
 */

public class BaseTrip {

    private long estimatedTime;
    private float rate;
    private String photoURL;
    private String name;
    private ArrayList<Atrakcja> attractions;

    public BaseTrip()
    {
        setName("");
        setAttractions(new ArrayList<Atrakcja>());
        setRate(0);
    }

    public BaseTrip(String name, String photoURL)
    {
        this.setPhotoURL(photoURL);
        this.setName(name);
        setAttractions(new ArrayList<Atrakcja>());
        setRate(0);
    }

    public void addAttraction(Atrakcja attraction)
    {
        getAttractions().add(attraction);
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

    public ArrayList<Atrakcja> getAttractions() {
        return attractions;
    }

    public void setAttractions(ArrayList<Atrakcja> attractions) {
        this.attractions = attractions;
    }
}
