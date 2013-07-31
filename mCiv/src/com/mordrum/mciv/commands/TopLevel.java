package com.mordrum.mciv.commands;

import com.mordrum.mciv.Main;
import com.sun.org.glassfish.gmbal.Description;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import se.ranzdo.bukkit.methodcommand.Arg;
import se.ranzdo.bukkit.methodcommand.Command;

/**
 * Created with IntelliJ IDEA.
 * User: Costco
 * Date: 31/07/13
 * Time: 7:16 PM
 */
public class TopLevel {

    private final Main plugin;
    private String[] helpArray = {"reload - Reloads the configuration",
                                    "stats - Check statistics",
                                    "rmtown - Remove a town",
                                    "rmprovince - Remove a province",
                                    "addtown - Add a town",
                                    "addprovince - Add a province",
                                    "settown - Set a town property",
                                    "setprovince - Set a province property",
                                    "refresh - Refresh all caches"
                                    };

    public TopLevel(Main instance) {
        this.plugin = instance;
    }

    @Command(identifier = "mciv", description = "mCiv administration command")
    public void TopLevelCommand(Player sender) {
        sender.sendMessage("mCiv version " + plugin.getDescription().getVersion());
        sender.sendMessage("Type /mciv help for a list of subcommands");
    }

    @Command(identifier = "mciv help", description = "Help command")
    public void HelpCommand(Player sender) {
        sender.sendMessage(helpArray);
    }

    @Command(identifier = "mciv stats", description = "Check statistics")
    public void StatsCommand(Player sender) {
        //TODO add stats command logic
    }

    @Command(identifier = "mciv rmtown", description = "Remove a town")
    public void RemoveTownCommand(Player sender, @Arg(name = "town") String townName) {
        //TODO add remove town command logic
    }

    @Command(identifier = "mciv rmprovince", description = "Remove a province")
    public void RemoveProvinceCommand(Player sender, @Arg(name = "province") String provinceName) {
        //TODO add remove province command logic
    }

    @Command(identifier = "mciv addtown", description = "Add a town")
    public void AddTownCommand(Player sender, @Arg(name = "town") String townName) {
        //TODO add add town command logic
    }

    @Command(identifier = "mciv addprovince", description = "Add a province")
    public void AddProvinceCommand(Player sender, @Arg(name = "province") String provinceName) {
        //TODO add add province command logic
    }

    @Command(identifier = "mciv settown", description = "Set a town property")
    public void SetTownCommand(Player sender, @Arg(name = "town") String townName, @Arg(name = "property") String property, @Arg(name = "value") String value) {
        //TODO add set town command logic
    }

    @Command(identifier = "mciv ", description = "Set a province property")
    public void SetProvinceCommand(Player sender, @Arg(name = "province") String province, @Arg(name = "property") String property, @Arg(name = "value") String value) {
        //TODO add set province command logic
    }

    @Command(identifier = "mciv refresh", description = "Refreshes all caches")
    public void RefreshCommand(Player sender) {
        //TODO add refresh command logic
    }
}