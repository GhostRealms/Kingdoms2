package net.ghostrealms.kingdoms.cmds;

import com.imdeity.deityapi.api.DeityCommandHandler;

import net.ghostrealms.kingdoms.cmds.town.TownInfoCommand;
import net.ghostrealms.kingdoms.cmds.town.TownVoteCommand;
import net.ghostrealms.kingdoms.cmds.town.TownWithdrawCommand;
import net.ghostrealms.kingdoms.cmds.town.TownAddCommand;
import net.ghostrealms.kingdoms.cmds.town.TownChatCommand;
import net.ghostrealms.kingdoms.cmds.town.TownClaimCommand;
import net.ghostrealms.kingdoms.cmds.town.TownCreateCommand;
import net.ghostrealms.kingdoms.cmds.town.TownDeleteCommand;
import net.ghostrealms.kingdoms.cmds.town.TownDemoteCommand;
import net.ghostrealms.kingdoms.cmds.town.TownDepositCommand;
import net.ghostrealms.kingdoms.cmds.town.TownKickCommand;
import net.ghostrealms.kingdoms.cmds.town.TownLeaveCommand;
import net.ghostrealms.kingdoms.cmds.town.TownListCommand;
import net.ghostrealms.kingdoms.cmds.town.TownPromoteCommand;
import net.ghostrealms.kingdoms.cmds.town.TownSetCommand;
import net.ghostrealms.kingdoms.cmds.town.TownSpawnCommand;
import net.ghostrealms.kingdoms.cmds.town.TownUnclaimCommand;
import net.ghostrealms.kingdoms.cmds.town.TownWarpCommand;

public class TownCommand extends DeityCommandHandler {
    
    public TownCommand(String pluginName) {
        super(pluginName, "Town");
    }
    
    @Override
    public void initRegisteredCommands() {
        String[] setArgs = { "town-board [message]", "plot-price [amount]", "spawn", "public [on/off]", "permissions edit [P/F/T/K/TS/KS]", "permissions use [P/F/T/K/TS/KS]", "permissions access [P/F/T/K/TS/KS]" };
        String[] voteArgs = { "create", "[assistant-name]", "check" };
        String[] warpArgs = { "list", "[warp-name]", "add [warp-name]", "add [warp-name] [cost]", "remove [warp-name]" };
        this.registerCommand("list", null, "", "Lists all towns", new TownListCommand(), "kingdoms.town.list");
        this.registerCommand("info", null, "<town-name> <-o>", "Displays a towns information", new TownInfoCommand(), "kingdoms.town.info");
        this.registerCommand("create", null, "[town-name]", "Creates a new Town", new TownCreateCommand(), "kingdoms.town.create");
        this.registerCommand("delete", null, "[town-name]", "Deletes a town", new TownDeleteCommand(), "kingdoms.town.delete");
        this.registerCommand("claim", null, "", "Claims new town plots", new TownClaimCommand(), "kingdoms.town.claim");
        this.registerCommand("unclaim", null, "", "Unclaims this town plots", new TownUnclaimCommand(), "kingdoms.town.unclaim");
        this.registerCommand("add", null, "[player-name]", "Add a resident to the town", new TownAddCommand(), "kingdoms.town.add");
        this.registerCommand("kick", null, "[player-name]", "Kicks a resident from the town", new TownKickCommand(), "kingdoms.town.kick");
        this.registerCommand("set", null, setArgs, "Changes a towns options", new TownSetCommand(), "kingdoms.town.set");
        this.registerCommand("spawn", null, "<town-name>", "Teleports to a town's spawn", new TownSpawnCommand(), "kingdoms.town.spawn");
        this.registerCommand("leave", null, "", "Leaves the town", new TownLeaveCommand(), "kingdoms.town.leave");
        this.registerCommand("deposit", null, "<town> [amount]", "Deposits money into the town bank", new TownDepositCommand(), "kingdoms.town.deposit");
        this.registerCommand("withdraw", null, "[amount]", "Withdraws money from the town bank", new TownWithdrawCommand(), "kingdoms.town.withdraw");
        this.registerCommand("demote", null, "[resident-name]", "Demotes a resident to the next rank", new TownDemoteCommand(), "kingdoms.town.demote");
        this.registerCommand("promote", null, "[resident-name]", "Promotes a resident to the next rank", new TownPromoteCommand(), "kingdoms.town.promote");
        this.registerCommand("vote", null, voteArgs, "Voting on a new town mayor", new TownVoteCommand(), "kingdoms.town.vote");
        this.registerCommand("warp", null, warpArgs, "Town warp center", new TownWarpCommand(), "kingdoms.town.warp");
        this.registerCommand("chat", null, "<message>", "Chat to your town", new TownChatCommand(), "kingdoms.town.chat");
    }
}
