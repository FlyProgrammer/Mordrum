package com.mordrum.mciv.api;

import com.mordrum.mciv.util.TaxType;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Costco
 * Date: 29/06/13
 * Time: 8:38 PM
 */
public class Town {

    private final int townID;

    private String motd = "Set the MOTD with /mayor set motd";
    private TaxType taxType = TaxType.FLAT;
    private Integer taxAmount = 0;
    private ChatColor primaryColor = ChatColor.WHITE;
    private ChatColor secondaryColor = ChatColor.WHITE;
    private String mayorName;
    private String name = "Default Town Name";
    private List<String> residents = new ArrayList<>();
    private int nationID;

    public Town(int townID, String motd, TaxType taxType, Integer taxAmount, ChatColor primaryColor, ChatColor secondaryColor, String mayorName, String name, List<String> residents, int nationID) {
        this.townID = townID;
        this.motd = motd;
        this.taxType = taxType;
        this.taxAmount = taxAmount;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        this.mayorName = mayorName;
        this.name = name;
        this.residents = residents;
        this.nationID = nationID;
    }

    public int getNationID() {
        return nationID;
    }

    public void setNationID(final int nationID) {
        this.nationID = nationID;
    }

    public List<String> getResidents() {
        return residents;
    }

    public void setResidents(final List<String> residents) {
        this.residents = residents;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getMayorName() {
        return mayorName;
    }

    public void setMayorName(final String mayorName) {
        this.mayorName = mayorName;
    }

    public ChatColor getSecondaryColor() {
        return secondaryColor;
    }

    public void setSecondaryColor(final ChatColor secondaryColor) {
        this.secondaryColor = secondaryColor;
    }

    public ChatColor getPrimaryColor() {
        return primaryColor;
    }

    public void setPrimaryColor(final ChatColor primaryColor) {
        this.primaryColor = primaryColor;
    }

    public Integer getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(final Integer taxAmount) {
        this.taxAmount = taxAmount;
    }

    public TaxType getTaxType() {
        return taxType;
    }

    public void setTaxType(final TaxType taxType) {
        this.taxType = taxType;
    }

    public String getMotd() {
        return motd;
    }

    public void setMotd(final String motd) {
        this.motd = motd;
    }
}
