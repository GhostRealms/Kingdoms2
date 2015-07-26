package net.ghostrealms.kingdoms.cmds.town;

import org.bukkit.entity.Player;

import com.imdeity.deityapi.DeityAPI;
import com.imdeity.deityapi.api.DeityCommandReceiver;

import net.ghostrealms.kingdoms.main.KingdomsMain;
import net.ghostrealms.kingdoms.obj.KingdomsManager;

public class TownListCommand extends DeityCommandReceiver {
    
    @Override
    public boolean onPlayerRunCommand(Player player, String[] args) {
        KingdomsMain.plugin.chat.sendPlayerMessageNoHeader(player, "Current Towns: ");
        KingdomsMain.plugin.chat.sendPlayerMessageNoHeader(player, DeityAPI.getAPI().getUtilAPI().getStringUtils().join(
            KingdomsManager.getSortedTownNames(), ", "));
        return true;
    }
    
    @Override
    public boolean onConsoleRunCommand(String[] args) {
        KingdomsMain.plugin.chat.out("Current Towns: ");
        KingdomsMain.plugin.chat.out(DeityAPI.getAPI().getUtilAPI().getStringUtils().join(KingdomsManager.getSortedTownNames(), ", "));
        return true;
    }
    
}
