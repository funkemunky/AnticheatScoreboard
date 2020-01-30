package cc.funkemunky.scoreboard.anticheats;

import cc.funkemunky.api.Atlas;
import cc.funkemunky.api.utils.Init;
import cc.funkemunky.api.utils.MathUtils;
import cc.funkemunky.scoreboard.listeners.custom.AnticheatFlagEvent;
import cc.funkemunky.scoreboard.wrapper.CheckWrapper;
import me.islandscout.hawk.event.bukkit.HawkFlagEvent;
import me.islandscout.hawk.util.Violation;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@Init(requirePlugins = "Hawk")
public class Hawk implements Listener {

    @EventHandler
    public void onCheat(HawkFlagEvent event) {
        Violation violation = event.getViolation();
        AnticheatFlagEvent afe = new AnticheatFlagEvent("Hawk",
                violation.getPlayer(), new CheckWrapper(violation.getCheck().getName(),
                violation.getCheck().canFlag(), violation.getCheck().canCancel(),
                violation.getCheck().getName()), violation.getVl(),
                "Ping: " + violation.getPing() + "ms TPS: " + MathUtils.round(violation.getTps(), 2));

        Atlas.getInstance().getEventManager().callEvent(afe);
    }
}
