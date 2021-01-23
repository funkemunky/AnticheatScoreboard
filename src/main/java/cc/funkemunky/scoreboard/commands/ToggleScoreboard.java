package cc.funkemunky.scoreboard.commands;

import cc.funkemunky.api.Atlas;
import cc.funkemunky.api.commands.ancmd.Command;
import cc.funkemunky.api.commands.ancmd.CommandAdapter;
import cc.funkemunky.api.utils.Color;
import cc.funkemunky.api.utils.Init;
import cc.funkemunky.scoreboard.AnticheatScoreboard;
import cc.funkemunky.scoreboard.anticheats.Anticheat;

import java.util.Optional;

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
        Anticheat.anticheats.forEach(ac -> ac.inUse.forEach(ac::disableForPlayer));
        AnticheatScoreboard.INSTANCE.reloadConfig();
        cmd.getSender().sendMessage(Color.Green + "Reloaded plugin.");
    }

    @Command(name = "scoreboard.alerts", aliases = {"alerts", "anticheat", "ac"}, playerOnly = true,
            description = "view anticheat alerts.", display = "alerts", permission = "as.command.alerts")
    public void onAlerts(CommandAdapter cmd) {
        String anticheat = cmd.getArgs().length > 0 ? cmd.getArgs()[0] : "NONE";

        if(anticheat.equalsIgnoreCase("none")) {
            Anticheat.anticheats.forEach(ac -> ac.disableForPlayer(cmd.getPlayer()));
            cmd.getPlayer().sendMessage(Color.Red + "Disabled all alerts");
        } else {
            Optional<Anticheat> optional = Anticheat.getAnticheatByName(anticheat);

            if(optional.isPresent()) {
                optional.get().enableForPlayer(cmd.getPlayer());
                cmd.getPlayer().sendMessage(Color.Green + "Added alerts for " + optional.get().name + ".");
            } else if(anticheat.equalsIgnoreCase("all")) {
                Anticheat.anticheats.forEach(ac -> ac.enableForPlayer(cmd.getPlayer()));
                cmd.getPlayer().sendMessage(Color.Green + "Toggled on all alerts.");
            }
        }
    }
}
