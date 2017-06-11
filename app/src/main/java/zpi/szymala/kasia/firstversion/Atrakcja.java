package zpi.szymala.kasia.firstversion;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by Katarzyna on 2017-05-21.
 */

public class Atrakcja {

    private String nazwa;
    private String szczegoly;
    private String zdjecieURL;
    private double latitude;
    private double longitude;

    private String kategoria;
    private HashMap<String, String> godzinyOtwarcia = null;
    private boolean zawszeOtwarte = false;

    public Atrakcja() {
    }

    ;

    public Atrakcja(String nazwa, String szczegoly, String zdjecieURL) {
        this.setNazwa(nazwa);
        this.setSzczegoly(szczegoly);
        this.setZdjecieURL(zdjecieURL);
        setLatitude(0.0);
        setLongitude(0.0);
    }

    public Atrakcja(String nazwa, String szczegoly, String zdjecieURL, double latitude, double longitude) {
        this.setNazwa(nazwa);
        this.setSzczegoly(szczegoly);
        this.setZdjecieURL(zdjecieURL);
        this.setLatitude(latitude);
        this.setLongitude(longitude);

    }

    public Atrakcja(String kategoria, String nazwa, String szczegoly, String zdjecieURL, double latitude, double longitude, String po, String wt, String sr, String cz, String pt, String so, String ni) {
        this.setKategoria(kategoria);
        this.setNazwa(nazwa);
        this.setSzczegoly(szczegoly);
        this.setZdjecieURL(zdjecieURL);
        this.setLatitude(latitude);
        this.setLongitude(longitude);
        if (po.equals("x"))
            zawszeOtwarte = true;
        else {
            godzinyOtwarcia = new HashMap<>();
            if (po.equals(""))
                po = null;
            godzinyOtwarcia.put("pon", po);
            if (wt.equals(""))
                wt = null;
            godzinyOtwarcia.put("wto", wt);
            if (sr.equals(""))
                sr = null;
            godzinyOtwarcia.put("sro", sr);
            if (cz.equals(""))
                cz = null;
            godzinyOtwarcia.put("czw", cz);
            if (pt.equals(""))
                pt = null;
            godzinyOtwarcia.put("pia", pt);
            if (so.equals(""))
                so = null;
            godzinyOtwarcia.put("sob", so);
            if (ni.equals(""))
                ni = null;
            godzinyOtwarcia.put("nie", ni);
        }
    }


    public LatLng buildLocation() {
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

    public HashMap<String, String> getGodzinyOtwarcia() {
        return godzinyOtwarcia;
    }

    public void setGodzinyOtwarcia(HashMap<String, String> godzinyOtwarcia) {
        this.godzinyOtwarcia = godzinyOtwarcia;
    }

    public String getKategoria() {
        return kategoria;
    }

    public void setKategoria(String kategoria) {
        this.kategoria = kategoria;
    }

    public boolean isZawszeOtwarte() {
        return zawszeOtwarte;
    }

    public void setZawszeOtwarte(boolean zawszeOtwarte) {
        this.zawszeOtwarte = zawszeOtwarte;
    }

    public String buildGodzinyOtwarcia() {
        if (isZawszeOtwarte())
            return "Nie dotyczy";
        else {
            ArrayList<String> dni = new ArrayList<>(Arrays.asList("Pon", "Wto", "Sro", "Czw", "Pia", "Sob", "Nie", null));
            boolean kont = false;
            boolean initial = true;
            String previous = null;
            StringBuilder res = new StringBuilder();
            for (String dzien : dni) {
                if (dzien != null && godzinyOtwarcia.containsKey(dzien.toLowerCase())) {
                    if (!kont) {
                        if (previous == null) {
                            res.append(dzien);
                        } else {
                            if (godzinyOtwarcia.get(dzien.toLowerCase()).equals(godzinyOtwarcia.get(previous.toLowerCase()))) {
                                kont = true;
                            } else {
                                res.append(": ").append(godzinyOtwarcia.get(previous.toLowerCase())).append("\n").append(dzien);
                            }
                        }
                    } else {
                        if (godzinyOtwarcia.get(dzien.toLowerCase()).equals(godzinyOtwarcia.get(previous.toLowerCase()))) {
                            kont = true;
                        } else {
                            res.append("-").append(previous).append(": ").append(godzinyOtwarcia.get(previous.toLowerCase())).append("\n").append(dzien);
                            kont = false;
                        }
                    }
                    previous = dzien;
                } else {
                    if (kont) {
                        res.append("-").append(previous).append(": ").append(godzinyOtwarcia.get(previous.toLowerCase())).append("\n");
                        kont = false;
                    }
                    else {
                        if (previous != null)
                        res.append(": ").append(godzinyOtwarcia.get(previous.toLowerCase())).append("\n");
                    }
                    previous = null;
                }
            }
            return res.toString();
        }
    }
}