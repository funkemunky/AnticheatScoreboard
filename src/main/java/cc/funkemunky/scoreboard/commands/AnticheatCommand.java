package cc.funkemunky.scoreboard.commands;

import cc.funkemunky.api.commands.ancmd.Command;
import cc.funkemunky.api.commands.ancmd.CommandAdapter;
import cc.funkemunky.api.utils.Color;
import cc.funkemunky.api.utils.Init;
import cc.funkemunky.scoreboard.AnticheatScoreboard;

@Init(commands = true)
public class AnticheatCommand {

    @Command(name = "as.anticheat", description = "Choose an anticheat to use.",
            aliases = {"anticheat", "ac", "pac", "asac", "pickac"},
            playerOnly = true,
            tabCompletions = {"%label%::kauri,,autoeye,,fireflyx,,aac,,hawk,,iris,,nocheatplus,,reflex,,spartan,,taka"})
    public void onCommand(CommandAdapter cmd) {
        if(cmd.getArgs().length == 0) {
            cmd.getPlayer().sendMessage(Color.Red + "Turned off anticheat alerts.");
        } else {
            AnticheatScoreboard.INSTANCE.alerts.put(cmd.getPlayer().getUniqueId(), cmd.getArgs()[0]);
        }
    }
}
