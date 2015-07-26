package net.ghostrealms.kingdoms.cmds.admin;

import org.bukkit.entity.Player;

import com.imdeity.deityapi.api.DeityCommandReceiver;

import net.ghostrealms.kingdoms.main.KingdomsMain;
import net.ghostrealms.kingdoms.main.KingdomsMessageHelper;
import net.ghostrealms.kingdoms.obj.KingdomsManager;

public class AdminSetCommand extends DeityCommandReceiver {
    
    @Override
    public boolean onPlayerRunCommand(Player player, String[] args) {
        if (!player.isOp()) { return false; }
        if (args.length == 2) {
            String node = args[0];
            Object value;
            try {
                value = Double.parseDouble(args[1]);
            } catch (NumberFormatException e) {
                value = args[1];
            }
            KingdomsMain.plugin.config.set(node, value);
            KingdomsMain.plugin.chat.sendPlayerMessage(player, String.format(KingdomsMessageHelper.CMD_KINGDOM_ADMIN_SET, node, value));
            return true;
        }
        return false;
    }
    
    @Override
    public boolean onConsoleRunCommand(String[] args) {
        KingdomsManager.reload();
        KingdomsMain.plugin.chat.out(KingdomsMessageHelper.CMD_ADMIN_RELOAD);
        return false;
    }
    
}
