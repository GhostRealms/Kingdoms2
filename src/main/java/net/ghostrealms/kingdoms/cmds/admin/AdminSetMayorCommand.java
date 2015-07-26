package net.ghostrealms.kingdoms.cmds.admin;

import org.bukkit.entity.Player;

import com.imdeity.deityapi.api.DeityCommandReceiver;

import net.ghostrealms.kingdoms.main.KingdomsMain;
import net.ghostrealms.kingdoms.obj.Town;
import net.ghostrealms.kingdoms.obj.KingdomsManager;
import net.ghostrealms.kingdoms.obj.Resident;

public class AdminSetMayorCommand extends DeityCommandReceiver {
    
    @Override
    public boolean onConsoleRunCommand(String[] args) {
        if (args.length < 2) { return false; }
        if (args[1].equalsIgnoreCase("npc")) {
            Town town = KingdomsManager.getTown(args[0]);
            if (town == null) {
                KingdomsMain.plugin.chat.out("The town of " + args[0] + " is invalid");
                return true;
            }
            town.setMayor(null);
        } else {
            Resident resident = KingdomsManager.getResident(args[1]);
            Town town = KingdomsManager.getTown(args[0]);
            if (town == null) {
                KingdomsMain.plugin.chat.out("The town of " + args[1] + " is invalid");
                return true;
            }
            if (resident == null) {
                KingdomsMain.plugin.chat.out("The resident " + args[1] + " is invalid");
                return true;
            }
            town.addResident(resident);
            town.setMayor(resident);
        }
        KingdomsMain.plugin.chat.out("Set the mayor of " + args[0] + " to " + args[1]);
        return true;
    }
    
    @Override
    public boolean onPlayerRunCommand(Player player, String[] args) {
        if (args.length < 2) { return false; }
        if (args[1].equalsIgnoreCase("npc")) {
            Town town = KingdomsManager.getTown(args[0]);
            if (town == null) {
                KingdomsMain.plugin.chat.sendPlayerMessage(player, "The town of " + args[1] + " is invalid");
                return true;
            }
            town.setMayor(null);
        } else {
            Resident resident = KingdomsManager.getResident(args[1]);
            Town town = KingdomsManager.getTown(args[0]);
            if (town == null) {
                KingdomsMain.plugin.chat.sendPlayerMessage(player, "The town of " + args[1] + " is invalid");
                return true;
            }
            if (resident == null) {
                KingdomsMain.plugin.chat.sendPlayerMessage(player, "The resident " + args[1] + " is invalid");
                return true;
            }
            town.addResident(resident);
            town.setMayor(resident);
        }
        KingdomsMain.plugin.chat.sendPlayerMessage(player, "Set the mayor of " + args[0] + " to " + args[1]);
        return true;
    }
    
}
