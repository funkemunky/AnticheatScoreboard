package cc.funkemunky.scoreboard.anticheats;

import api.FireflyXViolationEvent;
import cc.funkemunky.api.utils.Init;
import cc.funkemunky.scoreboard.listeners.custom.AnticheatFlagEvent;
import cc.funkemunky.scoreboard.wrapper.CheckWrapper;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@Init(requirePlugins = {"fireflyx", "nLoader"})
public class Firefly implements Listener {

    @EventHandler
    public void onCheat(FireflyXViolationEvent event) {
        AnticheatFlagEvent afe = new AnticheatFlagEvent("FireflyX",
                event.getPlayer(),
                new CheckWrapper(event.getType(), event.autoban, event.cancel, event.getType()),
                event.getViolations(), event.getDebug());

        Bukkit.getPluginManager().callEvent(afe);

        event.setCancelled(afe.isCancelled());
    }
}
