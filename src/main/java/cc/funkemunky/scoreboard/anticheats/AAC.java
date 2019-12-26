package cc.funkemunky.scoreboard.anticheats;

import cc.funkemunky.api.Atlas;
import cc.funkemunky.api.utils.Init;
import cc.funkemunky.scoreboard.listeners.custom.AnticheatFlagEvent;
import cc.funkemunky.scoreboard.wrapper.CheckWrapper;
import me.konsolas.aac.api.PlayerViolationEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@Init
public class AAC implements Listener {

    @EventHandler
    public void onEvent(PlayerViolationEvent event) {
        AnticheatFlagEvent afe =
                new AnticheatFlagEvent("AAC", event.getPlayer(),
                        new CheckWrapper(event.getHackType().getName(), false, true,
                                event.getHackType().getName()),
                        event.getViolations(), event.getMessage());

        Atlas.getInstance().getEventManager().callEvent(afe);
        event.setCancelled(afe.isCancelled());
    }
}
