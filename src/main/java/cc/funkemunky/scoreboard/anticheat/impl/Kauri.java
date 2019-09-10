package cc.funkemunky.scoreboard.anticheat.impl;

import cc.funkemunky.anticheat.api.event.PlayerCheatEvent;
import cc.funkemunky.scoreboard.AnticheatScoreboard;
import cc.funkemunky.scoreboard.anticheat.Anticheat;
import cc.funkemunky.scoreboard.config.GeneralConfig;
import org.bukkit.event.EventHandler;

public class Kauri extends Anticheat {

    @EventHandler
    public void onEvent(PlayerCheatEvent event) {
        addViolation(event.getPlayer().getUniqueId(), event.getCheck().getName(), event.getCheck().isCancellable(), vl -> vl.checkName.equals(event.getCheck().getName()), vl -> vl.vlCount = event.getCheck().getVl(), "none");
        event.setCancelled(GeneralConfig.testMode && !AnticheatScoreboard.INSTANCE.anticheatManager.anticheatMap.get(event.getPlayer().getUniqueId()).equals(getAnticheatName()));
    }

    @Override
    public String getAnticheatName() {
        return "KauriLoader";
    }
}
