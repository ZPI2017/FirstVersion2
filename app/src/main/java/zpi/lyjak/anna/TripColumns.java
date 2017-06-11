package zpi.lyjak.anna;

import android.provider.BaseColumns;

/**
 * Created by Anna Łyjak on 12.06.2017.
 */

public class TripColumns implements BaseColumns {
    public static final String TABLE_NAME = "Trips";
    public static final String COLUMN_NAME_ID = "id_trip";
    public static final String COLUMN_NAME_START = "start";
    public static final String COLUMN_NAME_END = "end";
    public static final String COLUMN_NAME_ATTRACTION = "id_main_attraction";
    public static final String COLUMN_NAME_DAYS = "days";
    public static final String COLUMN_NAME_ENDED = "isEnded";
}
