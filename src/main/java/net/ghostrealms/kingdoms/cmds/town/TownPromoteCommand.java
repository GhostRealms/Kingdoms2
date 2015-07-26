package net.ghostrealms.kingdoms.cmds.town;

import org.bukkit.entity.Player;

import com.imdeity.deityapi.api.DeityCommandReceiver;

import net.ghostrealms.kingdoms.main.KingdomsMain;
import net.ghostrealms.kingdoms.main.KingdomsMessageHelper;
import net.ghostrealms.kingdoms.obj.KingdomsManager;
import net.ghostrealms.kingdoms.obj.Resident;
import net.ghostrealms.kingdoms.obj.Town;

public class TownPromoteCommand extends DeityCommandReceiver {
    
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
        if (args.length == 0) { return false; }
        Resident changingResident = KingdomsManager.getResident(args[0]);
        if (changingResident == null) {
            KingdomsMain.plugin.chat.sendPlayerMessage(player,
                    String.format(KingdomsMessageHelper.CMD_FAIL_CANNOT_FIND_RESIDENT, args[0]));
            return true;
        }
        if (!changingResident.hasTown() || (changingResident.hasTown() && changingResident.getTown().getId() != town.getId())) {
            KingdomsMain.plugin.chat.sendPlayerMessage(player,
                    String.format(KingdomsMessageHelper.CMD_FAIL_RESIDENT_NOT_IN_TOWN, changingResident.getName()));
            return true;
        }
        if (changingResident.isMayor() || changingResident.isKing()) {
            KingdomsMain.plugin.chat.sendPlayerMessage(player,
                    String.format(KingdomsMessageHelper.CMD_FAIL_CANNOT_PROMOTE, changingResident.getName()));
            return true;
        }
        if (!changingResident.isAssistant() && !changingResident.isSeniorAssistant()) {
            changingResident.setAssistant(true);
            changingResident.setSeniorAssistant(false);
            changingResident.save();
            town.sendMessage(String.format(KingdomsMessageHelper.CMD_TOWN_PROMOTE_HELPER_TOWN, changingResident.getName()));
            return true;
        } else if (!changingResident.isSeniorAssistant()) {
            changingResident.setSeniorAssistant(true);
            changingResident.setAssistant(false);
            changingResident.save();
            town.sendMessage(String.format(KingdomsMessageHelper.CMD_TOWN_PROMOTE_ASSISTANT_TOWN, changingResident.getName()));
            return true;
        } else {
            resident.setMayor(false);
            resident.setSeniorAssistant(true);
            resident.save();
            
            town.setMayor(changingResident);
            changingResident.setMayor(true);
            changingResident.setSeniorAssistant(false);
            changingResident.save();
            town.sendMessage(String.format(KingdomsMessageHelper.CMD_TOWN_PROMOTE_MAYOR_TOWN, changingResident.getName()));
            return true;
        }
    }
    
    @Override
    public boolean onConsoleRunCommand(String[] args) {
        return false;
    }
    
}
