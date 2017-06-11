package zpi.lyjak.anna;

import java.util.ArrayList;
import java.util.Calendar;

import zpi.szymala.kasia.firstversion.Atrakcja;

/**
 * Created by Anna Łyjak on 12.06.2017.
 */

public class DayOfTrip {

    private String id;
    private Calendar date;
    private ArrayList<Atrakcja> attractions;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public ArrayList<Atrakcja> getAttractions() {
        return attractions;
    }

    public void setAttractions(ArrayList<Atrakcja> attractions) {
        this.attractions = attractions;
    }
}
