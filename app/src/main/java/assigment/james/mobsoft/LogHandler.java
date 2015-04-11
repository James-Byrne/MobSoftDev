package assigment.james.mobsoft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by james on 09/04/15.
 *
 * This class manages all the transactions between
 * the database and all other parts of the app.
 *
 * Functionality:
 * - Open the database for writing
 * - Close the database
 * - Create a new Log and write it to the database
 */
public class LogHandler {

    private SQLiteDatabase database;
    private DB_Handler dbHelper;
    private String[] columns = {
            DB_Handler.COLUMN_ID,
            DB_Handler.COLUMN_TITLE,
            DB_Handler.COLUMN_CONDITION,
            DB_Handler.COLUMN_OBJECTIVE,
            DB_Handler.COLUMN_ARROWS_SHOT,
            DB_Handler.COLUMN_REVIEW };

    public LogHandler(Context context){
        dbHelper = new DB_Handler(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        database.close();
    }

    public Log createLog(String title, String obj, String con, String arr, String rev){

        ContentValues values = new ContentValues();
        values.put(DB_Handler.COLUMN_TITLE, title);
        values.put(DB_Handler.COLUMN_OBJECTIVE, obj);
        values.put(DB_Handler.COLUMN_CONDITION, con);
        values.put(DB_Handler.COLUMN_ARROWS_SHOT, arr);
        values.put(DB_Handler.COLUMN_REVIEW, rev);

        long insertID = database.insert(DB_Handler.TABLE_LOGS, null, values);

        // Gets the Log with the above ID
        Cursor cursor =
                database.query(
                        DB_Handler.TABLE_LOGS,
                        columns,
                        DB_Handler.COLUMN_ID + " = " + insertID,
                        null,null,null,null);
        //moves to the most recent Log
        cursor.moveToFirst();

        //re-assemble the log retrieved
        Log log = assembleLog(cursor);

        cursor.close();
        return log;
    }

    public Boolean exists(Long id){

        Cursor cursor = database.query(
                DB_Handler.TABLE_LOGS,
                columns,
                DB_Handler.COLUMN_ID + " = " + id,
                null,null,null,null);

        Boolean r = cursor.getCount() > 0;
        cursor.close();
        return r;
    }

    public Boolean editLog(Log log){

        String filter = " _id = " + log.getID();

        ContentValues contentValues = new ContentValues();

        contentValues.put(DB_Handler.COLUMN_TITLE, log.getTitle());
        contentValues.put(DB_Handler.COLUMN_OBJECTIVE, log.getObjective());
        contentValues.put(DB_Handler.COLUMN_CONDITION, log.getCondition());
        contentValues.put(DB_Handler.COLUMN_ARROWS_SHOT, log.getArr_shot());
        contentValues.put(DB_Handler.COLUMN_REVIEW, log.getReview());

        database.update(
            DB_Handler.TABLE_LOGS,
            contentValues,
            filter,
            null);

        return exists(log.getID());
    }

    /**
     * Assembles the Log from the cursor
     * and returns the assembled log
     *
     * @param cursor
     * @return
     */
    public Log assembleLog(Cursor cursor){

        Log log = new Log();
        log.setID(cursor.getLong(0));
        log.setTitle(cursor.getString(1));
        log.setObjective(cursor.getString(2));
        log.setCondition(cursor.getString(3));
        log.setArr_shot(cursor.getString(4));
        log.setReview(cursor.getString(5));
        return log;
    }

    // Deletes a particular Log from the system
    public void deleteLog(Log log) {
        long id = log.getID();
        System.out.println("Log deleted with id: " + id);
        database.delete(DB_Handler.TABLE_LOGS, DB_Handler.COLUMN_ID
                + " = " + id, null);
    }

    /**
     * Returns a list of all logs that
     * the system has stored
     *
     * @return
     */
    public List<Log> getAllLogs() {
        List<Log> logs = new ArrayList<Log>();

        Cursor cursor = database.query(DB_Handler.TABLE_LOGS,
                columns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log log = assembleLog(cursor);
            logs.add(log);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return logs;
    }

}
