package utcc.som.cken.tae.healthrecord;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Admin on 10/31/2015 AD.
 */
public class RecordTABLE {

    //Explicit
    private MyOpenHelper objMyOpenHelper;
    private SQLiteDatabase writeSqLiteDatabase, readSqLiteDatabase;

    public static final String RECORD_TABLE = "recordTABLE";
    public static final String COLUMN_ID_USER = "_id";
    public static final String COLUMN_SLEEP = "Sleep";
    public static final String COLUMN_BREAKFAST = "Breakfast";
    public static final String COLUMN_LUNCH = "Lunch";
    public static final String COLUMN_DINNER = "Dinner";
    public static final String COLUMN_TYPEEXERCISE = "TypeExercise";
    public static final String COLUMN_TIMEEXERCISE = "TimeExercise";
    public static final String COLUMN_DRINKWATER = "DrinkWater";
    public static final String COLUMN_WEIGHT = "Weight";
    public static final String COLUMN_NAME_USER = "NameUser";

    public RecordTABLE(Context context) {

        objMyOpenHelper = new MyOpenHelper(context); // เชื่อมต่อ
        writeSqLiteDatabase = objMyOpenHelper.getWritableDatabase();
        readSqLiteDatabase = objMyOpenHelper.getReadableDatabase();

    } // Constructor

    public long addNewRecord(String strSleep, String strBreakfast, String strLunch, String strDinner, String strTypeExercise, String strTimeExercise, String strDrinkWater, String strWeight, String strNameUser) {

        ContentValues objContentValues = new ContentValues();
        objContentValues.put(COLUMN_SLEEP, strSleep);
        objContentValues.put(COLUMN_BREAKFAST, strBreakfast);
        objContentValues.put(COLUMN_LUNCH, strLunch);
        objContentValues.put(COLUMN_DINNER, strDinner);
        objContentValues.put(COLUMN_TYPEEXERCISE, strTypeExercise);
        objContentValues.put(COLUMN_TIMEEXERCISE, strTimeExercise);
        objContentValues.put(COLUMN_DRINKWATER, strDrinkWater);
        objContentValues.put(COLUMN_WEIGHT, strWeight);
        objContentValues.put(COLUMN_NAME_USER, strNameUser);
        return writeSqLiteDatabase.insert(RECORD_TABLE, null, objContentValues);

    } // addNewRecord

} // Main class
