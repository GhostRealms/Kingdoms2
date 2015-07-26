package net.ghostrealms.kingdoms.cmds.resident;

import org.bukkit.entity.Player;

import com.imdeity.deityapi.api.DeityCommandReceiver;

import net.ghostrealms.kingdoms.main.KingdomsMain;
import net.ghostrealms.kingdoms.main.KingdomsMessageHelper;
import net.ghostrealms.kingdoms.obj.KingdomsManager;
import net.ghostrealms.kingdoms.obj.Resident;

public class ResidentAddFriendCommand extends DeityCommandReceiver {
    
    @Override
    public boolean onPlayerRunCommand(Player player, String[] args) {
        if (args.length == 1) {
            Resident resident = KingdomsManager.getResident(player.getName());
            if (resident == null) { return false; }
            Resident friend = KingdomsManager.getResident(args[0]);
            if (friend != null) {
                resident.addFriend(friend);
                KingdomsMain.plugin.chat.sendPlayerMessage(player, String.format(KingdomsMessageHelper.CMD_RES_ADD_FRIEND_PLAYER, friend.getName()));
            } else {
                KingdomsMain.plugin.chat.sendPlayerMessage(player, String.format(KingdomsMessageHelper.CMD_FAIL_RES_RESIDENT_INVALID, args[0]));
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
