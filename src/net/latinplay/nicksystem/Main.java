package net.latinplay.nicksystem;

import net.latinplay.nicksystem.Configurations.ConfigCreator;
import net.latinplay.nicksystem.Configurations.ConfigUtils;
import net.latinplay.nicksystem.Database.PlayerBase;
import net.latinplay.nicksystem.Events.EventsManager;
import net.latinplay.nicksystem.Utils.Managers.NickManager;
import net.latinplay.nicksystem.Utils.Managers.PlayerManager;
import net.latinplay.nicksystem.Utils.Managers.SkinManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main plugin;
    private ConfigUtils configUtils;
    private PlayerBase playerBase;
    private PlayerManager playerManager;
    private EventsManager eventsManager;
    private NickManager nickManager;
    private SkinManager skinManager;

    public Main() {
        this.skinManager = new SkinManager(this);
        this.nickManager = new NickManager(this);
        this.eventsManager = new EventsManager(this);
        this.playerManager = new PlayerManager(this);
        this.playerBase = new PlayerBase(this);
        this.configUtils = new ConfigUtils();
    }

    @Override
    public void onEnable() {
        this.plugin = this;
        ConfigCreator.get().setup(this, "Settings");
        this.eventsManager.loadEvents();
        this.getPlayerBase().loadMySQL();

        this.log("NickSystem", "Has been loaded " + this.getDescription().getVersion());
        super.onEnable();
    }
    
    public static Main getMain() {
        return plugin;
    }

    @Override
    public void onDisable() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission("nick.use")) {
                getPlayerManager().removePlayer(p.getUniqueId());
            }
        }

        super.onDisable();
    }

    public void log(String logger, String log) {
        Bukkit.getConsoleSender().sendMessage(c("&a&l" + logger + " &8|&e " + log));
    }

    public ConfigUtils getConfigUtils() {
        return configUtils;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public PlayerBase getPlayerBase() {
        return playerBase;
    }

    public NickManager getNickManager() {
        return nickManager;
    }

    public SkinManager getSkinManager() {
        return skinManager;
    }

    private String c(String c) {
        return ChatColor.translateAlternateColorCodes('&', c);
    }
}
