package net.latinplay.nicksystem.Tasks;

import net.latinplay.nicksystem.Main;
import net.latinplay.nicksystem.Player.NickPlayer;
import net.latinplay.nicksystem.Utils.Packets;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.scheduler.BukkitRunnable;

public class ActionBarBC extends BukkitRunnable {

    private Main plugin;

    public ActionBarBC(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        Configuration config = plugin.getConfigUtils().getConfig(plugin, "Settings");

        for (NickPlayer nickPlayer : plugin.getPlayerManager().getValues()) {
            if (nickPlayer.isHidden()) {
                Packets.sendActionBar(nickPlayer.getPlayer(), c(config.getString("Messages.actionBar")));
            }
        }

    }

    private String c(String c) {
        return ChatColor.translateAlternateColorCodes('&', c);
    }

}
