package zpi.mazurek.tomasz.firstversion;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import zpi.lyjak.anna.DayOfTrip;
import zpi.szymala.kasia.firstversion.Atrakcja;

/**
 * Created by Tomasz on 21.05.2017.
 */

public class Trip {

    private Calendar startDate;
    private Calendar endDate;
    private boolean isFinished;

    //potrzebne do sqla:
    private String tripId;
    private String idSpectacularAttraction;
    private ArrayList<DayOfTrip> days;

    private long tripTime; //
    private int rate; //
    private String name; //

    //porponuję przerzucić do DayOfTrip:
    ArrayList<Atrakcja> attractions;

    public Trip()
    {
        name = "Wycieczka";
        isFinished = false;
        endDate = null;
        setStartDate(Calendar.getInstance());
        attractions = new ArrayList<>();
    }

    public Trip(String name)
    {
        this.name = name;
        isFinished = false;
        endDate = null;
        setStartDate(Calendar.getInstance());
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

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public long getTripTime() {
        return tripTime;
    }

    public void setTripTime(long tripTime) {
        this.tripTime = tripTime;
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

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public int countAttractions() {
        int counter = 0;
        for (DayOfTrip day : getDays())
            counter += day.getAttractions().size();
        return counter;
    }

    public ArrayList<DayOfTrip> getDays() {
        return days;
    }

    public void setDays(ArrayList<DayOfTrip> days) {
        this.days = days;
    }
}
