package net.ghostrealms.kingdoms.cmds.town;

import org.bukkit.entity.Player;

import com.imdeity.deityapi.api.DeityCommandReceiver;
import net.ghostrealms.kingdoms.main.KingdomsMain;
import net.ghostrealms.kingdoms.main.KingdomsMessageHelper;
import net.ghostrealms.kingdoms.obj.KingdomsManager;
import net.ghostrealms.kingdoms.obj.Resident;
import net.ghostrealms.kingdoms.obj.Town;

public class TownWithdrawCommand extends DeityCommandReceiver {
    
    @Override
    public boolean onPlayerRunCommand(Player player, String[] args) {
        Resident resident = KingdomsManager.getResident(player.getName());
        if (resident == null) { return false; }
        Town town = null;
        int amount = 0;
        if (args.length == 1) {
            if (!resident.hasTown()) {
                KingdomsMain.plugin.chat.sendPlayerMessage(player, KingdomsMessageHelper.CMD_FAIL_NOT_IN_TOWN);
                return true;
            }
            town = resident.getTown();
            
            if (!resident.isKing() && !resident.isMayor() && !resident.isSeniorAssistant()) {
                KingdomsMain.plugin.chat.sendPlayerMessage(player, KingdomsMessageHelper.CMD_FAIL_NOT_TOWN_STAFF);
                return true;
            }
            
            try {
                amount = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                KingdomsMain.plugin.chat.sendPlayerMessage(player, KingdomsMessageHelper.CMD_FAIL_INVALID_PRICE);
                return true;
            }
            if (!town.canPay(amount)) {
                KingdomsMain.plugin.chat.sendPlayerMessage(player, "Town does not have enough money");
                return true;
            }
            town.pay(resident.getName(), amount, "Town Withdraw");
            KingdomsMain.plugin.chat.sendPlayerMessage(player,
                    String.format(KingdomsMessageHelper.CMD_TOWN_WITHDRAW_PLAYER, amount, town.getName()));
            return true;
        }
        return false;
    }
    
    @Override
    public boolean onConsoleRunCommand(String[] args) {
        return false;
    }
    
}
