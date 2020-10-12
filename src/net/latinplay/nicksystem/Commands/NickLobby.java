package net.latinplay.nicksystem.Commands;

import net.latinplay.nicksystem.Main;
import net.latinplay.nicksystem.Player.NickPlayer;
import net.latinplay.nicksystem.Skins.SkinData;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.Bukkit;

public class NickLobby implements CommandExecutor {

    private Main plugin;

    public NickLobby(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Configuration config = plugin.getConfigUtils().getConfig(plugin, "Settings");

        if (cmd.getName().equalsIgnoreCase("nick") && sender.hasPermission("nick.use")) {
            if (args.length < 1) {
                Player p = (Player) sender;
                NickPlayer nickPlayer = plugin.getPlayerManager().getPlayer(p.getUniqueId());


                if (!nickPlayer.isHidden()) {
                    List<String> skins = Main.getMain().getConfigUtils().getConfig(plugin, "Settings").getStringList("SkinsNames");
                    String skin = skins.get(ThreadLocalRandom.current().nextInt(skins.size()));
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "bungee skin set "+p.getName()+" "+skin);
                    plugin.getSkinManager().generateRandom(skinData -> {
                        nickPlayer.setSkinData(skinData);
                        nickPlayer.setHidden(true);
                        plugin.getPlayerBase().savePlayer(p.getUniqueId());

                        p.sendMessage(c(config.getString("Messages.nicked")));
                        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                            p.kickPlayer(c(config.getString("Messages.nicked")));
                        }, 30L);
                    });
                } else {
                    nickPlayer.setHidden(false);
                    GameProfile gameProfile = ((CraftPlayer) p).getProfile();
                    PropertyMap pm = gameProfile.getProperties();
                    Property property = pm.get("textures").iterator().next();

                    nickPlayer.setSkinData(new SkinData(p.getName(), property.getValue(), property.getSignature()));
                    plugin.getPlayerBase().savePlayer(p.getUniqueId());
                    p.sendMessage(c(config.getString("Messages.removed")));
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "bungee skin clear "+p.getName());
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        p.kickPlayer(c(config.getString("Messages.removed")));
                    }, 30L);
                }

                return true;
            }

            Player p = (Player) sender;
            String nick = args[0];

            if (nick.length() < 1 || nick.length() > 16) {
                p.sendMessage(c("&cEl nick debe contener entre 1 y 16 caracteres"));
                return false;
            }

            NickPlayer nickPlayer = plugin.getPlayerManager().getPlayer(p.getUniqueId());

            if (!nickPlayer.isHidden()) {
                plugin.getSkinManager().generateNormal(nick, skinData -> {
                    nickPlayer.setSkinData(skinData);
                    nickPlayer.setHidden(true);
                    plugin.getPlayerBase().savePlayer(p.getUniqueId());

                    p.sendMessage(c(config.getString("Messages.nicked")));
                });
            } else {
                nickPlayer.setHidden(false);
                GameProfile gameProfile = ((CraftPlayer) p).getProfile();
                PropertyMap pm = gameProfile.getProperties();
                Property property = pm.get("textures").iterator().next();

                nickPlayer.setSkinData(new SkinData(p.getName(), property.getValue(), property.getSignature()));
                plugin.getPlayerBase().savePlayer(p.getUniqueId());
                p.sendMessage(c(config.getString("Messages.removed")));
            }

        }
        return false;
    }

    private String c(String c) {
        return ChatColor.translateAlternateColorCodes('&', c);
    }

}
