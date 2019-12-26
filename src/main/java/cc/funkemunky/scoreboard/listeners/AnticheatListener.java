package cc.funkemunky.scoreboard.listeners;

import cc.funkemunky.api.events.AtlasListener;
import cc.funkemunky.api.events.Listen;
import cc.funkemunky.api.events.ListenerPriority;
import cc.funkemunky.api.utils.Color;
import cc.funkemunky.api.utils.Init;
import cc.funkemunky.scoreboard.AnticheatScoreboard;
import cc.funkemunky.scoreboard.config.GeneralConfig;
import cc.funkemunky.scoreboard.listeners.custom.AnticheatFlagEvent;

@Init
public class AnticheatListener implements AtlasListener {

    @Listen(priority = ListenerPriority.LOWEST)
    public void onFlag(AnticheatFlagEvent event) {
        if(!event.isCancelled() && GeneralConfig.testMode) {
            if(AnticheatScoreboard.INSTANCE.alerts.containsKey(event.player.getUniqueId())) {
                String anticheat = AnticheatScoreboard.INSTANCE.alerts.get(event.player.getUniqueId());

                if(anticheat.equals("ALL") || event.anticheatName.toLowerCase().contains(anticheat.toLowerCase())) {
                    String message = Color.translate(GeneralConfig.alertMessage
                            .replace("%anticheat%", event.anticheatName)
                            .replace("%player%", event.player.getName())
                            .replace("%check%", event.wrapper.getName())
                            .replace("%vl%", event.vl + ""));

                    event.player.sendMessage(message);
                }
            }
            event.setCancelled(true);
        }
    }
}
