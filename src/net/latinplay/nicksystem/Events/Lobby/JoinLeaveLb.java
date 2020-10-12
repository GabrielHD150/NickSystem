package net.latinplay.nicksystem.Events.Lobby;

import net.latinplay.nicksystem.Main;
import net.latinplay.nicksystem.Player.NickPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.IOException;
import java.sql.SQLException;

public class JoinLeaveLb implements Listener {

    private Main plugin;

    public JoinLeaveLb(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void PlayerLoginEvent(AsyncPlayerPreLoginEvent e) throws SQLException, IOException {
        plugin.getPlayerBase().createPlayer(e.getUniqueId());
    }

    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent e) {
        NickPlayer nickPlayer = plugin.getPlayerManager().getPlayer(e.getPlayer().getUniqueId());
        nickPlayer.setPlayer(e.getPlayer());

    }

    @EventHandler
    public void PlayerQuitEvent(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        plugin.getPlayerManager().removePlayer(p.getUniqueId());
    }

    private String c(String c) {
        return ChatColor.translateAlternateColorCodes('&', c);
    }

}
