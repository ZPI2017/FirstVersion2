package zpi.szymala.kasia.firstversion;

/**
 * Created by Katarzyna on 2017-05-21.
 */

public class Atrakcja {

    private  String nazwa, szczegoly;
    private  int zdjecie;

    public Atrakcja(String nazwa, String szczegoly,int zdjecie){
        this.nazwa=nazwa;
        this.szczegoly=szczegoly;
        this.zdjecie=zdjecie;

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
}
