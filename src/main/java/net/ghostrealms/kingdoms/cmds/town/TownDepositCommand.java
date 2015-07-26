package net.ghostrealms.kingdoms.cmds.town;

import org.bukkit.entity.Player;

import com.imdeity.deityapi.api.DeityCommandReceiver;

import net.ghostrealms.kingdoms.main.KingdomsMain;
import net.ghostrealms.kingdoms.main.KingdomsMessageHelper;
import net.ghostrealms.kingdoms.obj.Resident;
import net.ghostrealms.kingdoms.obj.Town;
import net.ghostrealms.kingdoms.obj.KingdomsManager;

public class TownDepositCommand extends DeityCommandReceiver {
    
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
            try {
                amount = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                KingdomsMain.plugin.chat.sendPlayerMessage(player, KingdomsMessageHelper.CMD_FAIL_INVALID_PRICE);
                return true;
            }
        } else {
            town = KingdomsManager.getTown(args[0]);
            if (town == null) {
                KingdomsMain.plugin.chat.sendPlayerMessage(player, String.format(KingdomsMessageHelper.CMD_FAIL_CANNOT_FIND_TOWN, args[0]));
                return true;
            }
            try {
                amount = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                KingdomsMain.plugin.chat.sendPlayerMessage(player, KingdomsMessageHelper.CMD_FAIL_INVALID_PRICE);
                return true;
            }
        }
        if (!resident.canPay(amount)) {
            KingdomsMain.plugin.chat.sendPlayerMessage(player, KingdomsMessageHelper.CMD_FAIL_NO_MONEY);
            return true;
        }
        resident.pay(town.getEconName(), amount, "Town Deposit");
        KingdomsMain.plugin.chat.sendPlayerMessage(player, String.format(KingdomsMessageHelper.CMD_TOWN_DEPOSIT_PLAYER, amount, town.getName()));
        return true;
    }
    
    @Override
    public boolean onConsoleRunCommand(String[] args) {
        return false;
    }
    
}
