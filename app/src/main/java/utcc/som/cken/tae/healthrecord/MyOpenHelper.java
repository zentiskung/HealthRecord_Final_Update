package utcc.som.cken.tae.healthrecord;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Admin on 10/8/2015 AD.
 */
public class MyOpenHelper extends SQLiteOpenHelper{

    // Explicit
    private static final String DATABASE_NAME ="Health.db";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_USER_TABLE = "create table userTABLE (_id integer primary key, User text, Password text, Name text, Age text, Sex text, Weight text, Height text, Email text);";
    private static final String CREATE_RECORD_TABLE = "create table recordTABLE (_id integer primary key,Sleep text, Breakfast text, Lunch text, Dinner text, TypeExercise text, TimeExercise text, DrinkWater text, Weight text, NameUser text);";


    public MyOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    } // Constructor

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
        sqLiteDatabase.execSQL(CREATE_RECORD_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
} //Main Class
