package cc.funkemunky.scoreboard.anticheats;

import cc.funkemunky.api.utils.Init;
import cc.funkemunky.scoreboard.listeners.custom.AnticheatFlagEvent;
import cc.funkemunky.scoreboard.wrapper.CheckWrapper;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import space.fozzie.iris.data.api.IrisAlertEvent;

@Init(requirePlugins = "Iris")
public class Iris implements Listener {

    @EventHandler
    public void onEvent(IrisAlertEvent event) {
        AnticheatFlagEvent afe = new AnticheatFlagEvent("Iris", event.player,
                new CheckWrapper(event.checkName,
                        event.executable,
                        false,
                        event.checkName), event.vl, event.vl + "/" + event.maxVL);

        Bukkit.getPluginManager().callEvent(afe);
        event.cancelled = afe.isCancelled();
    }
}
