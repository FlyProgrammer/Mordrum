package com.mordrum.moverseer;

/**
 * Created with IntelliJ IDEA.
 * User: Costco
 * Date: 01/06/13
 * Time: 11:38 PM
 */
public class PlayerRecord {
    private Boolean banState = false;
    private String banReason = "You have been banned";
    private int homeX = 0;
    private int homeY = 0;
    private int homeZ = 0;
    private int homeWorld = -1;

    public PlayerRecord() {

    }

    public PlayerRecord(Boolean banState, String banReason, int homeX, int homeY, int homeZ, int homeWorld) {
        this.banState = banState;
        this.banReason = banReason;
        this.homeX = homeX;
        this.homeY = homeY;
        this.homeZ = homeZ;
        this.homeWorld = homeWorld;
    }

    public Boolean getBanState() {
        return banState;
    }

    public void setBanState(Boolean banState) {
        this.banState = banState;
    }

    public String getBanReason() {
        return banReason;
    }

    public void setBanReason(String banReason) {
        this.banReason = banReason;
    }

    public int getHomeX() {
        return homeX;
    }

    public void setHomeX(int homeX) {
        this.homeX = homeX;
    }

    public int getHomeY() {
        return homeY;
    }

    public void setHomeY(int homeY) {
        this.homeY = homeY;
    }

    public int getHomeZ() {
        return homeZ;
    }

    public void setHomeZ(int homeZ) {
        this.homeZ = homeZ;
    }

    public int getHomeWorld() {
        return homeWorld;
    }

    public void setHomeWorld(int homeWorld) {
        this.homeWorld = homeWorld;
    }

    /*
    Returns a string of SQL ready values
     */
    public String getSQLReadyValues() {
        StringBuilder sb = new StringBuilder(); //Create a new stringbuilder to make this process easier and faster
        //Below all of the values for the PlayerRecord are appended to the stringbuilder
        sb.append("'" + banState.toString() + "'");
        sb.append("," + "'" + banReason + "'");
        sb.append("," + homeX);
        sb.append("," + homeY);
        sb.append("," + homeZ);
        sb.append("," + homeWorld);
        return sb.toString(); //Return the values
    }
}
