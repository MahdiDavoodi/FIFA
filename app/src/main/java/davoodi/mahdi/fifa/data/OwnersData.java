package davoodi.mahdi.fifa.data;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import davoodi.mahdi.fifa.components.League;
import davoodi.mahdi.fifa.components.Owner;


public class OwnersData extends SQLiteOpenHelper {


    private static final String DB_NAME = "owners-db";
    private static final int DB_VERSION = 1;
    public static final String TABLE_OWNERS = "owners";
    private static final String CMD_CREATE = "CREATE TABLE IF NOT EXISTS '" + TABLE_OWNERS + "' " + "( '"
            + Owner.KEY_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '"
            + Owner.KEY_NAME + "' TEXT, '"
            + Owner.KEY_PASSWORD + "' INTEGER, '"
            + Owner.KEY_WEALTH + "' INTEGER, '"
            + Owner.KEY_WIN + "' INTEGER, '"
            + Owner.KEY_LOSS + "' INTEGER, '"
            + Owner.KEY_DRAW + "' INTEGER, '"
            + Owner.KEY_CUPS + "' INTEGER"
            + ")";

    public OwnersData(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CMD_CREATE);
        Log.i("database", "Table '" + TABLE_OWNERS + "' created!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        // We should get backups from old data.
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_OWNERS);
        Log.i("database", "Table '" + TABLE_OWNERS + "' dropped!");
        onCreate(database);
        // We should restore database.
    }

    public void insertOwner(Owner owner) {
        // Now we work with our database.
        SQLiteDatabase database = getWritableDatabase();
        long insertID = database.insert(TABLE_OWNERS, null, owner.getContentValues());

        if (insertID == -1)
            Log.i("database", "Owner data insertion failed. (Season: " + owner.toString() + " ) ");
        else
            Log.i("database", "Owner data inserted with id: " + insertID);
        if (database.isOpen()) database.close();
        Log.i("database", "Owners database closed");
    }

    public void updateOwner(Owner owner) {
        SQLiteDatabase database = getWritableDatabase();
        int count = database.update(TABLE_OWNERS, owner.getContentValues(), Owner.KEY_ID + " = " + owner.getOwnerID(), null);
        if (count != 1) Log.e("OwnersData", "Error in update method");
        if (database.isOpen()) database.close();
    }


    public Owner getOwnerFromID(int ownerID) {
        SQLiteDatabase database = getReadableDatabase();
        Owner owner = null;
        Cursor cursor = database.rawQuery("SELECT * FROM '" + TABLE_OWNERS + "' WHERE " + Owner.KEY_ID + " = " + ownerID, null);
        if (cursor.moveToFirst()) {

            Log.i("OwnersData", "everything is good in getOwnerFromID");
            owner = new Owner(
                    cursor.getInt(cursor.getColumnIndex(Owner.KEY_ID)),
                    cursor.getString(cursor.getColumnIndex(Owner.KEY_NAME)),
                    cursor.getLong(cursor.getColumnIndex(Owner.KEY_PASSWORD)),
                    cursor.getLong(cursor.getColumnIndex(Owner.KEY_WEALTH)),
                    cursor.getLong(cursor.getColumnIndex(Owner.KEY_WIN)),
                    cursor.getLong(cursor.getColumnIndex(Owner.KEY_LOSS)),
                    cursor.getLong(cursor.getColumnIndex(Owner.KEY_DRAW)),
                    cursor.getInt(cursor.getColumnIndex(Owner.KEY_CUPS)));


        } else Log.e("OwnersData", "Problem in getOwnerFromID");
        cursor.close();
        if (database.isOpen()) database.close();
        return owner;
    }

    public ArrayList<Owner> getAllOwners() {
        SQLiteDatabase database = getReadableDatabase();
        ArrayList<Owner> owners = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM '" + TABLE_OWNERS + "'", null);
        if (cursor.moveToFirst()) {
            do {

                Owner owner = new Owner(
                        cursor.getInt(cursor.getColumnIndex(Owner.KEY_ID)),
                        cursor.getString(cursor.getColumnIndex(Owner.KEY_NAME)),
                        cursor.getLong(cursor.getColumnIndex(Owner.KEY_PASSWORD)),
                        cursor.getLong(cursor.getColumnIndex(Owner.KEY_WEALTH)),
                        cursor.getLong(cursor.getColumnIndex(Owner.KEY_WIN)),
                        cursor.getLong(cursor.getColumnIndex(Owner.KEY_LOSS)),
                        cursor.getLong(cursor.getColumnIndex(Owner.KEY_DRAW)),
                        cursor.getInt(cursor.getColumnIndex(Owner.KEY_CUPS)));
                owners.add(owner);
            } while (cursor.moveToNext());
        }
        cursor.close();
        if (database.isOpen()) database.close();
        return owners;
    }
}
