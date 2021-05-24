package davoodi.mahdi.fifa.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import davoodi.mahdi.fifa.components.Transfer;


public class TransfersData extends SQLiteOpenHelper {

    private static final String DB_NAME = "transfers-db";
    private static final int DB_VERSION = 1;
    public static final String TABLE_TRANSFERS = "transfers";
    private static final String CMD_CREATE = "CREATE TABLE IF NOT EXISTS '" + TABLE_TRANSFERS + "' " + "( '"
            + Transfer.KEY_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '"
            + Transfer.KEY_FROM + "' INTEGER, '"
            + Transfer.KEY_TO + "' INTEGER, '"
            + Transfer.KEY_PLAYER + "' TEXT, '"
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

    public void insertTransfer(Transfer transfer) {

        // Now we work with our database.
        SQLiteDatabase database = getWritableDatabase();
        // Let SQLite set id.
        ContentValues values = transfer.getContentValues();
        values.remove(Transfer.KEY_ID);
        //
        long insertID = database.insert(TABLE_TRANSFERS, null, values);

        if (insertID == -1)
            Log.i("database", "Transfer data insertion failed. (Transfer: " + transfer.getPlayerName() + " ) ");
        else
            Log.i("database", "Transfer data inserted with id: " + insertID);
        // Better to not close it.
    }

    public ArrayList<Transfer> getAllTransfers() {
        SQLiteDatabase database = getReadableDatabase();
        ArrayList<Transfer> transfers = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM '" + TABLE_TRANSFERS
                + "' ORDER BY " + Transfer.KEY_ID + " DESC ", null);
        if (cursor.moveToFirst()) {
            do {

                Transfer transfer = new Transfer(cursor.getInt(cursor.getColumnIndex(Transfer.KEY_ID)),
                        cursor.getInt(cursor.getColumnIndex(Transfer.KEY_FROM)),
                        cursor.getInt(cursor.getColumnIndex(Transfer.KEY_TO)),
                        cursor.getString(cursor.getColumnIndex(Transfer.KEY_PLAYER)),
                        cursor.getLong(cursor.getColumnIndex(Transfer.KEY_PRICE)));
                transfers.add(transfer);
            } while (cursor.moveToNext());
        }
        cursor.close();
        if (database.isOpen()) database.close();
        return transfers;
    }
}
