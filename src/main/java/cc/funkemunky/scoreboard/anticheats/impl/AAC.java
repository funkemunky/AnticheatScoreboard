package cc.funkemunky.scoreboard.anticheats.impl;

import cc.funkemunky.api.Atlas;
import cc.funkemunky.api.utils.Init;
import cc.funkemunky.scoreboard.anticheats.Anticheat;
import cc.funkemunky.scoreboard.listeners.custom.AnticheatFlagEvent;
import cc.funkemunky.scoreboard.wrapper.CheckWrapper;
import me.konsolas.aac.api.PlayerViolationCommandEvent;
import me.konsolas.aac.api.PlayerViolationEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@Init(requirePlugins = "AAC")
public class AAC extends Anticheat implements Listener {

    public AAC() {
        super("AAC");
    }

    @EventHandler
    public void onEvent(PlayerViolationEvent event) {
        AnticheatFlagEvent afe =
                new AnticheatFlagEvent(this, event.getPlayer(),
                        new CheckWrapper(event.getHackType().getName(), false, true,
                                event.getHackType().getName()),
                        event.getViolations(), event.getMessage());

        Atlas.getInstance().getEventManager().callEvent(afe);
        event.setCancelled(afe.isCancelled());
    }

    @EventHandler
    public void onEvent(PlayerViolationCommandEvent event) {
        event.setCancelled(true);
    }
}
