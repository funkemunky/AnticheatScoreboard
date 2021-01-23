package cc.funkemunky.scoreboard.anticheats.impl;

import api.FireflyXViolationEvent;
import cc.funkemunky.api.Atlas;
import cc.funkemunky.api.utils.Init;
import cc.funkemunky.scoreboard.anticheats.Anticheat;
import cc.funkemunky.scoreboard.listeners.custom.AnticheatFlagEvent;
import cc.funkemunky.scoreboard.wrapper.CheckWrapper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@Init(requirePlugins = {"FireflyX"})
public class Firefly extends Anticheat implements Listener {

    public Firefly() {
        super("FireFlyX");
    }

    @EventHandler
    public void onCheat(FireflyXViolationEvent event) {
        AnticheatFlagEvent afe = new AnticheatFlagEvent(this,
                event.getPlayer(),
                new CheckWrapper(event.getType(), event.autoban, event.cancel, event.getType()),
                event.getViolations(), event.getDebug());

        Atlas.getInstance().getEventManager().callEvent(afe);

        event.setCancelled(afe.isCancelled());
    }
}
