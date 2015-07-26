package net.ghostrealms.kingdoms.cmds.kingdom;

import org.bukkit.entity.Player;

import com.imdeity.deityapi.api.DeityCommandReceiver;

import net.ghostrealms.kingdoms.main.KingdomsMain;
import net.ghostrealms.kingdoms.obj.Kingdom;
import net.ghostrealms.kingdoms.main.KingdomsMessageHelper;
import net.ghostrealms.kingdoms.obj.KingdomsManager;
import net.ghostrealms.kingdoms.obj.Resident;

public class KingdomInfoCommand extends DeityCommandReceiver {
    
    @Override
    public boolean onPlayerRunCommand(Player player, String[] args) {
        Resident resident = KingdomsManager.getResident(player.getName());
        if (resident == null) { return false; }
        if (args.length == 0) {
            if (!resident.hasTown()) {
                KingdomsMain.plugin.chat.sendPlayerMessage(player, KingdomsMessageHelper.CMD_FAIL_NOT_IN_TOWN);
                return true;
            }
            if (resident.getTown().getKingdom() == null) {
                KingdomsMain.plugin.chat.sendPlayerMessage(player, "Your town is not in a kingdom");
                //TODO move to helper
                return true;
            }
            Kingdom kingdom = resident.getTown().getKingdom();
            for (String s : kingdom.showInfo()) {
                KingdomsMain.plugin.chat.sendPlayerMessageNoHeader(player, s);
            }
            return true;
        } else if (args.length >= 1) {
            String kingdomName = args[0];
            Kingdom kingdom = KingdomsManager.getKingdom(kingdomName);
            if (kingdom == null) {
                KingdomsMain.plugin.chat.sendPlayerMessage(player, String.format(KingdomsMessageHelper.CMD_FAIL_CANNOT_FIND_KINGDOM, kingdomName));
                return true;
            }
            for (String s : kingdom.showInfo()) {
                KingdomsMain.plugin.chat.sendPlayerMessageNoHeader(player, s);
            }
            return true;
        }
        return false;
    }
    
    @Override
    public boolean onConsoleRunCommand(String[] args) {
        return false;
    }
    
}
