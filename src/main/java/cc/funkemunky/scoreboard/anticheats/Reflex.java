package cc.funkemunky.scoreboard.anticheats;

import cc.funkemunky.api.Atlas;
import cc.funkemunky.api.utils.Init;
import cc.funkemunky.scoreboard.listeners.custom.AnticheatFlagEvent;
import cc.funkemunky.scoreboard.wrapper.CheckWrapper;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import rip.reflex.api.event.ReflexCheckEvent;

@Init(requirePlugins = {"Reflex"})
public class Reflex {

    @EventHandler
    public void onEvent(ReflexCheckEvent event) {
        AnticheatFlagEvent afe = new AnticheatFlagEvent("Reflex", event.getPlayer(),
                new CheckWrapper(event.getCheat().name(),
                        event.getResult().isCheckFailed(),
                        event.getResult().cancel().isCheckFailed(),
                        event.getCheat().name()), event.getResult().getViolationsMod(),
                String.join(",", event.getResult().getTags()));

        Bukkit.getPluginManager().callEvent(afe);
        event.setCancelled(afe.isCancelled());
    }
}
