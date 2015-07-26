package net.ghostrealms.kingdoms.cmds;

import com.imdeity.deityapi.api.DeityCommandHandler;

import net.ghostrealms.kingdoms.cmds.kingdom.KingdomDepositCommand;
import net.ghostrealms.kingdoms.cmds.kingdom.KingdomInfoCommand;
import net.ghostrealms.kingdoms.cmds.kingdom.KingdomLeaveCommand;
import net.ghostrealms.kingdoms.cmds.kingdom.KingdomRemoveCommand;
import net.ghostrealms.kingdoms.cmds.kingdom.KingdomRequestCommand;
import net.ghostrealms.kingdoms.cmds.kingdom.KingdomWithdrawCommand;
import net.ghostrealms.kingdoms.cmds.kingdom.KingdomChatCommand;
import net.ghostrealms.kingdoms.cmds.kingdom.KingdomCreateCommand;
import net.ghostrealms.kingdoms.cmds.kingdom.KingdomListCommand;

public class KingdomCommand extends DeityCommandHandler {
    
    public KingdomCommand(String pluginName) {
        super(pluginName, "Kingdom");
    }
    
    @Override
    public void initRegisteredCommands() {
        String[] requestArgs = { "list", "accept [request-id]", "deny [request-id]", "join [kingdom-name]" };
        String[] createAliases = { "new" };
        String[] requestAliases = { "req" };
        String[] removeAliases = { "rm", "delete", "del" };
        this.registerCommand("info", null, "<kingdom-name>", "Shows a kingdoms status", new KingdomInfoCommand(), "kingdoms.kingdom.info");
        this.registerCommand("list", null, "", "Lists all kingdoms", new KingdomListCommand(), "kingdoms.kingdom.list");
        this.registerCommand("create", createAliases, "[kingdom-name]", "Creates a new Kingdom", new KingdomCreateCommand(), "kingdoms.kingdom.create");
        this.registerCommand("request", requestAliases, requestArgs, "Requests to join a kingdom", new KingdomRequestCommand(), "kingdoms.kingdom.request");
        this.registerCommand("remove", removeAliases, "[town-name]", "Removes a town from your kingdom", new KingdomRemoveCommand(), "kingdoms.kingdom.remove");
        this.registerCommand("leave", null, "", "Leaves a kingdom", new KingdomLeaveCommand(), "kingdoms.kingdom.leave");
        this.registerCommand("withdraw", null, "[amount]", "Takes money from the kingdom", new KingdomWithdrawCommand(), "kingdoms.kingdom.withdraw");
        this.registerCommand("deposit", null, "<kingdom-name> [amount]", "Adds money to a  kingdom", new KingdomDepositCommand(), "kingdoms.kingdom.deposit");
        this.registerCommand("chat", null, "<message>", "Talk with your kingdom", new KingdomChatCommand(), "kingdoms.kingdom.chat");
    }
}
