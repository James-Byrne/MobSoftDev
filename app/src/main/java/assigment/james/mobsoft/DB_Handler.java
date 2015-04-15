package assigment.james.mobsoft;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Blob;

/**
 * Created by james on 09/04/15.
 *
 */
public class DB_Handler extends SQLiteOpenHelper {


    // General database variables
    private SQLiteDatabase database;

    public static final String DATABASE_NAME = "assignment_james.db";
    public static final String TABLE_LOGS = "training_log";
    public static final String COLUMN_ID = "_id";
    public static final int DATABASE_VERSION = 1;

    // Fields for the database
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_OBJECTIVE ="objective";
    public static final String COLUMN_CONDITION = "condition";
    public static final String COLUMN_ARROWS_SHOT = "arrows_shot";
    public static final String COLUMN_REVIEW = "review";
    public static final String COLUMN_IMAGE = "image";


    private static final String DB_CREATE =
            "create table " + TABLE_LOGS + "("
                    + COLUMN_ID + " integer primary key autoincrement, "
                    + COLUMN_TITLE + ", "
                    + COLUMN_OBJECTIVE + ", "
                    + COLUMN_CONDITION + ", "
                    + COLUMN_ARROWS_SHOT + ", "
                    + COLUMN_REVIEW + ", "
                    + COLUMN_IMAGE
                    +");"
            ;

    public DB_Handler(Context ctx){
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DB_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

    }

}
