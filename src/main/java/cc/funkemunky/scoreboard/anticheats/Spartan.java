package cc.funkemunky.scoreboard.anticheats;

import cc.funkemunky.api.Atlas;
import cc.funkemunky.api.utils.Init;
import cc.funkemunky.scoreboard.listeners.custom.AnticheatFlagEvent;
import cc.funkemunky.scoreboard.wrapper.CheckWrapper;
import me.vagdedes.spartan.api.API;
import me.vagdedes.spartan.api.CheckCancelEvent;
import me.vagdedes.spartan.api.PlayerViolationCommandEvent;
import me.vagdedes.spartan.api.PlayerViolationEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;

@Init(requirePlugins = "Spartan")
public class Spartan {

    @EventHandler
    public void onEvent(PlayerViolationEvent event) {
        AnticheatFlagEvent afe = new AnticheatFlagEvent("Spartan", event.getPlayer(),
                new CheckWrapper(event.getHackType().name(),
                        true,
                        !API.isSilent(event.getHackType()),
                        event.getHackType().name()), event.getViolation(), event.getMessage());

        Bukkit.getPluginManager().callEvent(afe);

        event.setCancelled(afe.isCancelled());
    }

    @EventHandler
    public void onEvent(CheckCancelEvent event) {
        AnticheatFlagEvent afe = new AnticheatFlagEvent("Spartan-Cancel", event.getPlayer(),
                new CheckWrapper(event.getHackType().name(),
                        false,
                        true,
                        event.getHackType().name()), -1, "");

        Bukkit.getPluginManager().callEvent(afe);

        event.setCancelled(afe.isCancelled());
    }

    @EventHandler
    public void onEvent(PlayerViolationCommandEvent event) {
        event.setCancelled(true);
    }
}