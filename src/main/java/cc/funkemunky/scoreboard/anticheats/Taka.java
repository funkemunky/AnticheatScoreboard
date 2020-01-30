package cc.funkemunky.scoreboard.anticheats;

import TAKA.PlayerCheatEvent;
import cc.funkemunky.api.Atlas;
import cc.funkemunky.api.utils.Init;
import cc.funkemunky.scoreboard.listeners.custom.AnticheatFlagEvent;
import cc.funkemunky.scoreboard.wrapper.CheckWrapper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@Init(requirePlugins = "TakaAntiCheat")
public class Taka implements Listener {

    @EventHandler
    public void onCheat(PlayerCheatEvent event) {
        AnticheatFlagEvent afe = new AnticheatFlagEvent("TakaAntiCheat",
                event.getPlayer(), new CheckWrapper(event.getHack().name(),
                true, true, event.getHack().name()), event.getViolation(), "");

        Atlas.getInstance().getEventManager().callEvent(afe);
        event.setCancelled(afe.isCancelled());
    }
}