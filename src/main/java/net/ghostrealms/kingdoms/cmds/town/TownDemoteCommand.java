package net.ghostrealms.kingdoms.cmds.town;

import org.bukkit.entity.Player;

import com.imdeity.deityapi.api.DeityCommandReceiver;

import net.ghostrealms.kingdoms.main.KingdomsMain;
import net.ghostrealms.kingdoms.obj.Town;
import net.ghostrealms.kingdoms.main.KingdomsMessageHelper;
import net.ghostrealms.kingdoms.obj.KingdomsManager;
import net.ghostrealms.kingdoms.obj.Resident;

public class TownDemoteCommand extends DeityCommandReceiver {
    
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
        if (changingResident.isMayor()
                || changingResident.isKing()
                || (!changingResident.isAssistant() && !changingResident.isSeniorAssistant() && !changingResident.isMayor() && !changingResident
                        .isKing())) {
            KingdomsMain.plugin.chat.sendPlayerMessage(player,
                    String.format(KingdomsMessageHelper.CMD_FAIL_CANNOT_DEMOTE, changingResident.getName()));
            return true;
        }
        if (changingResident.isAssistant()) {
            changingResident.setAssistant(false);
            changingResident.save();
            town.sendMessage(String.format(KingdomsMessageHelper.CMD_TOWN_DEMOTE_HELPER_TOWN, changingResident.getName()));
            return true;
        } else if (changingResident.isSeniorAssistant()) {
            changingResident.setSeniorAssistant(false);
            changingResident.setAssistant(true);
            changingResident.save();
            town.sendMessage(String.format(KingdomsMessageHelper.CMD_TOWN_DEMOTE_ASSISTANT_TOWN, changingResident.getName()));
            return true;
        }
        return false;
    }
    
    @Override
    public boolean onConsoleRunCommand(String[] args) {
        return false;
    }
    
}
