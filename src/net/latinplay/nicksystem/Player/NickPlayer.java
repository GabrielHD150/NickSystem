package net.latinplay.nicksystem.Player;

import net.latinplay.nicksystem.Skins.SkinData;
import org.bukkit.entity.Player;

import java.util.UUID;

public class NickPlayer {

    private UUID uuid;
    private Player player;
    private SkinData skinData;
    private boolean hidden;

    public NickPlayer(UUID uuid, SkinData skinData, boolean hidden) {
        this.uuid = uuid;
        this.hidden = hidden;
        this.skinData = skinData;
    }
    
    public boolean isHidden() {
        return this.hidden;
    }
    
    public SkinData getSkinData() {
        return this.skinData;
    }
    
    public void setSkinData(SkinData data) {
        this.skinData = data;
    }

    public void setHidden(boolean boo) {
        this.hidden = boo;
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public void setPlayer(Player p) {
        this.player = p;
    }
    
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
    
    public UUID getUuid() {
        return this.uuid;
    }
}
