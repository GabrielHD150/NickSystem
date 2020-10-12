package net.latinplay.nicksystem.Events.Bungee;

import ak.CookLoco.SkyWars.events.ArenaJoinEvent;
import ak.CookLoco.SkyWars.kit.Kit;
import ak.CookLoco.SkyWars.kit.KitManager;
import ak.CookLoco.SkyWars.player.SkyPlayer;
import com.nametagedit.plugin.NametagEdit;
import net.latinplay.nicksystem.Main;
import net.latinplay.nicksystem.Player.NickPlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.IOException;
import java.sql.SQLException;

public class JoinLeaveBg implements Listener {

    private Main plugin;

    public JoinLeaveBg(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void PlayerLoginEvent(AsyncPlayerPreLoginEvent e) throws SQLException, IOException {
        plugin.getPlayerBase().createPlayer(e.getUniqueId());
    }

    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (p.hasPermission("nick.use")) {
            NickPlayer nickPlayer = plugin.getPlayerManager().getPlayer(p.getUniqueId());
            nickPlayer.setPlayer(p);
            if (nickPlayer.isHidden()) {
                plugin.getNickManager().hidePlayer(p);
                com.hordern123.title.Main.BLOCK_MSG.add(p);
                NametagEdit.getApi().setPrefix(p, "&a»&2&lVIP &f");
            } else {
                NametagEdit.getApi().clearNametag(p);
                NametagEdit.getApi().reloadNametag(p);
                com.hordern123.title.Main.BLOCK_MSG.remove(p);
            }
        }
    }
    
    @EventHandler
    public void onJoinArena(ArenaJoinEvent e) {
        SkyPlayer player = e.getPlayer();
        Player p = player.getPlayer();
        if (p.hasPermission("nick.use")) {
            NickPlayer nickPlayer = plugin.getPlayerManager().getPlayer(p.getUniqueId());
            if (nickPlayer.isHidden()) {
                for(Kit kit : KitManager.getKits()) {
                    player.addKit(kit);
                }
            }
        }
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
