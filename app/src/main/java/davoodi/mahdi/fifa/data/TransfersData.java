package davoodi.mahdi.fifa.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import davoodi.mahdi.fifa.components.Transfer;


public class TransfersData extends SQLiteOpenHelper {

    private static final String DB_NAME = "transfers-db";
    private static final int DB_VERSION = 1;
    public static final String TABLE_TRANSFERS = "transfers";
    private static final String CMD_CREATE = "CREATE TABLE IF NOT EXISTS '" + TABLE_TRANSFERS + "' " + "( '"
            + Transfer.KEY_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '"
            + Transfer.KEY_FROM + "' INTEGER, '"
            + Transfer.KEY_TO + "' INTEGER, '"
            + Transfer.KEY_PLAYER + "' INTEGER, '"
            + Transfer.KEY_PRICE + "' INTEGER"
            + ")";

    public TransfersData(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CMD_CREATE);
        Log.i("database", "Table '" + TABLE_TRANSFERS + "' created!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        // We should get backups from old data.
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSFERS);
        Log.i("database", "Table '" + TABLE_TRANSFERS + "' dropped!");
        onCreate(database);
        // We should restore database.
    }
}
