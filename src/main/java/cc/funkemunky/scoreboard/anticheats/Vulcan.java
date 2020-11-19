package cc.funkemunky.scoreboard.anticheats;

import cc.funkemunky.api.Atlas;
import cc.funkemunky.api.utils.Init;
import cc.funkemunky.scoreboard.listeners.custom.AnticheatFlagEvent;
import cc.funkemunky.scoreboard.wrapper.CheckWrapper;
import me.frep.api.VulcanFlagEvent;
import me.frep.vulcan.checks.Check;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@Init(requirePlugins = {"Vulcan"})
public class Vulcan implements Listener {

    @EventHandler
    public void onFlag(VulcanFlagEvent event) {
        AnticheatFlagEvent afe = new AnticheatFlagEvent("Vulcan", event.getPlayer(),
                new CheckWrapper(event.getCheck().getName(),
                        true,
                        false,
                        event.getCheck().getIdentifier()), event.getVl(), event.getCheck().getType().name());

        Atlas.getInstance().getEventManager().callEvent(afe);

        event.setCancelled(afe.isCancelled());

        if(afe.isCancelled()) {
            Check.violations.remove(event.getPlayer().getUniqueId());
        }
    }
}
