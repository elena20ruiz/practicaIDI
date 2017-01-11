package com.example.pr_idi.mydatabaseexample;

/**
 * MySQLiteHelper
 * Created by pr_idi on 10/11/16.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_FILMS = "films";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_COUNTRY = "country";
    public static final String COLUMN_YEAR_RELEASE = "year_release";
    public static final String COLUMN_DIRECTOR = "director";
    public static final String COLUMN_PROTAGONIST = "protagonist";
    public static final String COLUMN_CRITICS_RATE = "critics_rate";
    public static final String COLUMN_ID_THEME = "thematic";

    private static final String DATABASE_NAME = "films.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table " + TABLE_FILMS + "( "
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TITLE + " text not null, "
            + COLUMN_COUNTRY + " text not null, "
            + COLUMN_YEAR_RELEASE + " integer not null, "
            + COLUMN_DIRECTOR + " text not null, "
            + COLUMN_PROTAGONIST + " text not null, "
            + COLUMN_CRITICS_RATE + " integer, "
            + COLUMN_ID_THEME + " integer"
            + ");";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);

        database.execSQL("INSERT INTO " + TABLE_FILMS + "(title,country,year_release,director,protagonist,critics_rate,thematic) VALUES ('Deadpool','Estats Units',2016,'Tim Miller','Ryan Reynolds',8,2)") ;
        database.execSQL("INSERT INTO " + TABLE_FILMS + "(title,country,year_release,director,protagonist,critics_rate,thematic) VALUES ('Zootrópolis','Estats Units',2016,'Byron Howard','Ginnifer Goodwin',6,6)") ;
        database.execSQL("INSERT INTO " + TABLE_FILMS + "(title,country,year_release,director,protagonist,critics_rate,thematic) VALUES ('Zoolander 2','Estats Units',2016,'Ben Stiller','Ben Stiller',3,1)") ;
        database.execSQL("INSERT INTO " + TABLE_FILMS + "(title,country,year_release,director,protagonist,critics_rate,thematic) VALUES ('The Martian 2','Estats Units',2015,'Ridley Scott','Matt Damon',9,5)") ;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILMS);
        onCreate(db);
    }

}