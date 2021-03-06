package net.ghostrealms.kingdoms.cmds.town;

import org.bukkit.entity.Player;

import com.imdeity.deityapi.api.DeityCommandReceiver;

import net.ghostrealms.kingdoms.main.KingdomsMain;
import net.ghostrealms.kingdoms.main.KingdomsMessageHelper;
import net.ghostrealms.kingdoms.obj.KingdomsManager;
import net.ghostrealms.kingdoms.obj.Resident;
import net.ghostrealms.kingdoms.obj.Town;

public class TownKickCommand extends DeityCommandReceiver {
    
    @Override
    public boolean onPlayerRunCommand(Player player, String[] args) {
        Resident resident = KingdomsManager.getResident(player.getName());
        if (resident == null) { return false; }
        if (!resident.hasTown()) {
            KingdomsMain.plugin.chat.sendPlayerMessage(player, KingdomsMessageHelper.CMD_FAIL_NOT_IN_TOWN);
            return true;
        }
        if (!resident.isKing() && !resident.isMayor() && !resident.isSeniorAssistant()) {
            KingdomsMain.plugin.chat.sendPlayerMessage(player, KingdomsMessageHelper.CMD_FAIL_NOT_TOWN_STAFF);
            return true;
        }
        Town town = resident.getTown();
        if (args.length == 1) {
            Resident newResident = KingdomsManager.getResident(args[0]);
            if (newResident == null) {
                KingdomsMain.plugin.chat.sendPlayerMessage(player,
                        String.format(KingdomsMessageHelper.CMD_FAIL_CANNOT_FIND_RESIDENT, args[0]));
                return true;
            }
            if (!newResident.hasTown() || !newResident.getTown().getName().equalsIgnoreCase(town.getName())
                    || newResident.getName().equalsIgnoreCase(resident.getName())) {
                KingdomsMain.plugin.chat.sendPlayerMessage(player,
                        String.format(KingdomsMessageHelper.CMD_FAIL_TOWN_KICK_INVALID_PLAYER, newResident.getName()));
                return true;
            }
            town.removeResident(newResident);
            KingdomsMain.plugin.chat.sendPlayerMessage(player,
                    String.format(KingdomsMessageHelper.CMD_TOWN_KICK_PLAYER, newResident.getName()));
            return true;
        }
        return false;
    }
    
    @Override
    public boolean onConsoleRunCommand(String[] args) {
        return false;
    }
}
