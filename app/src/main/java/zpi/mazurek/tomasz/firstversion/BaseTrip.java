package zpi.mazurek.tomasz.firstversion;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import zpi.szymala.kasia.firstversion.Atrakcja;

/**
 * Created by Tomasz on 28.05.2017.
 */

public class BaseTrip {

    private long estimatedTime;
    private float rate;
    private int photo;
    private String name;
    ArrayList<Atrakcja> attractions;

    public BaseTrip()
    {
        name = "";
        attractions = new ArrayList<>();
        rate = 0;
    }

    public BaseTrip(String name, int photo)
    {
        this.photo = photo;
        this.name = name;
        attractions = new ArrayList<>();
        rate = 0;
    }

    public void setRate(int rate)
    {
        this.rate = rate;
    }

    public float getRate(){ return rate;}


    public void addAttraction(Atrakcja attraction)
    {
        attractions.add(attraction);
    }

    public ArrayList<Atrakcja> getAttractions()
    {
        return attractions;
    }

    public String getName()
    {
        return name;
    }

    public int getPhoto()
    {
        return photo;
    }
}
