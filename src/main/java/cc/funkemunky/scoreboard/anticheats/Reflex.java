package cc.funkemunky.scoreboard.anticheats;

import cc.funkemunky.api.Atlas;
import cc.funkemunky.api.utils.Init;
import cc.funkemunky.scoreboard.config.GeneralConfig;
import cc.funkemunky.scoreboard.listeners.custom.AnticheatFlagEvent;
import cc.funkemunky.scoreboard.wrapper.CheckWrapper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import rip.reflex.api.event.ReflexCheckEvent;
import rip.reflex.api.event.ReflexCommandEvent;

@Init(requirePlugins = {"Reflex"})
public class Reflex implements Listener {

    @EventHandler
    public void onEvent(ReflexCheckEvent event) {
        if(!event.getResult().isCheckFailed()) return;
        AnticheatFlagEvent afe = new AnticheatFlagEvent("Reflex", event.getPlayer(),
                new CheckWrapper(event.getCheat().name(),
                        event.getResult().isCheckFailed(),
                        event.getResult().cancel().isCheckFailed(),
                        event.getCheat().name()), event.getResult().getViolationsMod(),
                String.join(",", event.getResult().getTags()));

        Atlas.getInstance().getEventManager().callEvent(afe);
        event.setCancelled(afe.isCancelled());
    }

    @EventHandler
    public void onEvent(ReflexCommandEvent event) {
        event.setCancelled(GeneralConfig.testMode);
    }
}
