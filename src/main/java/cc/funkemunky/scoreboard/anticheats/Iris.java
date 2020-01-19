package cc.funkemunky.scoreboard.anticheats;

import cc.funkemunky.api.Atlas;
import cc.funkemunky.api.utils.Init;
import cc.funkemunky.scoreboard.listeners.custom.AnticheatFlagEvent;
import cc.funkemunky.scoreboard.wrapper.CheckWrapper;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import space.fozzie.iris.api.event.IrisCheatEvent;

@Init(requirePlugins = "Iris")
public class Iris implements Listener {

    @EventHandler
    public void onEvent(IrisCheatEvent event) {
        AnticheatFlagEvent afe = new AnticheatFlagEvent("Iris", event.getPlayer(),
                new CheckWrapper(event.getCheck().getName(),
                        event.getCheck().isExecutable(),
                        event.getCheck().isCancellable(),
                        event.getCheck().getCancelType().name()), event.getCheck().getVl(), event.getInformation());

        Bukkit.getPluginManager().callEvent(afe);
        event.setCancelled(afe.isCancelled());
    }
}
