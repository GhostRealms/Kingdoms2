package net.ghostrealms.kingdoms.cmds.kingdoms;

import org.bukkit.entity.Player;

import com.imdeity.deityapi.api.DeityCommandReceiver;

import net.ghostrealms.kingdoms.main.KingdomsMain;
import net.ghostrealms.kingdoms.main.KingdomsMessageHelper;
import net.ghostrealms.kingdoms.obj.KingdomsHelper;

public class KingdomsMapCommand extends DeityCommandReceiver {
    
    @Override
    public boolean onPlayerRunCommand(Player player, String[] args) {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("help")) {
                KingdomsHelper.getMapHelp(player);
            } else if (args[0].equalsIgnoreCase("on")) {
                if (!KingdomsMain.plugin.residentsInMapMode.contains(player.getName())) {
                    KingdomsMain.plugin.residentsInMapMode.add(player.getName());
                    KingdomsMain.plugin.chat.sendPlayerMessage(player, KingdomsMessageHelper.CMD_KINGDOMS_MAP_ON);
                }
            } else {
                if (KingdomsMain.plugin.residentsInMapMode.contains(player.getName())) {
                    KingdomsMain.plugin.residentsInMapMode.remove(player.getName());
                    KingdomsMain.plugin.chat.sendPlayerMessage(player, KingdomsMessageHelper.CMD_KINGDOMS_MAP_OFF);
                }
            }
            return true;
        } else {
            KingdomsHelper.sendMap(player, player.getLocation());
            return true;
        }
    }
    
    @Override
    public boolean onConsoleRunCommand(String[] args) {
        return false;
    }
    
}
