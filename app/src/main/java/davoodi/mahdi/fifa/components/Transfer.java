package davoodi.mahdi.fifa.components;

public class Transfer {
    public static final String KEY_ID = "transferID";
    public static final String KEY_FROM = "fromClubID";
    public static final String KEY_TO = "toClubID";
    public static final String KEY_PLAYER = "playerID";
    public static final String KEY_PRICE = "price";


    private int transferID, fromClubID, toClubID, playerID;
    private long price;

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

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }
}
