package davoodi.mahdi.fifa.components;

import android.content.ContentValues;

public class Transfer {
    public static final String KEY_ID = "transferID";
    public static final String KEY_FROM = "fromClubID";
    public static final String KEY_TO = "toClubID";
    public static final String KEY_PLAYER = "playerName";
    public static final String KEY_PRICE = "price";


    private int transferID, fromClubID, toClubID;
    private long price;
    private String playerName;

    public Transfer(int transferID, int fromClubID, int toClubID, String playerName, long price) {
        this.transferID = transferID;
        this.fromClubID = fromClubID;
        this.toClubID = toClubID;
        this.price = price;
        this.playerName = playerName;
    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(KEY_ID, getTransferID());
        values.put(KEY_FROM, getFromClubID());
        values.put(KEY_TO, getToClubID());
        values.put(KEY_PLAYER, getPlayerName());
        values.put(KEY_PRICE, getPrice());
        return values;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getTransferID() {
        return transferID;
    }

    public void setTransferID(int transferID) {
        this.transferID = transferID;
    }

    public int getFromClubID() {
        return fromClubID;
    }

    public void setFromClubID(int fromClubID) {
        this.fromClubID = fromClubID;
    }

    public int getToClubID() {
        return toClubID;
    }

    public void setToClubID(int toClubID) {
        this.toClubID = toClubID;
    }


    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }
}
