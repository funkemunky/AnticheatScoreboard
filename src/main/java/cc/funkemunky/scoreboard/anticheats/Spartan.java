package cc.funkemunky.scoreboard.anticheats;

import cc.funkemunky.api.Atlas;
import cc.funkemunky.api.utils.Init;
import cc.funkemunky.scoreboard.AnticheatScoreboard;
import cc.funkemunky.scoreboard.config.GeneralConfig;
import cc.funkemunky.scoreboard.listeners.custom.AnticheatFlagEvent;
import cc.funkemunky.scoreboard.wrapper.CheckWrapper;
import me.vagdedes.spartan.api.API;
import me.vagdedes.spartan.api.CheckCancelEvent;
import me.vagdedes.spartan.api.PlayerViolationCommandEvent;
import me.vagdedes.spartan.api.PlayerViolationEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@Init(requirePlugins = "Spartan")
public class Spartan implements Listener {

    @EventHandler
    public void onEvent(PlayerViolationEvent event) {
        AnticheatFlagEvent afe = new AnticheatFlagEvent("Spartan", event.getPlayer(),
                new CheckWrapper(event.getHackType().name(),
                        true,
                        !API.isSilent(event.getHackType()),
                        event.getHackType().name()), event.getViolation(), event.getMessage());

        Atlas.getInstance().getEventManager().callEvent(afe);

        event.setCancelled(afe.isCancelled());
    }

    @EventHandler
    public void onEvent(CheckCancelEvent event) {
        if(GeneralConfig.testMode && !AnticheatScoreboard.INSTANCE.alerts
                .computeIfAbsent(event.getPlayer().getUniqueId(), key -> {
                    AnticheatScoreboard.INSTANCE.alerts.put(key, "Vanilla");
                    return "Vanilla";
                }).equals("Spartan")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEvent(PlayerViolationCommandEvent event) {
        event.setCancelled(GeneralConfig.testMode);
    }
}