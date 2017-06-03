package zpi.mazurek.tomasz.firstversion;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import zpi.szymala.kasia.firstversion.Atrakcja;

/**
 * Created by Tomasz on 21.05.2017.
 */

public class Trip {

    private final Calendar startDate;
    private Calendar endDate;
    private boolean isFinished;
    private long tripTime;
    private int rate;
    private String name;
    ArrayList<Atrakcja> attractions;

    public Trip()
    {
        name = "";
        isFinished = false;
        endDate = null;
        startDate = Calendar.getInstance();
        attractions = new ArrayList<>();
    }

    public Trip(String name)
    {
        this.name = name;
        isFinished = false;
        endDate = null;
        startDate = Calendar.getInstance();
        attractions = new ArrayList<>();
    }

    public void setRate(int rate)
    {
        this.rate = rate;
    }

    public int getRate(){ return rate;}

    public void endTrip()
    {
        endDate = Calendar.getInstance();
        tripTime = endDate.getTimeInMillis() - startDate.getTimeInMillis();
        isFinished = true;
    }

    public String getStartDate()
    {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format1.format(startDate.getTime());
    }

    public String getEndDate()
    {
        String result;
        if(!isFinished)
        {
            result = "W trakcie";
        }
        else {
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            result = format1.format(startDate.getTime());
        }
        return result;
    }

    public String getTripTimeAsString()
    {
        String result;
        if(!isFinished)
            result = "";
        else
            result = tripTime/3600000 + "h " + tripTime%360000 + "m ";
        return result;
    }

    public void addAttraction(Atrakcja attraction)
    {
        attractions.add(attraction);
    }
}
