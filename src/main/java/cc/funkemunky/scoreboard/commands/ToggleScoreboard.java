package cc.funkemunky.scoreboard.commands;

import cc.funkemunky.api.Atlas;
import cc.funkemunky.api.commands.ancmd.Command;
import cc.funkemunky.api.commands.ancmd.CommandAdapter;
import cc.funkemunky.api.utils.Color;
import cc.funkemunky.api.utils.Init;
import cc.funkemunky.scoreboard.AnticheatScoreboard;

@Init(commands = true)
public class ToggleScoreboard {

    @Command(name = "scoreboard", description = "The AnticheatScoreboard main command.",
            display = "Anticheat Scoreboard", aliases = {"as", "ascb"})
    public void onCommand(CommandAdapter cmd) {
        Atlas.getInstance().getCommandManager(AnticheatScoreboard.INSTANCE).runHelpMessage(cmd, cmd.getSender(),
                Atlas.getInstance().getCommandManager(AnticheatScoreboard.INSTANCE).getDefaultScheme());
    }

    @Command(name = "scoreboard.reload", description = "reload the plugin.", display = "reload",
            permission = "as.command.reload", aliases = {"as.reload", "ascb.reload"})
    public void onReload(CommandAdapter cmd) {
        cmd.getSender().sendMessage(Color.Gray + "Reloading...");
        AnticheatScoreboard.INSTANCE.alerts.clear();
        AnticheatScoreboard.INSTANCE.reloadConfig();
        cmd.getSender().sendMessage(Color.Green + "Reloaded plugin.");
    }

    @Command(name = "scoreboard.alerts", aliases = {"alerts", "anticheat", "ac"}, playerOnly = true,
            description = "view anticheat alerts.", display = "alerts", permission = "as.command.alerts")
    public void onAlerts(CommandAdapter cmd) {
        if(!AnticheatScoreboard.INSTANCE.alerts.containsKey(cmd.getPlayer().getUniqueId())) {
            String anticheat = cmd.getArgs().length > 0 ? cmd.getArgs()[0] : "ALL";

            AnticheatScoreboard.INSTANCE.alerts.put(cmd.getPlayer().getUniqueId(), anticheat);
            cmd.getPlayer().sendMessage(Color.Green + "Alerts are on for " + anticheat + ".");
        } else {
            AnticheatScoreboard.INSTANCE.alerts.remove(cmd.getPlayer().getUniqueId());
            cmd.getPlayer().sendMessage(Color.Red + "Alerts are off.");
        }
    }
}
