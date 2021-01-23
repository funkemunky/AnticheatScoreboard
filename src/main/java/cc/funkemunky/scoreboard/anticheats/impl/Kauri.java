package cc.funkemunky.scoreboard.anticheats.impl;

import cc.funkemunky.api.Atlas;
import cc.funkemunky.api.events.AtlasListener;
import cc.funkemunky.api.events.Listen;
import cc.funkemunky.api.utils.Init;
import cc.funkemunky.scoreboard.anticheats.Anticheat;
import cc.funkemunky.scoreboard.listeners.custom.AnticheatFlagEvent;
import cc.funkemunky.scoreboard.wrapper.CheckWrapper;
import dev.brighten.api.check.CheckType;
import dev.brighten.api.listener.KauriFlagEvent;

@Init(requirePlugins = {"KauriLoader"})
public class Kauri extends Anticheat implements AtlasListener {

    public Kauri() {
        super("Kauri");

        pluginName = "KauriLoader";
    }

    @Listen
    public void onCheat(KauriFlagEvent event) {
        AnticheatFlagEvent afe = new AnticheatFlagEvent(this, event.player,
                new CheckWrapper(event.check.getName(),
                        event.check.isEnabled(),
                        false,
                        event.check.getCheckType().name()),
                event.check.getVl(),
                event.information);

        Atlas.getInstance().getEventManager().callEvent(afe);
        event.setCancelled(afe.isCancelled()
                && !event.getCheck().getCheckType().equals(CheckType.EXPLOIT));
    }
}
