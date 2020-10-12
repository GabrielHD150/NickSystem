package net.latinplay.nicksystem.Events;

import net.latinplay.nicksystem.Commands.NickLobby;
import net.latinplay.nicksystem.Events.Bungee.JoinLeaveBg;
import net.latinplay.nicksystem.Events.Lobby.JoinLeaveLb;
import net.latinplay.nicksystem.Main;
import net.latinplay.nicksystem.Tasks.ActionBarBC;
import org.bukkit.configuration.Configuration;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public class EventsManager {

    private Main plugin;

    public EventsManager(Main plugin) {
        this.plugin = plugin;
    }

    public void loadEvents() {
        Configuration config = plugin.getConfigUtils().getConfig(plugin, "Settings");
        plugin.log("NickSystem", "Loading Events..");

        if (config.getString("Mode").equalsIgnoreCase("Lobby")) {
            plugin.getCommand("nick").setExecutor(new NickLobby(plugin));
            registerListener(new JoinLeaveLb(plugin));
            new ActionBarBC(plugin).runTaskTimerAsynchronously(plugin, 0, 10);
        } else if (config.getString("Mode").equalsIgnoreCase("Arena")) {
            registerListener(new JoinLeaveBg(plugin));
        }

        plugin.log("NickSystem", "Events has been loaded");
    }

    // Loading Listeners
    public void registerListener(Listener listener) {
        plugin.log("NickSystem", "&5-&e Loaded listener &a" + listener.getClass().getSimpleName());
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }

    public void unregisterListener(Listener listener) {
        plugin.log("NickSystem", "&5-&e Unloading listener &a" + listener.getClass().getSimpleName());
        HandlerList.unregisterAll(listener);
    }

}
