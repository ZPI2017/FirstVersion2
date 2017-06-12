package zpi.lyjak.anna;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Anna Łyjak on 12.06.2017.
 */

public class LocalDbHelper extends SQLiteOpenHelper {

    private static final int DATEBASE_VERSION = 1;
    private static final String DATEBASE_NAME = "tripsCreatorDb.db";

    private static final String SQL_CREATE_ATRACTIONS =
            "CREATE TABLE " + Columns.Attractions.TABLE_NAME + " (" + Columns.Attractions.TABLE_NAME_ATTRACTION_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Columns.Attractions.TABLE_NAME_LATITUDE + " TEXT, "
            + Columns.Attractions.TABLE_NAME_LONGITUDE + " TEXT, " + Columns.Attractions.TABLE_NAME_NAME + " TEXT, "
            + Columns.Attractions.TABLE_NAME_DESCRIPTION + " TEXT, " + Columns.Attractions.TABLE_NAME_LINK + " TEXT, "
            + Columns.Attractions.TABLE_NAME_LINK + " TEXT)";
    private static final String SQL_CREATE_DAYS =
            "CREATE TABLE " + Columns.DayOfTrip.TABLE_NAME + " (" + Columns.DayOfTrip.TABLE_NAME_DAY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Columns.DayOfTrip.TABLE_NAME_DATE + " TEXT, "
            + Columns.DayOfTrip.TABLE_NAME_ATTRACTIONS + " TEXT)";
    private static final String SQL_CREATE_TRIPS =
            "CREATE TABLE " + Columns.TripColumns.TABLE_NAME + " (" + Columns.TripColumns.COLUMN_NAME_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " +  Columns.TripColumns.TABLE_NAME + " TEXT, " + Columns.TripColumns.COLUMN_NAME_START
            + " DATE, " + Columns.TripColumns.COLUMN_NAME_END + " TEXT, " + Columns.TripColumns.COLUMN_NAME_ATTRACTION + " TEXT, "
            + Columns.TripColumns.COLUMN_NAME_DAYS + " TEXT)";

    private static final String SQL_DELETE_ATRACTIONS =
            "DROP TABLE IF EXISTS " + Columns.Attractions.TABLE_NAME;
    private static final String SQL_DELETE_DAYS =
            "DROP TABLE IF EXISTS " + Columns.DayOfTrip.TABLE_NAME;
    private static final String SQL_DELETE_TRIPS =
            "DROP TABLE IF EXISTS " + Columns.TripColumns.TABLE_NAME;

    public LocalDbHelper(Context context) {
        super(context, DATEBASE_NAME, null, DATEBASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ATRACTIONS);
        sqLiteDatabase.execSQL(SQL_CREATE_DAYS);
        sqLiteDatabase.execSQL(SQL_CREATE_TRIPS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ATRACTIONS);
        sqLiteDatabase.execSQL(SQL_DELETE_DAYS);
        sqLiteDatabase.execSQL(SQL_DELETE_TRIPS);
        onCreate(sqLiteDatabase);
    }
}
