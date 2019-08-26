package cc.funkemunky.scoreboard.anticheat.impl;

import cc.funkemunky.scoreboard.AnticheatScoreboard;
import cc.funkemunky.scoreboard.anticheat.Anticheat;
import cc.funkemunky.scoreboard.config.GeneralConfig;
import me.konsolas.aac.api.PlayerViolationEvent;
import org.bukkit.event.EventHandler;

public class AAC extends Anticheat {

    @EventHandler
    public void onEvent(PlayerViolationEvent event) {
        addViolation(event.getPlayer().getUniqueId(), event.getHackType().getName(), event.isCancelled(), vl -> vl.checkName.equals(event.getHackType().getName()), vl -> vl.vlCount = event.getViolations(), event.getMessage());

        event.setCancelled(GeneralConfig.testMode && !AnticheatScoreboard.INSTANCE.anticheatManager.anticheatMap.get(event.getPlayer().getUniqueId()).getAnticheatName().equals(getAnticheatName()));
    }

    @Override
    public String getAnticheatName() {
        return "AAC";
    }
}
