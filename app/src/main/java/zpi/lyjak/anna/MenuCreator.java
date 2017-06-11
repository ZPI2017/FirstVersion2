package zpi.lyjak.anna;

import android.content.Intent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.model.SlideMenuItem;
import yalantis.com.sidemenu.util.ViewAnimator;
import zpi.lignarski.janusz.CreateTripActivity;
import zpi.lyjak.anna.firstversion.R;
import zpi.lyjak.anna.fragments.ExampleFragment;
import zpi.mazurek.tomasz.firstversion.ActiveTripActivity;
import zpi.mazurek.tomasz.firstversion.RecomendedTrips;
import zpi.mazurek.tomasz.firstversion.TripHistoryActivity;
import zpi.szymala.kasia.firstversion.ShowAtrakcje;

/**
 * Created by Anna Łyjak on 12.06.2017.
 */

public class MenuCreator {

    private static List<SlideMenuItem> list = new ArrayList<>();

    public static List<SlideMenuItem> createMenuList() {
        if(list.size() == 0 ) {
            SlideMenuItem activeTrip = new SlideMenuItem("Active Trip", R.drawable.active_icon);
            list.add(activeTrip);

            SlideMenuItem recomendedTrips = new SlideMenuItem("Recomended Trips", R.drawable.heart);
            list.add(recomendedTrips);

            SlideMenuItem mapOpen = new SlideMenuItem("Map", R.drawable.mapiconmini);
            list.add(mapOpen);

            SlideMenuItem addTrip = new SlideMenuItem("Add Trips", R.drawable.create);
            list.add(addTrip);

            SlideMenuItem details = new SlideMenuItem("Details", R.drawable.details);
            list.add(details);

            SlideMenuItem history = new SlideMenuItem("Trip History", R.drawable.history_icon);
            list.add(history);
        }
        return list;
    }

}
