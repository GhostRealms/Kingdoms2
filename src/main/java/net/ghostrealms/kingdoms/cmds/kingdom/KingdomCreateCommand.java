package net.ghostrealms.kingdoms.cmds.kingdom;

import org.bukkit.entity.Player;

import com.imdeity.deityapi.api.DeityCommandReceiver;

import net.ghostrealms.kingdoms.main.KingdomsMain;
import net.ghostrealms.kingdoms.main.KingdomsMessageHelper;
import net.ghostrealms.kingdoms.obj.Kingdom;
import net.ghostrealms.kingdoms.obj.KingdomsHelper;
import net.ghostrealms.kingdoms.obj.KingdomsManager;
import net.ghostrealms.kingdoms.obj.Resident;
import net.ghostrealms.kingdoms.obj.Town;
import net.ghostrealms.kingdoms.main.KingdomsConfigHelper;

public class KingdomCreateCommand extends DeityCommandReceiver {
    
    @Override
    public boolean onPlayerRunCommand(Player player, String[] args) {
        Resident resident = KingdomsManager.getResident(player.getName());
        if (resident == null) { return false; }
        if (args.length == 0) { return false; }
        if (!resident.hasTown() || !resident.isMayor()) {
            KingdomsMain.plugin.chat.sendPlayerMessage(player, KingdomsMessageHelper.CMD_FAIL_KINGDOM_CREATE_NO_TOWN);
            return true;
        }
        if (resident.getTown().getKingdom() != null) {
            KingdomsMain.plugin.chat.sendPlayerMessage(player, KingdomsMessageHelper.CMD_FAIL_KINGDOM_CREATE_ALREADY_IN_KINGDOM);
            return true;
        }
        double cost = KingdomsMain.plugin.config.getDouble(String.format(KingdomsConfigHelper.KINGDOM_PRICES_CREATE, player.getWorld().getName()));
        if (!resident.canPay(cost)) {
            KingdomsMain.plugin.chat.sendPlayerMessage(player, KingdomsMessageHelper.CMD_FAIL_NO_MONEY);
            return true;
        }
        String kingdomName = args[0];
        if (!KingdomsHelper.verifyName(kingdomName)) {
            KingdomsMain.plugin.chat.sendPlayerMessage(player, String.format(KingdomsMessageHelper.CMD_FAIL_NAME_INVALID, kingdomName));
            return true;
        }
        if (KingdomsHelper.verifyNameExist(kingdomName, false)) {
            KingdomsMain.plugin.chat.sendPlayerMessage(player, String.format(KingdomsMessageHelper.CMD_FAIL_KINGDOM_CREATE_EXIST, kingdomName));
            return true;
        }
        new Runner(resident, kingdomName, cost);
        return true;
    }
    
    @Override
    public boolean onConsoleRunCommand(String[] args) {
        return false;
    }
    
    public class Runner implements Runnable {
        private Resident resident;
        private Town town;
        private String kingdomName;
        private double cost;
        
        public Runner(Resident resident, String kingdomName, double cost) {
            this.resident = resident;
            this.town = resident.getTown();
            this.kingdomName = kingdomName;
            this.cost = cost;
            KingdomsMain.plugin.getServer().getScheduler().scheduleAsyncDelayedTask(KingdomsMain.plugin, this);
        }
        
        public void run() {
            try {
                Kingdom kingdom = KingdomsManager.addNewKingdom(kingdomName);
                kingdom.addTown(town, true);
                resident.pay(cost, "Kingdom Creation");
                KingdomsMain.plugin.chat.sendGlobalMessage(String.format(KingdomsMessageHelper.CMD_KINGDOM_CREATE_SUCCESS_PUBLIC, resident.getName(), kingdom.getName()));
            } catch (Exception e) {
            }
        }
    }
}
