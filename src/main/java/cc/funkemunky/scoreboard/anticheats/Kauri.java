package cc.funkemunky.scoreboard.anticheats;

import cc.funkemunky.api.Atlas;
import cc.funkemunky.api.events.AtlasListener;
import cc.funkemunky.api.events.Listen;
import cc.funkemunky.api.utils.Init;
import cc.funkemunky.scoreboard.listeners.custom.AnticheatFlagEvent;
import cc.funkemunky.scoreboard.wrapper.CheckWrapper;
import dev.brighten.api.listener.KauriFlagEvent;

@Init(requirePlugins = {"KauriLoader", "Kauri"})
public class Kauri implements AtlasListener {

    @Listen
    public void onCheat(KauriFlagEvent event) {
        AnticheatFlagEvent afe = new AnticheatFlagEvent("Kauri", event.player,
                new CheckWrapper(event.check.getName(),
                        event.check.isEnabled(),
                        false,
                        event.check.getCheckType().name()),
                event.check.getVl(),
                event.information);

        Atlas.getInstance().getEventManager().callEvent(afe);
        event.setCancelled(afe.isCancelled());
    }
}
