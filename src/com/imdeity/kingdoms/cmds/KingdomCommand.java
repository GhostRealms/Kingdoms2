package com.imdeity.kingdoms.cmds;

import com.imdeity.deityapi.api.DeityCommandHandler;
import com.imdeity.kingdoms.cmds.kingdom.KingdomCreateCommand;
import com.imdeity.kingdoms.cmds.kingdom.KingdomDepositCommand;
import com.imdeity.kingdoms.cmds.kingdom.KingdomInfoCommand;
import com.imdeity.kingdoms.cmds.kingdom.KingdomLeaveCommand;
import com.imdeity.kingdoms.cmds.kingdom.KingdomListCommand;
import com.imdeity.kingdoms.cmds.kingdom.KingdomRemoveCommand;
import com.imdeity.kingdoms.cmds.kingdom.KingdomRequestCommand;
import com.imdeity.kingdoms.cmds.kingdom.KingdomWithdrawCommand;

public class KingdomCommand extends DeityCommandHandler {
    
    public KingdomCommand(String pluginName) {
        super(pluginName, "Kingdom");
    }
    
    @Override
    public void initRegisteredCommands() {
        String[] requestArgs = { "list", "accept [request-id]", "deny [request-id]", "join [kingdom-name]" };
        this.registerCommand("info", "<kingdom-name>", "Shows a kingdoms status", new KingdomInfoCommand(), "kingdoms.kingdom.info");
        this.registerCommand("list", "", "Lists all kingdoms", new KingdomListCommand(), "kingdoms.kingdom.list");
        this.registerCommand("create", "[kingdom-name]", "Creates a new Kingdom", new KingdomCreateCommand(), "kingdoms.kingdom.create");
        this.registerCommand("request", requestArgs, "Requests to join a kingdom", new KingdomRequestCommand(), "kingdoms.kingdom.request");
        this.registerCommand("remove", "[town-name]", "Removes a town from your kingdom", new KingdomRemoveCommand(), "kingdoms.kingdom.remove");
        this.registerCommand("leave", "", "Leaves a kingdom", new KingdomLeaveCommand(), "kingdoms.kingdom.leave");
        this.registerCommand("withdraw", "[amount]", "Takes money from the kingdom", new KingdomWithdrawCommand(), "kingdoms.kingdom.withdraw");
        this.registerCommand("deposit", "<kingdom-name> [amount]", "Adds money to a  kingdom", new KingdomDepositCommand(), "kingdoms.kingdom.deposit");
    }
}
