package net.latinplay.nicksystem.Events;

import net.latinplay.nicksystem.Main;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;

public class Example implements Listener {

    private Main plugin;

    public Example(Main plugin) {
        this.plugin = plugin;
    }

    private String c(String c) {
        return ChatColor.translateAlternateColorCodes('&', c);
    }

}
