package cc.funkemunky.scoreboard.commands;

import be.maximvdw.featherboard.P;
import cc.funkemunky.api.Atlas;
import cc.funkemunky.api.commands.ancmd.Command;
import cc.funkemunky.api.commands.ancmd.CommandAdapter;
import cc.funkemunky.api.utils.Color;
import cc.funkemunky.api.utils.Init;
import cc.funkemunky.api.utils.MiscUtils;
import cc.funkemunky.scoreboard.AnticheatScoreboard;
import cc.funkemunky.scoreboard.anticheat.Anticheat;
import cc.funkemunky.scoreboard.anticheat.Violation;
import cc.funkemunky.scoreboard.config.GeneralConfig;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.TreeMap;

@Init(commands = true)
public class ToggleScoreboard {

    @Command(name = "scoreboard", description = "The AnticheatScoreboard main command.", display = "Anticheat Scoreboard", permission = "as.command.scoreboard", aliases = {"as", "ascb"})
    public void onCommand(CommandAdapter cmd) {
        Atlas.getInstance().getCommandManager().runHelpMessage(cmd, cmd.getSender(), Atlas.getInstance().getCommandManager().getDefaultScheme());
    }

    @Command(name = "scoreboard.toggle", description = "toggle your scoreboard.", display = "toggle", permission = "as.command.toggle", aliases = {"as.toggle", "ascb.toggle"}, playerOnly = true)
    public void onToggle(CommandAdapter cmd) {
        if(AnticheatScoreboard.INSTANCE.scoreboardManager.scoreboards.containsKey(cmd.getPlayer())) {
            val scoreboard = AnticheatScoreboard.INSTANCE.scoreboardManager.scoreboards.get(cmd.getPlayer());

            if(scoreboard.isActivated()) {
                scoreboard.deactivate();
                cmd.getPlayer().sendMessage(Color.Red + "Deactivated your scoreboard.");
            } else {
                scoreboard.activate();
                cmd.getPlayer().sendMessage(Color.Green + "Reactivated your scoreboard.");
            }
        }
    }

    @Command(name = "scoreboard.reload", description = "reload the plugin.", display = "reload", permission = "as.command.reload", aliases = {"as.reload", "ascb.reload"})
    public void onReload(CommandAdapter cmd) {
        cmd.getSender().sendMessage(Color.Gray + "Reloading...");
        AnticheatScoreboard.INSTANCE.scoreboardManager.scoreboards.keySet().forEach(key -> {
            AnticheatScoreboard.INSTANCE.scoreboardManager.scoreboards.get(key).deactivate();
            AnticheatScoreboard.INSTANCE.scoreboardManager.scoreboards.remove(key);
        });

        AnticheatScoreboard.INSTANCE.reloadConfig();
        AnticheatScoreboard.INSTANCE.loadScoreboards();
        cmd.getSender().sendMessage(Color.Green + "Reloaded plugin.");
    }

    @Command(name = "scoreboard.violations", description = "view violation's of a player", display = "violations [player]", permission = "as.command.violations", aliases = {"as.violations", "ascb.violations", "as.vl", "scoreboard.vl", "ascb.vl"})
    public void onViolation(CommandAdapter cmd) {
        cmd.getSender().sendMessage(MiscUtils.line(Color.Dark_Gray));
        val instance = new TreeMap<>(AnticheatScoreboard.INSTANCE.anticheatManager.anticheat.violations);
        if(cmd.getArgs().length == 0) {
            if(cmd.getSender() instanceof Player) {
                if(instance.containsKey(cmd.getPlayer().getUniqueId())) {
                    for (Violation violation : instance.get(cmd.getPlayer().getUniqueId())) {
                        cmd.getSender().sendMessage(Color.translate("&7- &f" + violation.checkName + " &c(" + violation.vlCount + ")"));
                    }
                } else cmd.getSender().sendMessage(Color.Red + "No violations.");
            } else cmd.getSender().sendMessage(Color.Red + "You must be a player to view your own violations.");
        } else {
            OfflinePlayer target = Bukkit.getOfflinePlayer(cmd.getArgs()[0]);

            if(target != null) {
                if(instance.containsKey(target.getUniqueId())) {
                    for (Violation violation : instance.get(target.getUniqueId())) {
                        cmd.getSender().sendMessage(Color.translate("&7- &f" + violation.checkName + " &c(" + violation.vlCount + ")"));
                    }
                } else cmd.getSender().sendMessage(Color.Red + "No violations.");
            } else cmd.getSender().sendMessage(Color.Red + "Wow you actually found a username that doesnt even exist. Good job.");
        }
        cmd.getSender().sendMessage(MiscUtils.line(Color.Dark_Gray));
    }

    @Command(name = "scoreboard.alerts", playerOnly = true, description = "view anticheat alerts.", display = "alerts", permission = "as.command.alerts", aliases = {"as.alerts", "ascb.alerts"})
    public void onAlerts(CommandAdapter cmd) {
        if(!AnticheatScoreboard.INSTANCE.alerts.contains(cmd.getPlayer())) {
            AnticheatScoreboard.INSTANCE.alerts.add(cmd.getPlayer());
            cmd.getPlayer().sendMessage(Color.Green + "Alerts are on.");
        } else {
            AnticheatScoreboard.INSTANCE.alerts.remove(cmd.getPlayer());
            cmd.getPlayer().sendMessage(Color.Red + "Alerts are off.");
        }
    }

    @Command(name = "scoreboard.anticheat", playerOnly = true, description = "change anticheat [testmode only].", display = "anticheat <name>", aliases = {"as.anticheat", "ascb.anticheat"})
    public void onAnticheat(CommandAdapter cmd) {
        if(!GeneralConfig.testMode) {
            cmd.getSender().sendMessage(Color.Red + "Testmode is off.");
            return;
        }
        if(cmd.getArgs().length > 0) {
            val optional = AnticheatScoreboard.INSTANCE.anticheatManager.anticheats.stream().filter(ac -> ac.getAnticheatName().equalsIgnoreCase(cmd.getArgs()[0])).findFirst();
            if(optional.isPresent()) {
                Anticheat anticheat = optional.get();
                AnticheatScoreboard.INSTANCE.anticheatManager.anticheatMap.put(cmd.getPlayer().getUniqueId(), anticheat.getAnticheatName());
                cmd.getSender().sendMessage(Color.Green + "Using anticheat " + anticheat.getAnticheatName() + ".");
            } else cmd.getPlayer().sendMessage(Color.Red + "Not an anticheat.");
        } else cmd.getPlayer().sendMessage(Color.Red + "Invalid arguments you dipcrap wiener.");
    }
}
