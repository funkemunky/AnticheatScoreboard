package cc.funkemunky.scoreboard.listeners;

import cc.funkemunky.api.events.AtlasListener;
import cc.funkemunky.api.events.Listen;
import cc.funkemunky.api.utils.Color;
import cc.funkemunky.api.utils.Init;
import cc.funkemunky.scoreboard.config.GeneralConfig;
import cc.funkemunky.scoreboard.listeners.custom.AnticheatFlagEvent;

@Init
public class AnticheatListener implements AtlasListener {

    @Listen
    public void onFlag(AnticheatFlagEvent event) {
        if(!event.isCancelled() && event.anticheat.inUse.contains(event.player)) {

            String message = Color.translate(GeneralConfig.alertMessage
                    .replace("%anticheat%", event.anticheat.name)
                    .replace("%player%", event.player.getName())
                    .replace("%check%", event.wrapper.getName())
                    .replace("%vl%", event.vl + "")
                    .replace("%tags%", event.messaging));

            event.player.sendMessage(message);
        } else event.setCancelled(GeneralConfig.testMode);
    }
}