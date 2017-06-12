package zpi.lyjak.anna;

import android.animation.Animator;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.model.SlideMenuItem;
import yalantis.com.sidemenu.util.ViewAnimator;
import zpi.lignarski.janusz.CreateTripActivity;
import zpi.mazurek.tomasz.firstversion.ActiveTripActivity;
import zpi.mazurek.tomasz.firstversion.RecomendedTrips;
import zpi.mazurek.tomasz.firstversion.Trip;
import zpi.mazurek.tomasz.firstversion.TripHistoryActivity;
import zpi.lyjak.anna.firstversion.R;
import zpi.lyjak.anna.fragments.ExampleFragment;
import zpi.szymala.kasia.firstversion.Atrakcja;
import zpi.szymala.kasia.firstversion.ShowAtrakcje;

/**
 * @author Anna Łyjak
 */
public class MainActivity extends AppCompatActivity implements ViewAnimator.ViewAnimatorListener {

    public static Trip activeTrip;
    public static HashMap<String, Boolean> visitedAtractions;
    public static ArrayList<Trip> tripHistory;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private List<SlideMenuItem> list = new ArrayList<>();
    private ExampleFragment contentFragment;
    private ViewAnimator viewAnimator;
    private int res = R.drawable.start_layout;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Statyczne dane do prezentacji

        tripHistory = new ArrayList<>();
        Trip tripExample = new Trip();
        tripExample.endTrip();
        tripExample.setRate(3);
        Trip tripExample2 = new Trip();
        tripExample2.endTrip();
        tripExample.setRate(5);
        tripExample.addAttraction(new Atrakcja("Hala Stulecia", "", ""));
        tripExample.addAttraction(new Atrakcja("Zoo", "", ""));
        tripExample.addAttraction(new Atrakcja("Stadion Olimpijski", "", ""));

        MainActivity.tripHistory.add(tripExample);
        MainActivity.tripHistory.add(tripExample2);

        contentFragment = ExampleFragment.newInstance(R.drawable.start_layout);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, contentFragment)
                .commit();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        linearLayout = (LinearLayout) findViewById(R.id.left_drawer);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });


        setActionBar();
        list = MenuCreator.createMenuList();
        viewAnimator = new ViewAnimator<>(this, list, contentFragment, drawerLayout, this);
    }

    private void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                linearLayout.removeAllViews();
                linearLayout.invalidate();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset > 0.6 && linearLayout.getChildCount() == 0)
                    viewAnimator.showMenuContent();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.action_map:
                startActivity(new Intent(this, MapsMainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    private ScreenShotable replaceFragment(ScreenShotable screenShotable, int topPosition) {
//        this.res = this.res == R.drawable.content_music ? R.drawable.content_films : R.drawable.content_music;
//        View view = findViewById(R.id.content_frame);
//        int finalRadius = Math.max(view.getWidth(), view.getHeight());
//        Animator animator = null;
//        //TODO MENU DZIALA TYLKO DLA API >= 21!
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//            animator = ViewAnimationUtils.createCircularReveal(view, 0, topPosition, 0, finalRadius);
//        }
//        animator.setInterpolator(new AccelerateInterpolator());
//        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);
//
//        findViewById(R.id.content_overlay).setBackgroundDrawable(new BitmapDrawable(getResources(), screenShotable.getBitmap()));
//        animator.start();
//        ExampleFragment contentFragment = ExampleFragment.newInstance(this.res);
//        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, contentFragment).commit();
//        return contentFragment;
//    }

    @Override
    public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position) {
        switch (slideMenuItem.getName()) {
            case ExampleFragment.CLOSE:
                return screenShotable;
            case "Active Trip":
                startActivity(new Intent(this, ActiveTripActivity.class));
                return screenShotable;
            case "Trip History":
                startActivity(new Intent(this, TripHistoryActivity.class));
                return screenShotable;
            case "Recomended Trips":
                startActivity(new Intent(this, RecomendedTrips.class));
                return screenShotable;
            case "Map":
                startActivity(new Intent(this, MapsMainActivity.class));
                return screenShotable;
            case "Add Trips":
                startActivity(new Intent(this, CreateTripActivity.class));
                return screenShotable;
            case "Details":
                startActivity(new Intent(this, ShowAtrakcje.class));
                return screenShotable;
            default:
                startActivity(new Intent(this, MapsMainActivity.class));
                return screenShotable;
        }
    }

    @Override
    public void disableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(false);
    }

    @Override
    public void enableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout.closeDrawers();
    }

    @Override
    public void addViewToContainer(View view) {
        linearLayout.addView(view);
    }
}
