package net.ghostrealms.kingdoms.cmds.town;

import org.bukkit.entity.Player;

import com.imdeity.deityapi.api.DeityCommandReceiver;

import net.ghostrealms.kingdoms.main.KingdomsMain;
import net.ghostrealms.kingdoms.main.KingdomsMessageHelper;
import net.ghostrealms.kingdoms.obj.KingdomsManager;
import net.ghostrealms.kingdoms.obj.Resident;
import net.ghostrealms.kingdoms.obj.Town;

public class TownDeleteCommand extends DeityCommandReceiver {
    
    @Override
    public boolean onPlayerRunCommand(Player player, String[] args) {
        Resident resident = KingdomsManager.getResident(player.getName());
        if (resident == null) { return false; }
        if (!resident.hasTown()) {
            KingdomsMain.plugin.chat.sendPlayerMessage(player, KingdomsMessageHelper.CMD_FAIL_NOT_IN_TOWN);
            return true;
        }
        if (!resident.isKing() && !resident.isMayor()) {
            KingdomsMain.plugin.chat.sendPlayerMessage(player, KingdomsMessageHelper.CMD_FAIL_NOT_TOWN_DUKE);
            return true;
        }
        Town town = resident.getTown();
        KingdomsManager.removeTown(town);
        KingdomsMain.plugin.chat.sendPlayerMessage(player, KingdomsMessageHelper.CMD_TOWN_DELETE_PLAYER);
        return true;
    }
    
    @Override
    public boolean onConsoleRunCommand(String[] args) {
        if (args.length > 0) {
            Town town = KingdomsManager.getTown(args[0]);
            if (town != null) {
                KingdomsManager.removeTown(town);
                KingdomsMain.plugin.chat.out(KingdomsMessageHelper.CMD_TOWN_DELETE_CONSOLE.replaceAll("%s", args[0]));
                return true;
            }
        }
        return false;
    }
}
